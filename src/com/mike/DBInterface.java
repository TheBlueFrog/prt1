package com.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBInterface extends com.mike.BasicInterface
{
	private static final String TAG = DBInterface.class.getSimpleName();

	protected static String mDBPath = "data/history.db";

	protected Connection mDB = null;

	public DBInterface(String[] args) 
	{
		super (args);
	}

	public void process()
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    try
		    {
		      mDB = DriverManager.getConnection("jdbc:sqlite:" + mDBPath);

		      String s = innerProcess ();
		      
		      Log(s);
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
	}
	
	abstract String innerProcess () throws SQLException;
}
