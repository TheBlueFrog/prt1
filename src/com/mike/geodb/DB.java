package com.mike.geodb;

import com.mike.abstractdb.AbstractEventForwardingTargetRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class DB
{
	// these column indexes are 1-based, unlike Android's SQLite implementation 
	// where they are zero-based

	/*
	
	Installations
		id
		InstallGUID
		DisplayName
		GCMRegistrationID
	
CREATE TABLE Fences
    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
    InstallationID INTEGER, 
    Guid TEXT, 
    DisplayName TEXT,
 Latitude NUMBER, Longitude NUMBER, 
 Radius NUMBER, 
 Events INTEGER, 
 URI TEXT);
	
CREATE TABLE EventForwardings
    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
    FenceID INTEGER,
    InstallationID INTEGER,
    IncomingEventType INTEGER
    );
		
CREATE TABLE EventForwardingTargets (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, EvenForwardingID INTEGER,InstallationID INTEGER);
	
	*/
	
	/**
	 * @param installGuid
	 * @return the row ID in the Installations table of the matching GUID, -1 if none
	 * @throws SQLException
	 */
	static public long getGuidID(Connection db, String installGuid) throws SQLException
	{
		PreparedStatement s = null;
		try
		{
			// get the ID
			String q = "select * from Installations where ( Guid = '" + installGuid + "');";
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
			{
				return rs.getLong(1);
			}
			return -1;
		}
		finally
		{
			if (s != null)
				s.close();
		}
	}

	/**
	 * @param id
	 * @return the installGUID from the Installations row, null if none
	 * @throws SQLException
	 */
	static public String getInstallationGuid(Connection db, long id) throws SQLException
	{
		PreparedStatement s = null;
		try
		{
			// get the ID
			String q = "select * from Installations where ( _id = " + id + " );";
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				return rs.getString(2);
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (s != null)
				s.close();
		}
		return null;
	}

	private static String extractGUID(String body)
	{
		// ...8ff84996-6ae4-43b1-bef7-9b183076c1e4...
		Pattern p = Pattern.compile(".*([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}).*");
		Matcher m = p.matcher(body);
		if (m.matches())
		{
			String guid = m.group(1);
			return guid;
		}
		return "";
	}
	
	static public List<Long> getAllInstallationIDs(Connection db) throws SQLException
	{
		PreparedStatement s = null;
		try
		{
			List<Long> v = new ArrayList<Long>();

			String q = "select * from Installations";
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				v.add(rs.getLong(1));
			}
			return v;
		}
		finally
		{
			if (s != null)
				s.close();
		}
	}
	static public List<String> getAllInstallationGuids(Connection db) throws SQLException
	{
		PreparedStatement s = null;
		try
		{
			List<String> v = new ArrayList<String>();

			String q = "select * from Installations";
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				v.add(rs.getString(2));
			}
			return v;
		}
		finally
		{
			if (s != null)
				s.close();
		}
	}

	public static List<FenceRecord> getAllFences(Connection db, String installation) throws SQLException
	{
		PreparedStatement s = null;
		
		try 
		{
			List<FenceRecord> v = new ArrayList<FenceRecord>();
			long id = getGuidID(db, installation);
			String q = String.format("select * from Fences where (InstallationID = %d)", id);
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				v.add(new FenceRecord(rs));
			}
			return v;
			
		} 
		finally
		{
			if (s != null)
				s.close();
		}
	}

	public static List<EventForwardingRecord> getAllEventForwardings(
			Connection db, String installation) throws SQLException 
	{
		PreparedStatement s = null;
		
		try 
		{
			List<EventForwardingRecord> v = new ArrayList<EventForwardingRecord>();
			long id = getGuidID(db, installation);
			String q = String.format("select * from EventForwardings where (InstallationID = %d)", id);
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				v.add(new EventForwardingRecord(db, rs));
			}
			return v;
			
		} 
		finally
		{
			if (s != null)
				s.close();
		}
	}

	public static List<AbstractEventForwardingTargetRecord> getEventForwardingTargets(
			Connection db, long fenceID) throws SQLException 
	{
		PreparedStatement s = null;
		
		try 
		{
			List<AbstractEventForwardingTargetRecord> v = new ArrayList<AbstractEventForwardingTargetRecord>();
			String q = String.format("select * from EventForwardingTargets where (FenceID = %d)", fenceID);
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				v.add(new EventForwardingTargetRecord(rs));
			}
			return v;
			
		} 
		finally
		{
			if (s != null)
				s.close();
		}
	}

	public static long getFenceIDFromGUID(Connection db, String fenceGUID) throws SQLException
	{
		PreparedStatement s = null;
		
		try 
		{
			String q = String.format("select * from Fences where (Guid = %d)", fenceGUID);
			s = db.prepareStatement(q);
			ResultSet rs = s.executeQuery();
			while (rs.next())
			{
				FenceRecord r = new FenceRecord(rs);
				return r.mID;
			}
			return -1;
		} 
		finally
		{
			if (s != null)
				s.close();
		}
	}

}
