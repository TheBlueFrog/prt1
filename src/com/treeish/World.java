package com.treeish;

/**
 * Created by mike on 7/24/2015.
 */
public class World extends DBRecord {
    private final int tick;

    public World(String tableName) {
        super(tableName);
        tick = 0;

        // push into db
    }

    static public World load (long id) {
        return null;
    }

    public DBRecord insert (String q) {
        // q is the whole statement
        return null;
    }

}
