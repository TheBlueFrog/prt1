package com.mike;

import java.sql.SQLException;

import com.mike.geodb.DB;
import com.mike.geodb.FenceRecord;
import com.mike.geodb.InstallationRecord;

/**
 * add a new fence to an installation
 */
public class PutNewFence extends DBInterface
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * 	"OpCode"			PutNewFence
	 * 	"InstallationGUID"	owning installation GUID
	 * 	"DisplayName"		user visible fence name
	 *  "Latitude"			location
	 *  "Longitude"
	 *  "Radius"			in meters
	 *  "Events"			enter and/or leave
	 *  "URI"				URL app can use?
	 *  
	 *  returns updated InstallationRecord
	 */
	
	public PutNewFence(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		PutNewFence x = new PutNewFence(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		long id = DB.getGuidID(mDB, mParams.get("InstallationGUID"));
		InstallationRecord install = new InstallationRecord(mDB, id);
		
		install.addFence(new FenceRecord(mDB, 
				install.mGuid,
				mParams.get("DisplayName"),
				Double.parseDouble(mParams.get("Latitude")), 
				Double.parseDouble(mParams.get("Longitude")), 
				Double.parseDouble(mParams.get("Radius")),
				Integer.parseInt(mParams.get("Events")), mParams.get("URI")));
		
		return install.toString();
	}	
}
