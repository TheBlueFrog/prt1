package com.company;

import com.mike.WorldState;
import com.mike.util.PhysicalObject;
import com.treeish.TreeData;
import com.treeish.TreeNode;
import com.treeish.World;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/20/2015.
 */
public class Thing extends TreeData {
    static private final String TAG = Thing.class.getSimpleName();

    private long born = 0;
    private PhysicalObject physical;
    private Smarts smarts;

    static public List<Thing> defaultThings(TreeNode<TreeData> parent) throws SQLException {
        List<Thing> list = new ArrayList<>();
//        list.sort();
        list.add(new Thing(parent, null));
        return list;
    }

    public Thing (TreeNode<TreeData> parent, JSONObject input) throws SQLException {
        treeNode = parent;

        if (input == null) {
            born = WorldState.getTick();
            setPhysics(0, 0, 0, 1.0);
        }
        else {
            born = input.getLong("born");
            physical = new PhysicalObject(0, 0, 0, 0);
            physical.fromJSON(input);
//            setPhysics(0, 0, 0, 1.0);// read it
        }
    }

    public long getAge() {
        return WorldState.getTick() - born;
    }

    private void setPhysics(double position, double velocity, double acceleration, double mass) {
        physical = new PhysicalObject(position, velocity, acceleration, mass);
    }
    public PhysicalObject getPhysics () {
        return physical;
    }

    public void tick() {
        smarts.tick();
        double acceleration = ((World)Main.treeRoot.data).getTick() == 4 ? 11.1 : 0;

        try {
            physical.integrate(1, acceleration);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromJSON(JSONObject parent) {
        JSONObject j = parent.getJSONObject("thing");

        born = j.getLong("born");
        physical.fromJSON(j);
    }
    public void toJSON(JSONObject parent) {
        JSONObject j = new JSONObject();
        parent.put("thing", j);

        j.put("born", born);
        physical.toJSON(j);
    }

}
