package com.mike;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mike.geodb.DB;
import com.mike.geodb.HistoryRecord;

/**
 * retrieve all of history
 */
public class GetHistory extends DBInterface
{
	public GetHistory(String[] args)
	{
		super(args);
	}

//	public static void main(String[] args)
//	{
//		GetHistory x = new GetHistory(args);
//		x.process();
//	}

	public List<HistoryRecord> innerProcess() throws SQLException
	{
		List<HistoryRecord> list = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		List<Long> v = DB.getHistoryIDs(mDB);
		for (Long i : v) 
		{
			HistoryRecord r = new HistoryRecord(mDB, i);

			list.add(r);

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
//					HistoryRecord ni = new HistoryRecord((JSONObject) j.get(i));
//				}
//				
//				Log("Sanity test passed");
//			}
////			catch (ParseException e) 
////			{
////				e.printStackTrace();
////			}
//		}
		
		return list;
	}
}
