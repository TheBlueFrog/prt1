package com.company;

import com.mike.GetWorldState;
import com.mike.WorldState;
import com.mike.lambda1;
import com.mike.util.SQL;
import com.treeish.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static List<Person> roster = new ArrayList<Person>();

    public static void main(String[] args) {

        {
            long id = 3;

            {
                TreeNode<Fred> r = null;
                r = new TreeNode<Fred>(new Fred(3.14));
                System.out.println(String.format("%f", r.data.v));

                r.addChild(new Fred(2.1));
                System.out.println(String.format("%f", r.children.get(0).data.v));

            }

            String fname = "fred.db";
            SQL.dbfname = "data/fred.db";
            try {
                SQL.init ();
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            TreeNode<DBRecord> treeRoot = null;
            try {
                if (World.exists("World", id)) {
                    treeRoot = new TreeNode<DBRecord>(World.load("World", id));
                }
                else {
                    // build tree one-time
                    treeRoot = new TreeNode<DBRecord>(new World(SQL.DB, "World"));

                    for (Thing t : Thing.defaultThings()) {
                        treeRoot.addChild(t);
                    }

                    Iterator<TreeNode<DBRecord>> iter = treeRoot.iterator();
                    iter.forEachRemaining(t -> t.data.update());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println(String.format("%s", treeRoot.data.toString()));




//            Comparable<DBRecord> searchCriteria;
//            searchCriteria = new Comparable<DBRecord>() {
//                @Override
//                public int compareTo(DBRecord o) {
//                    return 0;
//                }
//            };
//
//            treeRoot.findTreeNode(searchCriteria);
////            TreeNode<DBRecord> found = treeRoot.findTreeNode(searchCriteria);
////
////            System.out.println("Found: " + found);
        }



//        WorldState world = new GetWorldState(args).process().get(0);
//
//        System.out.println("Finished init, start ticking");
//
//        for (int i = 0; i  < 10; ++i)
//            world.getThings().forEach(t -> t.tick());

        System.out.println("Finished");
    }

}
