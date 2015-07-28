package com.treeish;

import com.mike.util.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mike on 7/24/2015.
 */
public class DBRecord<T> {
    private Connection db;
    String tableName = "";
    long rowID;


    static public boolean exists (String tableName, long id) throws SQLException {
        // probe table for record
        return SQL.readLast(SQL.DB, tableName) != null;
    }

    static public DBRecord load (String tableName, long id) throws SQLException {
        return SQL.readLast(SQL.DB, tableName);
    }

    public DBRecord insert (String q) {
        // q is the whole statement
        return null;
    }

    protected DBRecord(Connection db, String tableName) {
        this.db = db; //@TODO wrong we need a singleton or transactions...
        this.tableName = tableName;

        //we're abstract don't go into the DB
    }

    public void update () {
        SQL.update (tableName, colName, value);
    }

    public void delete () {
    }

    public enum Who { World };

    public static DBRecord factory(Connection db, String tableName, ResultSet rs) throws SQLException {
        if (tableName.equals("World"))
            return new World(rs);

        return null;
    }
}
