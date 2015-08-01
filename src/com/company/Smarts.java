package com.company;

/**
 * Created by mike on 7/31/2015.
 */
public class Smarts {
    Thing me;

    public Smarts (Thing t) {
        me = t;
    }
    public void tick () {
        if (me.getPhysics().getPosition() > 5) {


        }
    }
}
