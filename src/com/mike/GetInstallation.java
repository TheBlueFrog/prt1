package com.mike;

import java.sql.SQLException;

import com.mike.geodb.DB;
import com.mike.geodb.InstallationRecord;

/**
 * retrieve a named installation
 */
public class GetInstallation extends DBInterface
{
	/**
	 * "OpCode"				GetInstallation
	 * "InstallationGUID" 	installGuid to retrieve
	 */
	
	public GetInstallation(String[] args) 
	{
		super(args);
	}

	public static void main(String[] args)
	{
		GetInstallation x = new GetInstallation(args);
		x.process();
	}
	
	public String innerProcess() throws SQLException
	{
		String installGuid = mParams.get("InstallationGUID");
		InstallationRecord out = null;

		long id = DB.getGuidID(mDB, installGuid);

//		List<Long> v = DB.getAllInstallationIDs(mDB);
//		for (Long id : v) 
		if (id >= 0)
		{
			InstallationRecord r = new InstallationRecord(mDB, id);
			if (r.mGuid.equals(installGuid))
			{
				out = r;
	
//				if (r.mFences.size() == 0)
//					r.addFence (new FenceRecord(mDB, id, "testFence", -122.6, 45.3, 101.1, 3, "a fence url"));
				return out.toString();
			}
		}

		return new com.mike.abstractdb.Error ("1", "No record found", "").toString();
	}
}
