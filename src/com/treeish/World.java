package com.treeish;

import com.mike.util.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public World(Connection db, String tableName) throws SQLException {
        super(db, tableName);
        tick = 0;

        String q = String.format("insert into %s (tick) values (%d)",
                tableName,
                tick);
        SQL.insert(db, q);

        // re-read the insert to get the row id, the rest should
        // be the same (ha)
 //       this.rowID = ((World) SQL.readLast(db, tableName)).rowID;

        // read the tree
    }

    static public World load (Connection db, String tableName, long id) throws SQLException {
        return (World) SQL.readLast(db, tableName);
    }


}
