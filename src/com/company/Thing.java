package com.company;

import com.mike.WorldState;
import com.mike.util.PhysicalObject;
import com.mike.util.SQL;
import com.treeish.DBRecord;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/20/2015.
 * <p>
 *  CREATE TABLE Thing
 *      (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 *      ownerID INTEGER,
 *      born INTEGER
 *  );

 *  CREATE TABLE Thing (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ownerID INTEGER, born INTEGER);
 */
public class Thing
        extends DBRecord
{
    static private final String TAG = Thing.class.getSimpleName();
    static private final String tableName = "Thing";

    private long born = 0;
    private PhysicalObject phyisical;

    static public List<Thing> defaultThings() {
        List<Thing> list = new ArrayList<>();
//        list.sort();
        list.add(new Thing());
        return list;
    }

    public Thing () {
        super(SQL.DB, "Thing");
        born = WorldState.getTick ();

        setPhysics(0, 0, 0, 0);
    }
    public Thing (ResultSet rs) throws SQLException {
        super(SQL.DB, "Thing");
        dbToField(rs);
    }

    public long getAge() {
        return WorldState.getTick() - born;
    }

    static public void loadThings (Connection db, WorldState w) throws SQLException {
        String q = String.format("select * from %s where ( ownerID = %d", tableName, w.getID());
        SQL.read(db, q, Thing::constructFromDB);
    }

    public void dbToField(ResultSet rs) throws SQLException {
        born = ((ResultSet) rs).getLong(1);
    }

    static public void constructFromDB(ResultSet rs) throws SQLException {
        Thing t = new Thing (rs);
    }

    private void setPhysics(double position, double velocity, double acceleration, double mass) {
        phyisical = new PhysicalObject(position, velocity, acceleration, mass);
    }

    public void tick() {
        try {
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
     * insert a Thing in the DB
     *
     * @param db
     * @param parentID should be the owning WorldState...
     * @param dbToField
     * @throws SQLException
     */
    public void insert(Connection db, long parentID, SQL.DBtoField dbToField) throws SQLException {
        String q = String.format("insert into %s (ownerID, born) values (%d, %d)",
                tableName,
                parentID,
                WorldState.getTick());
        SQL.insert(db, q, this::dbToField);
    }

    public static void insert(Connection db, long parentID, List<Thing> things) throws SQLException {
        for (Thing t : things) {
                t.insert(db, parentID, t::dbToField);
            }
    }

}
