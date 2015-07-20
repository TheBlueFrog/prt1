package com.mike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mike.geodb.DB;
import com.mike.geodb.FenceRecord;

/**
 * retrieve known fences for an installation
 */
public class GetFences
{
	/**
	 * app does a GET to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = GetFencess we get here
	 * we output all known Installation records, stringified
	 * 
	 * args
	 * [0] = installation GUID 
	 */
	
	public static void main(String[] args)
	{
	    try
	    {
			Class.forName("org.sqlite.JDBC");

		    Connection db = null;

		    try
		    {
		      db = DriverManager.getConnection("jdbc:sqlite:fencenotification.db");

		      List<FenceRecord> v = DB.getAllFences(db, args[0]);
		      for (FenceRecord r : v)
		      {
		    	  System.out.println(r.toString());
		      }
		    }
		    catch(SQLException e)
		    {
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(db != null)
		        	db.close();
		      }
		      catch(SQLException e)
		      {
		        System.out.println(e);
		      }
		    }
		}
	    catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace(System.out);
		}
	}

	static private void Log(String s)
	{
		System.out.println(s);		
	}
	
}
