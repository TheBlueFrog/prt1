package com.mike.geodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mike.abstractdb.AbstractFenceRecord;

/**
class to wrap SQLite3 table row

CREATE TABLE Fences
    (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
    InstallationID INTEGER, 
    Guid TEXT, 
    DisplayName TEXT,
 	Latitude NUMBER, Longitude NUMBER, 
 	Radius NUMBER, 
 	Events INTEGER, 
 	URI TEXT);
	 
*/
public class FenceRecord extends AbstractFenceRecord
{
	/**
	 * insert a new FenceRecord into the db
	 * 
	 * @param db
	 * @param installationGuid
	 * @param displayName
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @param events
	 * @param uri
	 * @throws SQLException
	 */
	public FenceRecord(Connection db, 
			String installationGuid, 
			String displayName, 
			double latitude, double longitude, double radius,
			int events, 
			String uri) throws SQLException
	{
		this(db, DB.getGuidID(db, installationGuid), displayName, latitude, longitude, radius, events, uri);
	}
	/**
	 * insert a new FenceRecord into the db
	 * 
	 * @param db
	 * @param installationID
	 * @param displayName
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @param events
	 * @param uri
	 * @throws SQLException
	 */
	public FenceRecord(Connection db, 
			long installationID, 
			String displayName, 
			double latitude, double longitude, double radius,
			int events, 
			String uri) throws SQLException
	{
		super();
		
		PreparedStatement s = null;
		try
		{
			mID = -1;
			mInstallationID = installationID;
			mGuid = UUID.randomUUID().toString();
			mDisplayName = displayName;
			mLatitude = latitude;
			mLongitude = longitude;
			mRadius = radius;
			mEvents = events;
			mURI = uri;

			List<String> v = new ArrayList<String>();

			String q = String.format("insert into Fences (InstallationID, Guid, DisplayName, Latitude, Longitude, Radius, Events, URI) "
					+ "values (%d, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%d\", \"%s\")",
					mInstallationID,
					mGuid,
					mDisplayName,
					Double.toString(mLatitude),
					Double.toString(mLongitude),
					Double.toString(mRadius),
					mEvents,
					mURI);
			
			s = db.prepareStatement(q);
			s.executeUpdate();
			s.close();

			q = String.format("select _id from Fences order by _id DESC limit 1");                				
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
	
	
	public FenceRecord(ResultSet rs) throws SQLException 
	{
		mID = rs.getLong(1);
		mInstallationID = rs.getLong(2);
		mGuid = rs.getString(3);
		mDisplayName = rs.getString(4);
		mLatitude = rs.getDouble(5);
		mLongitude = rs.getDouble(6);
		mRadius = rs.getDouble(7);
		mEvents = rs.getInt(8);
		mURI = rs.getString(9);
	}

	public static List<AbstractFenceRecord> getFencesForInstallation(Connection db, long installID) throws SQLException
	{
		PreparedStatement s = null;
		
		try 
		{
			List<AbstractFenceRecord> v = new ArrayList<AbstractFenceRecord>();
			String q = String.format("select * from Fences where (InstallationID = %d)", installID);
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
	
}
