package com.mike;

import com.company.Log;
import com.company.Thing;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/21/2015.
 * <p>
 * CREATE TABLE WorldState
 * (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 * tick INTEGER);
 */
public class WorldState extends MyJSON {

    static private final String TAG = WorldState.class.getSimpleName();

    // db record ID
    private long id = -1;

    // world clock
    private long tick = 0;

    // things in the world
    private List<Thing> things = new ArrayList<>();

    public List<Thing> getThings() {
        return things;
    }
    public WorldState(JSONObject j) throws JSONException {
        super(j);
    }


    // recover our state from the DB
    public WorldState(Connection db) {
        super();

        // assume one, and only one in DB

        PreparedStatement s = null;
        try {
//            String q = String.format("select * from WorldState where (_id = %d) order by _id DESC limit 1", id);
            // find newest one
            String q = String.format("select * from %s order by _id DESC limit 1", TAG);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                // xfer data
                tick = rs.getLong(1);
            } else {
                materializeWorld(db);
//                throw new IllegalStateException("Failed to load or create record, _id: " + id);
            }

            unitTest();
        }
        catch (SQLException e) {
                e.printStackTrace(System.out);
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
        }
    }

    private void materializeWorld(Connection db) throws SQLException {
        PreparedStatement s = null;
        try {
            // setup world values
            id = -1;
            things = defaultThings();

            xferToDB(db);

            // look for it
            String q = String.format("select _id from %s order by _id DESC limit 1", TAG);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                xferFromDB (rs);
                return;
            } else
                throw new IllegalStateException(String.format("Failed to insert and recover _id table %s", TAG));
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
        }
    }

    private List<Thing> defaultThings() {
        List<Thing> list = new ArrayList<>();
        list.add(new Thing());
        return list;
    }

    private void xferFromDB(ResultSet rs) throws SQLException {
        tick = rs.getLong(1);
    }

    // write ourself out to the DB
    private void xferToDB(Connection db) throws SQLException {
        PreparedStatement s = null;
        String q = String.format("insert into %s (tick) values (%d)",
                TAG,
                tick);
        s = db.prepareStatement(q);
        s.executeUpdate();
        s.close();
    }

    private void unitTest() {
        testJson();
    }

    private void testJson() {
        JSONObject j = toJSON();
        WorldState w = new WorldState(j);

        if ( ! ((id == w.id) && (tick == w.tick)))
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
