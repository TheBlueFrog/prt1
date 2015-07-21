package com.mike;

import java.sql.SQLException;
import java.util.List;

import com.mike.geodb.DB;
import com.mike.geodb.EventForwardingRecord;
import com.mike.geodb.HistoryRecord;

/**
 * add a new fence to an installation
 */
public class PutNewEventForwarding extends DBInterface
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * 	"OpCode"			PutNewEventForwarding
	 * 	"InstallationGUID"	owning installation GUID
	 * 	"FenceGUID"			which fence this forwarding applies to, or "" for no fence
	 *  "EventType"			one of the Event* values, e.g. EventFenceEnter or GeoLocation
	 *  
	 *  This does not set any targets for the forwarding, use the AddEventForwardingTarget API
	 *  to do that
	 *  
	 *  returns updated HistoryRecord
	 */
	
	public PutNewEventForwarding(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		PutNewEventForwarding x = new PutNewEventForwarding(args);
		x.process();
	}
	
	public List<HistoryRecord> innerProcess() throws SQLException
	{
		long installID = DB.getGuidID(mDB, mParams.get("InstallationGUID"));
		
		HistoryRecord install = new HistoryRecord(mDB, installID);
		
		install.addForwarding(new EventForwardingRecord(mDB, 
				installID,
				mParams.get("FenceGUID"),
				Integer.parseInt(mParams.get("EventType"))
				));
		
		return install.toString();
	}	
}
