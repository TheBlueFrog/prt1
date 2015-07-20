package com.mike;

import java.sql.SQLException;

import com.mike.geodb.EventForwardingRecord;
import com.mike.geodb.EventForwardingTargetRecord;
import com.mike.geodb.InstallationRecord;

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
	 *  returns updated InstallationRecord
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
	
	public String innerProcess() throws SQLException
	{
		long installID = Long.parseLong(mParams.get("InstallationGUID"));
		long forwardingID = Long.parseLong(mParams.get("EventForwardingID"));
		
		InstallationRecord install = new InstallationRecord(mDB, installID);
		
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
