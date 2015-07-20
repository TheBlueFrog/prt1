package com.mike.abstractdb;

import org.json.JSONObject;


public class AbstractEventForwardingTargetRecord extends com.mike.abstractdb.AbstractRecord
{
	public long mID;
	
	/** record ID of EventForwardingRecord we belong to */
	public long mEventForwardingID;

	/** installation to forward event to */
	public long mInstallationID;
	
	protected AbstractEventForwardingTargetRecord()
	{
	}
	
	@Override
	public String toString() 
	{
		return toJSON().toString();
	}
	
	public AbstractEventForwardingTargetRecord(JSONObject j)
	{
		mID = j.getLong("id");
		mEventForwardingID = j.getLong("EventForwardingID");
		mInstallationID = j.getLong("InstallationID");
	}
	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		j.put("id", mID);
		j.put("EventForwardingID", mEventForwardingID);
		j.put("InstallationID", mInstallationID);
		
		String jsonText = j.toString();
		return j;
	}
}
