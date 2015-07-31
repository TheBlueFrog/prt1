package com.treeish;

import com.company.Thing;
import com.mike.util.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/24/2015.
 */
public abstract class DBRecord<T>
{
    private Connection db;
    protected String tableName = "";
    protected long rowID;

    static public boolean exists (String tableName, long id) throws SQLException {
        // probe table for record
        return SQL.tableHasRow(SQL.DB, tableName, id);
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

    protected void getData(List<String> colNames, List<String> values) {
        // we never write the rowID column, only read
//        colNames.add("_id");
//        values.add(Long.toString(this.rowID));
    }

    public void update() throws SQLException {
        List<String> colNames = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        getData(colNames, values);
        SQL.update (tableName, colNames, values);
    }

    public void delete () {
    }

    public void write(Connection db, String tableName) throws SQLException {
        List<String> colNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        getData(colNames, values);
        SQL.update (tableName, colNames, values);
    }


    where is the tree linkage info in the DB? better?
    why a db?
    just read/write to a JSON file


    public void setRowID(long rowID) {


    }

    private List<? extends DBRecord> load(Who who) throws SQLException {
        // find our children
        String q = String.format("select * from %s where ( ownerID = %d)", who.toString(), rowID);
        return SQL.load(SQL.DB, q, who);
    }

    /**
     * read the children of this tree node
     * and add to the tree
     *
     * @param parent
     * @throws SQLException
     */
    public void loadChildren(TreeNode<DBRecord> parent) throws SQLException {
        // do things
        List<? extends DBRecord> a = load(Who.Thing);
        for(DBRecord r : a) {
            TreeNode<DBRecord> child = parent.addChild(r);
            r.loadChildren(child);
        }
    }

    public static DBRecord factory(Who who, ResultSet rs) throws SQLException {
        if (who.equals("World"))
            return new World(rs);
        if (who.equals("Thing"))
            return new Thing(rs);

        return null;
    }

    public long getRowID() {
        return rowID;
    }


    public enum Who {Thing, World };

    public static DBRecord factory(Connection db, String tableName, ResultSet rs) throws SQLException {
        if (tableName.equals("World"))
            return new World(rs);
        if (tableName.equals("Thing"))
            return new Thing(rs);

        return null;
    }
}
