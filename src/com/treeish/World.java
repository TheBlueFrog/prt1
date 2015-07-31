package com.treeish;

import com.company.Thing;
import com.mike.util.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mike on 7/24/2015.
 *
 * CREATE TABLE World (
 *      _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 *      tick INTEGER);

 CREATE TABLE World (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, tick INTEGER);
 */
public class World extends DBRecord {
    private int tick;

    public World (ResultSet rs) throws SQLException {
        super(SQL.DB, "World");
        rowID = rs.getInt("_id");
        tick = rs.getInt("tick");
    }

    /**
     * insert the world into the DB
     *
     * @param db
     * @param tableName
     * @throws SQLException
     */
    public World(Connection db, String tableName) throws SQLException {
        super(db, tableName);
        tick = 0;

        String q = String.format("insert into %s (tick) values (%d)",
                tableName,
                tick);
        this.rowID = SQL.insert(db, q);


        // re-read the insert to get the row id, the rest should
        // be the same (ha)
 //       this.rowID = ((World) SQL.readLast(db, tableName)).rowID;

        // read the tree
    }

    static public World load (Connection db, String tableName, long id) throws SQLException {
        return (World) SQL.readLast(db, tableName);
    }

    /**
     * load (recursively) the entire tree rooted at the given table/rowID
     *
     * @param   tableName
     * @param   id
     * @return  root of the new world
     * @throws  SQLException
     */
    static public DBRecord load (String tableName, long id) throws SQLException {
//        SQL.
        DBRecord w = SQL.readLast(SQL.DB, tableName);
        read (tableName, )
    }


    /**
     * initialize the world
     *
     * @param id the DB rowID of the world to load
     * @return a world, in the same state as it was when saved
     */
    public static TreeNode<DBRecord> init(long id) {

        TreeNode<DBRecord> wt = null;

        try {
            if ( ! World.exists("World", id)) {
                // setup tree
                // save to db
                wt = new TreeNode<DBRecord>(new World(SQL.DB, "World"));

                for (Thing t : Thing.defaultThings(wt)) {
                    wt.addChild(t);
                }

                Iterator<TreeNode<DBRecord>> iter = wt.iterator();
                iter.forEachRemaining(t -> {
                    System.out.println(String.format("Created %s", t.data.getClass().getSimpleName()));
                });

                write(SQL.DB, "World", wt);

                /* dump all of that and do the normal read */
                wt = null;
            }

            // build the whole tree from the DB
            wt = new TreeNode<DBRecord>(World.read("World", id));

//          w.loadChildren(wt);   // recursively
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        Iterator<TreeNode<DBRecord>> iter = wt.iterator();
        iter.forEachRemaining(t -> {
            System.out.println(String.format("Loaded %s", t.data.getClass().getSimpleName()));
        });

        return wt;
    }

    @Override
    protected void getData(List colNames, List values) {
        super.getData(colNames, values);

        colNames.add("tick");
        values.add(Long.toString(tick));
    }

    private static void write(Connection db, String tableName, TreeNode<DBRecord> root) throws SQLException {

        // write myself
        root.data.write (db, tableName);

        // and recurse through children
        root.children.forEach( r -> {
            try {
                write(db, r.data.tableName, r);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
