package com.mike.abstractdb;

import org.json.JSONException;
import org.json.JSONObject;


public class AbstractFenceRecord extends AbstractRecord 
{
	private static final String TAG = AbstractFenceRecord.class.getSimpleName();
	
	public long mID;
	public long mInstallationID;
	public String mGuid;
	public String mDisplayName;
	public double mLatitude;
	public double mLongitude;
	public double mRadius;
	public int mEvents;
	public String mURI;

	protected AbstractFenceRecord() 
	{
	}

	@Override
	public String toString() 
	{
		return toJSON().toString();
	}

	public AbstractFenceRecord(JSONObject j) throws JSONException
	{
		mID = j.getLong("id");
		mInstallationID = j.getLong("InstallationID");
		mGuid = j.getString("Guid");
		mDisplayName = j.getString("DisplayName");
		mLatitude = j.getDouble("Latitude");
		mLongitude = j.getDouble("Longitude");
		mRadius = j.getDouble("Radius");
		mEvents = j.getInt("Events");
		mURI = j.getString("URI");
	}

	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		try
		{
			j.put("id", mID);
			j.put("InstallationID", mInstallationID);
			j.put("Guid", mGuid);
			j.put("DisplayName", mDisplayName);
			j.put("Latitude", mLatitude);
			j.put("Longitude", mLongitude);
			j.put("Radius", mRadius);
			j.put("Events", mEvents);
			j.put("URI", mURI);
			
			String jsonText = j.toString();
		}
		catch (JSONException e)
		{
			try
			{
				j.put("Error", new com.mike.util.Error("", e.getClass().getSimpleName(), e.getMessage()));
			}
			catch (JSONException e1)
			{
				e1.printStackTrace();
			}
		}
		return j;
	}
}
