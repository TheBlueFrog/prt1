package com.mike;

import com.mike.abstractdb.AbstractRecord;
import com.mike.geodb.HistoryRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class DBInterface extends com.mike.BasicInterface
{
	private static final String TAG = DBInterface.class.getSimpleName();

	protected static String mDBPath = "data/history.db";

	protected Connection mDB = null;

	public DBInterface(String[] args) 
	{
		super (args);
	}

	public List<? extends AbstractRecord> process()
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    try
		    {
		      mDB = DriverManager.getConnection("jdbc:sqlite:" + mDBPath);

		      List<? extends AbstractRecord> s = innerProcess ();
		      
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
	
	abstract List<HistoryRecord> innerProcess() throws SQLException;
}
