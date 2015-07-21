package com.mike.geodb;

import com.company.Thing;

import java.sql.Connection;
import java.util.List;

/**
 * Created by mike on 7/21/2015.
 */
public class WorldState {

    // TODO read from db table

    // world clock
    private long tick = 0;

    // things in the world
    private List<Thing> things;

    public WorldState(Connection mDB) {
    }

    public List<Thing> getThings () { return things; }
}
