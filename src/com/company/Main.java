package com.company;

import com.mike.util.SQL;
import com.treeish.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static public TreeNode<TreeData> treeRoot = null;

    public static void main(String[] args) {

        {
//            long id = 3;    // rowID of the current world
//
//            { // setup DB
//                String fname = "fred.db";
//                SQL.dbfname = "data/fred.db";
//                try {
//                    SQL.init();
//                } catch (ClassNotFoundException | SQLException e) {
//                    e.printStackTrace();
//                }
//            }

            try {
                treeRoot = World.init (new File("data/b.json"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Finished init, start ticking");

        for (int i = 0; i  < 10; ++i) {
            System.out.print(String.format("%d: ", ((World) treeRoot.data).getTick()));

            ((World) treeRoot.data).tick();

//            System.out.println("");
        }

        System.out.println("Finished");
    }

}
