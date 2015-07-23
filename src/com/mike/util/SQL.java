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

    static public void insert (Connection db, String q) throws SQLException {
        PreparedStatement s = null;
        try {
            s = db.prepareStatement(q);
            s.executeUpdate();
            s.close();
        } finally {
            cleanup(s);
        }
    }

    static public void cleanup(PreparedStatement s) {
        if (s != null)
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
    }

    /**
     * read the record with the highest auto-increase value, _id
     *
     * @param db
     * @param w
     * @return
     * @throws SQLException
     */
    static public boolean readLast(Connection db, WorldState w) throws SQLException {
        PreparedStatement s = null;
        try {
            String q = String.format("select _id from %s order by _id DESC limit 1", w.getClass().getSimpleName());
            s = db.prepareStatement(q);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                w.dbToField(rs);
                return true;
            }
        } finally {
            SQL.cleanup(s);
        }

        throw new IllegalStateException(String.format("Failed to read last record from table %s", w.getClass().getSimpleName()));
//        return false;
    }

}
