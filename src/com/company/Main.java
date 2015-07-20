package com.company;

import com.mike.GetInstallations;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Person> roster = new ArrayList<Person>();

//    public static void printPersonsWithPredicate(
//            List<Person> roster,
//            Predicate<Person> tester) {
//        for (Person p : roster) {
//            if (tester.test(p)) {
//                p.printPerson();
//            }
//        }
//    }

    public static void main(String[] args) {
//        Mover m = new Mover();
//        m.setMass(11.0);
//
//        movers.add(m);
//
//        for (int i = 0; i < 10; ++i)
//            tick();

//        printPersonsWithPredicate(
//                roster,
//                p -> p.getSex() == Person.Sex.MALE
//                    && p.getAge() >= 18
//                    && p.getAge() <= 25);

        GetInstallations x = new GetInstallations(args);
        x.process();
    }

    private static List<Mover> movers = new ArrayList<Mover>();

    static private void tick() {
        Force force = new Force ();
        force.force =  100.0;

        movers.forEach(p -> p.tick(force));
    }
}
