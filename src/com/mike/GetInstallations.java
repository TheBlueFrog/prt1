package com.mike;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;

import com.mike.geodb.DB;
import com.mike.geodb.InstallationRecord;

/**
 * retrieve all known installations
 */
public class GetInstallations extends DBInterface
{
	/**
	 * app does a GET to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = GetInstallations we get here
	 * we output all known Installation records, stringified
	 * 
	 * no args
	 */
	
	public GetInstallations(String[] args) 
	{
		super(args);
	}

//	public static void main(String[] args)
//	{
//		GetInstallations x = new GetInstallations(args);
//		x.process();
//	}

	public String innerProcess() throws SQLException
	{
		JSONArray list = new JSONArray();

		StringBuilder sb = new StringBuilder();
		List<Long> v = DB.getAllInstallationIDs(mDB);
		for (Long i : v) 
		{
			InstallationRecord r = new InstallationRecord(mDB, i);

			list.put(r.toJSON());

//			if (r.mFences.size() == 0)
//				r.addFence (new FenceRecord(mDB, i, "testFence", -122.6, 45.3, 101.1, 3, "a fence url"));
		}

//		{	// TEST code
//			JSONTokener parser = new JSONTokener(list.toString());
////			try 
//			{
//				JSONArray j = (JSONArray) parser.nextValue();
//				for (int i = 0; i < j.length(); ++i)
//				{
//					InstallationRecord ni = new InstallationRecord((JSONObject) j.get(i));
//				}
//				
//				Log("Sanity test passed");
//			}
////			catch (ParseException e) 
////			{
////				e.printStackTrace();
////			}
//		}
		
		return list.toString();
	}
}
