package com.mike.util;

import com.mike.WorldState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mike on 7/22/2015.
 */
public class SQL {

    private static final String TAG = SQL.class.getSimpleName();

    static public void cleanup(PreparedStatement s) {
        if (s != null)
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
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

}
