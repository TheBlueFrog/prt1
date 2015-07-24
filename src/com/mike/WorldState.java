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
    private final String tableName = "WorldState";

    private static WorldState ourself = null;
    public static long getTick () {
        return ourself.tick;
    }
    // db record ID
    private long id = -1;

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
    public WorldState(Connection db) throws SQLException {
        super();
        ourself = this;

        try {
            // load latest one in DB or setup a new DB
            SQL.readLast(db, this);

            unitTest();
        } catch (SQLException | IllegalStateException e) {
            insertNewWorld(db);
        }
    }

    private void insertNewWorld(Connection db) throws SQLException {
        PreparedStatement s = null;
        try {
            // setup world values
            id = -1;
            things = Thing.defaultThings();

            insert(db);

            // load latest one in DB
            readLast(db, this);
        } finally {
            SQL.cleanup(s);
        }
    }

    void cb (ResultSet rs) throws SQLException {
        dbToField(rs);
    }

    private void insert(Connection db) throws SQLException {
        SQL.insert(db, String.format("insert into %s (tick) values (%d)",
                TAG,
                tick));
        things.forEach(t -> t.insert(db, id, WorldState::dbToField));
    }

    /**
     * copy data from a DB ResultSet into the object, or not
     *
     * @param rs
     * @throws SQLException
     */
    public void dbToField(ResultSet rs) throws SQLException {
        tick = ((ResultSet) rs).getLong(1);
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



}
