package com.mike;

import java.sql.SQLException;
import java.util.List;

import com.mike.geodb.EventForwardingRecord;
import com.mike.geodb.EventForwardingTargetRecord;
import com.mike.geodb.HistoryRecord;

/**
 * add a new fence to an installation
 */
public class AddEventForwardingTarget extends DBInterface
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * 	"OpCode"			AddEventForwardingTarget
	 * 	"EventForwardingID"	the EventForwarding ID
	 * 	"InstallationID"	the target installation 
	 *  
	 *  returns updated HistoryRecord
	 */
	
	public AddEventForwardingTarget(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		AddEventForwardingTarget x = new AddEventForwardingTarget(args);
		x.process();
	}
	
	public List<HistoryRecord> innerProcess() throws SQLException
	{
		long installID = Long.parseLong(mParams.get("InstallationGUID"));
		long forwardingID = Long.parseLong(mParams.get("EventForwardingID"));
		
		HistoryRecord install = new HistoryRecord(mDB, installID);
		
		EventForwardingRecord ef = install.getEventForwarding(forwardingID);
		
		if (ef != null)
		{
			ef.addTarget(new EventForwardingTargetRecord(mDB, 
					forwardingID,
					installID));
		}
		
		return install.toString();
	}	
}
