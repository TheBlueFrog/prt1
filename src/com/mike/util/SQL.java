package com.mike.util;

import com.company.Thing;
import com.mike.WorldState;
import com.treeish.DBRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/22/2015.
 */
public class SQL {

    private static final String TAG = SQL.class.getSimpleName();
    public static String dbfname = "main.db";
    public static Connection DB;

    static public void cleanup(PreparedStatement s) {
        if (s != null)
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
    }

    public static long insert(Connection db, String q) throws SQLException {
        PreparedStatement s = null;
        try {
            s = db.prepareStatement(q);
            s.execute();
            ResultSet rs = s.getGeneratedKeys();
            long rowID = rs.getInt("last_insert_rowid()");
            s.close();
            return rowID;

//            updateRowID (db, );
        } finally {
            cleanup(s);
        }
    }


    public static void insert(Connection db, String q, DBtoField dbToField) throws SQLException {
        PreparedStatement s = null;
        try {
            s = db.prepareStatement(q);
            s.executeUpdate();
            s.close();
        } finally {
            cleanup(s);
        }
    }

    public static void init() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");

        try {
            DB = DriverManager.getConnection("jdbc:sqlite:" + dbfname);
        }
        catch (SQLException e) {
            DB = null;
            throw new SQLException(e);
        }
    }

    public static void update(Object tableName, List<String> colNames, List<String> values) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = String.format("update %s set ", tableName);
            for (int i = 0; i < colNames.size(); /* */) {
                q = q + String.format("%s = %s", colNames.get(i), values.get(i));
                if (++i < colNames.size())
                    q = q + ", ";
                else
                    q = q + ";";
            }
            s = DB.prepareStatement(q);
            s.executeUpdate();
            s.close();
        } finally {
            cleanup(s);
        }
    }

    public static List<DBRecord> loadThings(Connection db, String q) throws SQLException {
        List<DBRecord> list = new ArrayList<>();
        PreparedStatement s = null;
        try {
            s = DB.prepareStatement(q);
            ResultSet rs = s.executeQuery();
//            while (rs.next()) {
//                list.add(new Thing(rs));
//            }
            s.close();
        } finally {
            cleanup(s);
        }
        return list;
    }

    /**
     * interface to let common code move DB data into an object
     */
    static public interface DBtoField {
        /**
         * implementers will be called when a ResultSet is available
         * and asked to update the corresponding in-memory object
         *
         * @param rs
         * @throws SQLException
         */
        public void dbToField(ResultSet rs) throws SQLException;
    }
    /**
     * interface to construct a new object from data in the DB
     */
    static public interface constructfromDB {
        /**
         *
         * @param rs
         * @throws SQLException
         */
        public void construct(ResultSet rs) throws SQLException;
    }

    /**
     *
     * @param db
     * @param q query string to fetch a set of objects
     * @param f function to call to update each object
     * @return crap @TODO fix
     * @throws SQLException
     */
    static public boolean read (Connection db, String q, constructfromDB f) throws SQLException {
        PreparedStatement s = null;
        try {
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                f.construct(rs);
                return true;
            }
        } finally {
            SQL.cleanup(s);
        }

        throw new IllegalStateException(
                String.format("Failed query: %s", q));
//        return false;
    }

    /**
     * read the record with the highest auto-increase value, _id
     *
     * @param db
     * @return
     * @throws SQLException
     */
    static public boolean readLast(Connection db, String tableName, constructfromDB c) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = String.format("select _id, tick from %s order by _id DESC limit 1",
                    tableName);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                c.construct(rs);
                return true;
            }
        } finally {
            SQL.cleanup(s);
        }

        throw new IllegalStateException(String.format("Failed to read last record from table %s", tableName));
//        return false;
    }


    public static DBRecord readLast(Connection db, String tableName) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = String.format("select _id, tick from %s order by _id DESC limit 1",
                    tableName);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return DBRecord.factory(SQL.DB, tableName, rs);
            }
            return null;
        } finally {
            SQL.cleanup(s);
        }

//        throw new IllegalStateException(String.format("Failed to read last record from table %s", tableName));
    }

    public static boolean tableHasRow(Connection db, String tableName, long id) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = String.format("select _id from %s",
                    tableName);
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            return rs.next();
        } finally {
            SQL.cleanup(s);
        }

//        throw new IllegalStateException(String.format("Failed to read last record from table %s", tableName));
    }

    static public List<DBRecord> load(Connection db, String q, DBRecord.Who who) throws SQLException {
        List<DBRecord> list = new ArrayList<>();
        PreparedStatement s = null;
        try {
            s = SQL.DB.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                DBRecord x = DBRecord.factory(who, rs);
                list.add(x);
            }
            s.close();
        } finally {
            SQL.cleanup(s);
        }
        return list;
    }

}
