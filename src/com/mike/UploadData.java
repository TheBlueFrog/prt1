package com.mike;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 */
public class UploadData extends BasicInterface
{
	/**
	 * app does a POST to http://66.211.190.18/cgi-bin/toJava.pl
	 * 
	 * if OpCode = UploadData we get here
	 * we return the toString of the new installation record

	 * args are a map
	 * 
	    params.put("InstallationGUID", installGuid);
		params.put("Timestamp", timeStamp);
		params.put("Data", data);
	 */
	
	public static void main(String[] args)
	{
		UploadData x = new UploadData(args);
		x.process ();
	}
	
	public UploadData(String[] args) 
	{
		super(args);
	}

	public void process() 
	{
		File dir = new File ("/data/uploads", mParams.get("InstallationGUID"));
		dir.mkdirs();
		File f = new File (dir, mParams.get("Timestamp") + ".seq");
		Log("Write to " + f.getPath());
		writeAll (f, mParams.get("Data"));
	}
	
	public void writeAll(File f, String s) 
	{
		FileOutputStream fis;
		try 
		{
			f.createNewFile();
			fis = new FileOutputStream (f);
			fis.write(s.getBytes(), 0, s.length());
			fis.close();
			Log("Wrote " + s.length() + " chars of data");
		}
		catch (IOException e)
		{
			Log("Exception " + e.getMessage());
		}
	}
}
