package com.company;

import com.mike.WorldState;
import com.mike.util.PhysicalObject;
import com.mike.util.SQL;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/20/2015.
 */
public class Thing {
    static private final String TAG = Thing.class.getSimpleName();
    private final String tableName = "Thing";


    private PhysicalObject phyisical;

    static public List<Thing> defaultThings() {
        List<Thing> list = new ArrayList<>();
//        list.sort();
        list.add(new Thing());
        return list;
    }


    long born = 0;

    public long getAge() {
        return WorldState.getTick() - born;
    }

    public Thing () {
        born = WorldState.getTick ();

        setPhysics(0, 0, 0, 0);
    }

    public Thing (Connection db, long ownerID) throws SQLException {
        String q = String.format("select * from %s where ( ownerID = %d", tableName, ownerID);
     //   SQL.read(db, q, this::dbToField);
    }

    public void dbToField(ResultSet rs) throws SQLException {
        born = ((ResultSet) rs).getLong(1);
    }

    private void setPhysics(double position, double velocity, double acceleration, double mass) {
        phyisical = new PhysicalObject(position, velocity, acceleration, mass);
    }

    public void tick() {
        try {
            SQL.read(null, null, this::dbToField);
            phyisical.integrate(1, 11.1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fromJSON(JSONObject j) {
        born = j.getLong("born");
        phyisical = new PhysicalObject(j);
    }
    protected void toJSON(JSONObject j) {
        j.put("born", born);
        phyisical.toJSON(j);
    }

    /**
     * insert a new Thing into the DB given the owner
     *
     * @param db
     * @param ownerID
     * @throws SQLException
     */
    public void insert(Connection db, long ownerID) throws SQLException {
        SQL.insert(db, String.format("insert into %s (ownerID, born) values (%d, %d)",
                tableName,
                ownerID,
                born));
    }

    public void insert(Connection db, long id, SQL.DBtoField dbToField) {
    }
}
