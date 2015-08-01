package com.mike.util;

import com.treeish.TreeData;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mike on 7/23/2015.
 */
public class PhysicalObject {

    static private String TAG = PhysicalObject.class.getSimpleName();

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
        JSONObject j = parent.getJSONObject(TAG);

        position = j.getDouble("position");
        velocity = j.getDouble("velocity");
        acceleration = j.getDouble("acceleration");
        mass = j.getDouble("mass");
    }
    public JSONObject toJSON(JSONObject parent) {
        JSONObject j = new JSONObject();
        parent.put(TAG, j);

        j.put("position", position);
        j.put("velocity", velocity);
        j.put("acceleration", acceleration);
        j.put("mass", mass);

        return j;
    }

    public double getPosition() {
        return position;
    }
    public double getVelocity() {
        return velocity;
    }
    public double getAcceleration() {
        return acceleration;
    }
    public double getMass() {
        return mass;
    }

    /**
     * do linear integration for a time step
     * @param dt size of the time step
     * @param externalForce being applied to the object
     */
    public void integrate(long dt, double externalForce) {
        System.out.print(String.format("%s -> ", toJSON(new JSONObject()).toString()));

        double ea = externalForce / mass;
        double a = acceleration + (ea / 2.0);
        double v = velocity + (a / 2.0);
        double p = position + (v / 2.0);

        position = p;
        velocity = v;
        acceleration = 0; // a;  reset for next integration time

        System.out.println(String.format("%s", toJSON(new JSONObject()).toString()));
    }

}
