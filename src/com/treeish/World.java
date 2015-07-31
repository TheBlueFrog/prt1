package com.treeish;

import com.company.Thing;
import com.mike.util.PhysicalObject;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mike on 7/24/2015.
 */
public class World extends TreeData {
    public long tick;

    /**
     * initialize the world
     *
     * @param f file containing the world to load
     * @return a world, in the same state as it was when saved
     */
    public static TreeNode<TreeData> init(File f) throws FileNotFoundException {

        TreeNode<TreeData> wt = null;

        try {
            if ( ! f.exists()) {
                // setup tree
                // save to file
                wt = new TreeNode<TreeData>(null);
                World w = new World(null, wt);
                wt.data = w;

                for (Thing t : Thing.defaultThings(wt)) {
                    wt.addChild(t);
                }

//                Iterator<TreeNode<TreeData>> iter = wt.iterator();
//                iter.forEachRemaining(t -> {
//                    System.out.println(String.format("Created %s", t.data.getClass().getSimpleName()));
//                });

                try {
                    write(wt, f);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /* dump all of that and do the normal read */
                wt = null;
            }

            JSONTokener t = new JSONTokener (new FileInputStream(f));

            wt = new TreeNode<TreeData>(null);
            World w = new World(t, wt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        Iterator<TreeNode<TreeData>> iter = wt.iterator();
        iter.forEachRemaining(t -> {
            System.out.println(String.format("Loaded %s", t.data.getClass().getSimpleName()));
        });

        return wt;
    }

    private static void write(TreeNode<TreeData> root, File json) throws IOException {

        World w = (World) root.data;

        JSONObject j = new JSONObject();
        w.toJSON(j);

        OutputStream os = new FileOutputStream(json);
        os.write(j.toString().getBytes());
    }

    public World (JSONTokener t, TreeNode<TreeData> root) throws SQLException {
        treeNode = root;
        treeNode.data = this;

        tick = 0;
        if (t != null) {
            JSONObject j = new JSONObject(t);
            j = j.getJSONObject("world");
            tick = j.getLong("tick");

            JSONObject jthing = j.getJSONObject("thing");
            TreeNode<TreeData> tn = new TreeNode<TreeData>(null);
            Thing th = new Thing(tn, jthing);
            treeNode.addChild(th);
        }
    }

    public void fromJSON(JSONObject parent) {
        JSONObject j = parent.getJSONObject("world");

        tick = j.getLong("tick");
    }
    public void toJSON(JSONObject parent) {
        JSONObject j = new JSONObject();
        parent.put("world", j);

        j.put("tick", tick);

        // and recurse through children
        treeNode.children.forEach(r -> {
            r.data.toJSON(j);
        });
    }

    @Override
    public void tick() {

        tick++;

        // and recurse through children
        treeNode.children.forEach(r -> {
            r.data.tick();
        });
    }

    public long getTick () {
        return tick;
    }

}
