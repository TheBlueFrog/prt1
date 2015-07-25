package com.company;

import com.mike.GetWorldState;
import com.mike.WorldState;
import com.mike.lambda1;
import com.treeish.DBRecord;
import com.treeish.SampleData;
import com.treeish.TreeNode;
import com.treeish.World;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Person> roster = new ArrayList<Person>();

    public static void main(String[] args) {

        {
            Comparable<DBRecord> searchCriteria = new Comparable<DBRecord>() {
                @Override
                public int compareTo(DBRecord o) {
                    return 0;
                }
            };

            long id = 3;

            TreeNode<? extends DBRecord> treeRoot = null;
            if ( ! World.exists(id)) {
                // build tree one-time
                treeRoot = new TreeNode<World>(new World("World"));
            }
            else {
                treeRoot = new TreeNode<World>(World.load(id));
            }


//            TreeNode<DBRecord> found = treeRoot.findTreeNode(searchCriteria);
//
//            System.out.println("Found: " + found);
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
