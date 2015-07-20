package com.company;

/**
 * Created by mike on 7/19/2015.
 */
public class Mover {
    static int Position = 0;
    static int Velocity = 1;
    static int Acceleration = 2;
    static int Mass = 3;

    private double[] state;

    public Mover () {
        state = new double[] { 0.0, 0.0, 0.0, 0.0 };
    }

    public void print () {
        System.out.printf("Mover state: %5.2f kg, %5.2f m, %5.2f m/s, %5.2f m/s/s\n",
                state[Mass], state[Position], state[Velocity], state[Acceleration]);
    }
    public void tick(Force instA) {
        move(instA);
        print ();
    }

    private void move (Force force) {
        // integrate for one time unit
        // F = ma;
        double newA = force.force / state[Mass];
        double newV = state[Velocity] + ((newA - state[Acceleration]) / 2.0);
        double newP = state[Position] + ((newV - state[Velocity]) / 2.0);
        // update
        state[Position] = newP;
        state[Velocity] = newV;
        state[Acceleration] = newA;
    }

    public void setMass(double mass) {
        this.state[Mass] = mass;
    }
}
