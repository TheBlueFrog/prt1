package com.mike;

import com.mike.DBInterface;
import com.mike.geodb.DB;
import com.mike.geodb.HistoryRecord;
import com.mike.geodb.WorldState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 7/21/2015.
 */
public class GetWorldState extends DBInterface2<WorldState>
{
    public GetWorldState(String[] args)
    {
        super(args);
    }

//	public static void main(String[] args)
//	{
//		GetHistory x = new GetHistory(args);
//		x.process();
//	}

    List<WorldState> innerProcess() throws SQLException
    {
        List<WorldState> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        {
            WorldState r = new WorldState(mDB);

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

