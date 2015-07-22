package com.mike;

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

    List<WorldState> innerProcess() throws SQLException
    {
        List<WorldState> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        {
            WorldState r = new WorldState(mDB);

            list.add(r);
        }

        return list;
    }
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


