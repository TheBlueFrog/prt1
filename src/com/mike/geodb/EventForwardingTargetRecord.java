package com.mike.geodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mike.abstractdb.AbstractEventForwardingTargetRecord;

/**
CREATE TABLE EventForwardingTargets (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, EvenForwardingID INTEGER, InstallationID INTEGER);
 
 */
public class EventForwardingTargetRecord extends AbstractEventForwardingTargetRecord
{
	public EventForwardingTargetRecord(Connection db, long eventForwarding, long installID) throws SQLException
	{
		super();

		PreparedStatement s = null;
		try
		{
			mID = -1;
			mEventForwardingID = eventForwarding;
			mInstallationID = installID;

			String q = String.format("insert into EventForwardingTargets (EvenForwardingID, InstallationID) "
					+ "values (%d, %d)",
					mEventForwardingID,
					mInstallationID);
			
			s = db.prepareStatement(q);
			s.executeUpdate();
			s.close();

			q = String.format("select _id from EventForwardingTargets order by _id DESC limit 1");                				
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

	public EventForwardingTargetRecord(ResultSet rs) throws SQLException 
	{
		mID = rs.getLong(1);
		mEventForwardingID = rs.getLong(2);
		mInstallationID = rs.getLong(3);
	}
}
