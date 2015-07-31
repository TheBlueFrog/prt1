package com.mike.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mike on 7/23/2015.
 */
public class PhysicalObject {

    double position;
    double velocity;
    double acceleration;
    double mass;

    public PhysicalObject (double position, double velocity, double acceleration, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
    }

    public PhysicalObject(JSONObject j) throws JSONException {
        fromJSON(j);
    }

    public void fromJSON(JSONObject parent) {
        JSONObject j = parent.getJSONObject("physics");

        position = j.getDouble("position");
        velocity = j.getDouble("velocity");
        acceleration = j.getDouble("acceleration");
        mass = j.getDouble("mass");
    }
    public void toJSON(JSONObject parent) {
        JSONObject j = new JSONObject();
        parent.put("physics", j);

        j.put("position", position);
        j.put("velocity", velocity);
        j.put("acceleration", acceleration);
        j.put("mass", mass);
    }

    public void integrate(long dt, double externalForce) {
        double ea = externalForce / mass;
        double a = acceleration + (ea / 2.0);
        double v = velocity + (a / 2.0);
        double p = position + (v / 2.0);

        position = p;
        velocity = v;
        acceleration = a;
    }
}
