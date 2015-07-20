package com.mike;

import java.util.HashMap;
import java.util.Map;

public class BasicInterface
{

	protected Map<String, String> mParams = new HashMap<String, String>();

	public BasicInterface(String[] args)
	{
		extractParams(args);
	}

	protected void extractParams(String[] args)
	{
		for (int i = 0; i < args.length; i += 2)
		{
			mParams.put(args[i], args[i+1]);
//			Log(String.format("%s = %s", args[i], args[i+1]));
		}
	}

	protected void Log(String s)
	{
		System.out.println(s);		
	}

}
