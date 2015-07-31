package com.company;

import com.mike.util.SQL;
import com.treeish.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Person> roster = new ArrayList<Person>();

    public static void main(String[] args) {

        {
            long id = 3;    // rowID of the current world

            { // setup DB
                String fname = "fred.db";
                SQL.dbfname = "data/fred.db";
                try {
                    SQL.init();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }

            TreeNode<DBRecord> treeRoot = World.init (id);

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
