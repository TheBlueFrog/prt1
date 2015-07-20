package com.mike.geodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mike.abstractdb.AbstractEventForwardingRecord;
import com.mike.abstractdb.AbstractEventForwardingTargetRecord;

/**
	CREATE TABLE EventForwardings
	    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	    FenceID INTEGER,
	    InstallationID INTEGER,
	    IncomingEventType INTEGER);
 
 */
public class EventForwardingRecord extends AbstractEventForwardingRecord
{
//	protected Connection mDB;

	public EventForwardingRecord(Connection db, long installID, String fenceGUID, int eventType) throws SQLException
	{
		super();
		long fenceID = DB.getFenceIDFromGUID(db, fenceGUID);

		PreparedStatement s = null;
		try
		{
			mID = -1;
			mFenceID = fenceID;
			mInstallationID = installID;
			mIncomingEventType = eventType;

			String q = String.format("insert into EventForwardings (FenceID, InstallationID, IncomingEventType) "
					+ "values (%d, %d, %d)",
					mFenceID,
					mInstallationID,
					mIncomingEventType);
			
			s = db.prepareStatement(q);
			s.executeUpdate();
			s.close();

			q = String.format("select _id from EventForwardings order by _id DESC limit 1");                				
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				mID = rs.getLong(1);
			else
				throw new IllegalStateException("Failed to insert and recover _id");
		}
		finally
		{
			if (s != null)
				try 
				{
					s.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace(System.out);
				}
		}

	}

	public EventForwardingRecord(Connection db, ResultSet rs) throws SQLException 
	{
		super();
		
		mID = rs.getLong(1);
		mFenceID = rs.getLong(2);
		mInstallationID = rs.getLong(3);
		mIncomingEventType = rs.getInt(4);

		mTargets = getForwardingTargets(db);
	}

//	public EventForwardingRecord(ResultSet rs) throws SQLException 
//	{
//		super();
//
//		mID = rs.getLong(1);
//		mFenceID = rs.getLong(2);
//		mInstallationID = rs.getLong(3);
//		mIncomingEventType = rs.getInt(4);
//		mTargets = getForwardingTargets(db);
//	}
//
	public List<AbstractEventForwardingTargetRecord> getForwardingTargets (Connection db) throws SQLException
	{
		List<AbstractEventForwardingTargetRecord> v = DB.getEventForwardingTargets(db, mFenceID);
		return v;
	}
}
