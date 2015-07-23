package com.company;

import com.mike.GetWorldState;
import com.mike.WorldState;
import com.mike.lambda1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Person> roster = new ArrayList<Person>();

    public static void main(String[] args) {

//        lambda1.mymain();

        WorldState world = new GetWorldState(args).process().get(0);

        while (true)
            world.getThings().forEach(t -> t.tick());
    }

}
