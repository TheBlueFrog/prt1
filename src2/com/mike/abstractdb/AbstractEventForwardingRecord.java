package com.mike.abstractdb;

import com.mike.geodb.EventForwardingTargetRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AbstractEventForwardingRecord extends AbstractRecord
{
	private static final String TAG = AbstractEventForwardingRecord.class.getSimpleName();
	
	public long mID;
	public long mFenceID;
	public long mInstallationID;
	
	/** event type we match 
	 * 
	 * if this is of type "FenceEnter" then the mFenceID will indicate
	 * which fence we entered, if the fence isn't configured for enter
	 * then this event is not forwarded.
	 * 
	 * otherwise it's null and not used, e.g. event of type "GeoLocation" 
	 * don't care about a fence
	 */
	public int mIncomingEventType;
	
	/** installations to forward to */
	protected List<AbstractEventForwardingTargetRecord> mTargets = new ArrayList<AbstractEventForwardingTargetRecord>();
	
	protected AbstractEventForwardingRecord() 
	{
		super();
	}

	@Override
	public String toString() 
	{
		return toJSON().toString();
	}

	public AbstractEventForwardingRecord(JSONObject j) throws JSONException
	{
		mID = j.getLong("id");
		mFenceID = j.getLong("FenceID");
		mInstallationID = j.getLong("InstallationID");
		mIncomingEventType = j.getInt("IncomingEventType");
	}
	
	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		try
		{
			j.put("id", mID);
			j.put("FenceID", mFenceID);
			j.put("InstallationID", mInstallationID);
			j.put("IncomingEventType", mIncomingEventType);
	
			JSONArray a = new JSONArray();
			for (AbstractEventForwardingTargetRecord t : mTargets)
				a.put(t.toJSON());
			
			String jsonText = j.toString();
		}
		catch (JSONException e)
		{
			try
			{
				j.put("Error", new Error ("", e.getClass().getSimpleName(), e.getMessage()));
			}
			catch (JSONException e1)
			{
				e1.printStackTrace();
			}
		}
		return j;
	}

	public void addTarget(EventForwardingTargetRecord eft)
	{
		mTargets.add (eft);
	}

}
