package com.mike;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mike on 7/22/2015.
 */
abstract public class MyJSON {

    public MyJSON() {
    }
    public MyJSON(JSONObject j) {
        fromJSON(j);
    }

    abstract protected void fromJSON(JSONObject j);
    abstract protected void toJSON(JSONObject j);


    @Override
    public String toString() {
        return toJSON().toString();
    }

    protected JSONObject toJSON() {
        JSONObject j = new JSONObject();
        try
        {
            toJSON(j);
            String jsonText = j.toString();
        }
        catch (JSONException e)
        {
            try
            {
                j.put("Error", new com.mike.util.Error("", e.getClass().getSimpleName(), e.getMessage()));
            }
            catch (JSONException e1)
            {
                e1.printStackTrace();
            }
        }
        return j;
    }

}
