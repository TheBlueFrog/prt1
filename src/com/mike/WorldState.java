package com.mike;

import com.company.Log;
import com.company.Thing;
import com.mike.util.SQL;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.mike.util.SQL.readLast;

/**
 * Created by mike on 7/21/2015.
 * <p>
 * CREATE TABLE WorldState
 * (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 * tick INTEGER);
 */
public class WorldState extends MyJSON {

    static private final String TAG = WorldState.class.getSimpleName();
    static private final String tableName = "WorldState";

    private static WorldState ourself = null;
    public static long getTick () {
        return ourself == null ? 0 : ourself.tick;
    }
    // db record ID
    private long id = 0;

    // world clock
    public long tick = 0;

    // things in the world
    private List<Thing> things = new ArrayList<>();

    public List<Thing> getThings() {
        return things;
    }
    public WorldState(JSONObject j) throws JSONException {
        super(j);
        ourself = this;
    }

    // recover our state from the DB
    static public void start(Connection db) throws SQLException {
        try {
            // load latest one in DB or setup a new DB
            SQL.readLast(db, tableName, WorldState::constructFromDB);
            ourself.unitTest();
        } catch (SQLException | IllegalStateException e) {
            WorldState.insertNewWorld(db);
        }

//        xxxxxxxxxxxx
//        super();
//        ourself = this;
    }

    private void load(Connection db) {
//        String q = String.format("insert into %s (tick) values (%d)",
//                TAG,
////                id,
//                tick);
//
//        SQL.insert(db, q, this::dbToField);
//
//        Thing.insert(db, id, things);

    }

    static private void insertNewWorld(Connection db) throws SQLException {
        PreparedStatement s = null;
        try {
            // setup world values
//            id = -1;
//            insert(db);

            // load latest one in DB, this gets the correct id set since
            // it's autonumber
            readLast(db, tableName, WorldState::constructFromDB);

            // put default things in DB
//            Thing.insert(db, id, Thing.defaultThings(id));

            // and reload one more time
            readLast(db, tableName, WorldState::constructFromDB);
        } finally {
            SQL.cleanup(s);
        }
    }

    private static void constructFromDB(ResultSet rs) {
//        ourself = new WorldState(rs);
    }

    private void addThings(List<Thing> things) {
    }

    /**
     * copy data from a DB ResultSet into the object, or not
     *
     * @param rs
     * @throws SQLException
     */
    public void dbToField(ResultSet rs) throws SQLException {
        id = ((ResultSet) rs).getLong(1);
        tick = ((ResultSet) rs).getLong(2);
    }

    private void insert(Connection db) throws SQLException {
        String q = String.format("insert into %s (tick) values (%d)",
                TAG,
//                id,
                tick);

        SQL.insert(db, q, this::dbToField);

//        Thing.insert(db, id, things);
    }

    private void unitTest() {
        testJson();
    }

    private void testJson() {
        JSONObject j = toJSON();
        WorldState w = new WorldState(j);

        if ( ! ((id == w.id) ))// new world has current time... && (tick == w.tick)))
            Log.e(String.format("%s JSON error", TAG));
    }

    protected void fromJSON(JSONObject j) {
        id = j.getLong("id");
        tick = j.getLong("tick");
    }
    protected void toJSON(JSONObject j) {
        j.put("id", id);
        j.put("tick", tick);
    }


    public Object getID() {
        return id;
    }
}
