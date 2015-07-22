package com.mike.util;

import org.json.JSONObject;

/** and ordinary error that can be JSONed */
public class Error
{
	public String mCode;
	public String mShortMessage;
	public String mLongMessage;
	
	public Error(String code, String shortMsg, String longMsg) 
	{
		mCode = code;
		mShortMessage = shortMsg;
		mLongMessage = longMsg;
	}

	@Override
	public String toString() 
	{
		return toJSON().toString();
	}

	public Error(JSONObject j)
	{
		mCode = j.getString("Code");
		mShortMessage = j.getString("ShortMessage");
		mLongMessage = j.getString("LongMessage");
	}
	public JSONObject toJSON() 
	{
		JSONObject j = new JSONObject();
		j.put("Code", mCode);
		j.put("ShortMessage", mShortMessage);
		j.put("LongMessage", mLongMessage);
		
		String jsonText = j.toString();
		return j;
	}

}
