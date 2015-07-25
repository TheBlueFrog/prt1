package com.treeish;

/**
 * Created by mike on 7/24/2015.
 */
public class DBRecord<T> {
    String tableName = "";
    long rowID;


    static public boolean exists (long id) {
        // probe table for record
        return true;
    }

    static public DBRecord load (long id) {
        return null;
    }

    public DBRecord insert (String q) {
        // q is the whole statement
        return null;
    }


    public DBRecord(String tableName) {
        this.tableName = tableName;
        insert("");
    }

    public void update () {
    }

    public void delete () {
    }

}
