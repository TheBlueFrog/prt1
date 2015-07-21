package com.mike;

import com.mike.abstractdb.AbstractRecord;
import com.mike.geodb.HistoryRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class DBInterface2<T> extends BasicInterface
{
	private static final String TAG = DBInterface2.class.getSimpleName();

	protected static String mDBPath = "data/history.db";

	protected Connection mDB = null;

	public DBInterface2(String[] args)
	{
		super (args);
	}

	public List<T> process()
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    try
		    {
		      mDB = DriverManager.getConnection("jdbc:sqlite:" + mDBPath);

		      List<T> s = innerProcess ();
		      
		      return s;
		    }
		    catch(SQLException e)
		    {
		      Log(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(mDB != null)
		          mDB.close();
		      }
		      catch(SQLException e)
		      {
		        Log(e.getMessage());
		      }
		    }
		}
	    catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace(System.out);
		}
		return null;
	}
	
	abstract List<T> innerProcess() throws SQLException;
}
