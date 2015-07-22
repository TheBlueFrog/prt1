package com.mike.geodb;

import com.company.Thing;
import com.mike.abstractdb.*;
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
public class WorldState {

    // db record ID
    private long id = -1;

    // world clock
    private long tick = 0;

    // things in the world
    private List<Thing> things;

    public List<Thing> getThings() {
        return things;
    }

    // recover our state from the DB
    public WorldState(Connection db) {
        // assume one, and only one in DB
        things = new ArrayList<>();

        PreparedStatement s = null;
        try {
            String q = String.format("select * from WorldState where (_id = %d) order by _id DESC limit 1", id);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                // xfer data
                tick = rs.getLong(1);
            } else {
                juJuIfNoWorld(db);
//                throw new IllegalStateException("Failed to load or create record, _id: " + id);
            }
        } catch (SQLException e) {
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

    // do the juju to materialize a new world, or throw something
    private void juJuIfNoWorld(Connection db) throws SQLException {
        PreparedStatement s = null;
        try {
            id = -1;

            List<String> v = new ArrayList<String>();

            putDefaultWorld(db, 0, defaultThings ());

            // look for it
            String q = String.format("select _id from WorldState order by _id DESC limit 1");
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                xferFromDB (rs);
                return;
            } else
                throw new IllegalStateException("Failed to insert and recover _id");
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

    private void putDefaultWorld(Connection db, long tick, List<Thing> things) throws SQLException {
        PreparedStatement s = null;
        String q = String.format("insert into WorldState (tick) values (%d)", tick);

        s = db.prepareStatement(q);
        s.executeUpdate();
        s.close();
    }


    @Override
    public String toString()
    {
        return toJSON().toString();
    }

    public WorldState(JSONObject j) throws JSONException
    {
        id = j.getLong("id");
        tick = j.getLong("tick");
    }

    public JSONObject toJSON()
    {
        JSONObject j = new JSONObject();
        try
        {
            j.put("id", id);
            j.put("tick", tick);

            String jsonText = j.toString();
        }
        catch (JSONException e)
        {
            try
            {
                j.put("Error", new com.mike.abstractdb.Error("", e.getClass().getSimpleName(), e.getMessage()));
            }
            catch (JSONException e1)
            {
                e1.printStackTrace();
            }
        }
        return j;
    }

}
