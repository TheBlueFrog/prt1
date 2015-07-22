package com.company;

import com.mike.GetWorldState;
import com.mike.WorldState;

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

//        List<HistoryRecord> worldHistory = new GetHistory(args).process();
        WorldState world = new GetWorldState(args).process().get(0);

     //   List<? extends Thing> things = getThings(worldHistory);

        // start time ticking
        while (true)
            world.getThings().forEach(t -> t.tick());
    }

//    private static List<? extends Thing> getThings(WorldState world) {
//        List<Thing> things = new ArrayList<>();
//        processForThings(world,
//                t -> t instanceof Thing,
//                t -> things.add(t)
//                );
//        worldHistory.forEach(t -> t.);
//        return null;
//    }
//
//    public static void processForThings(
//            WorldState w,
//            Predicate<Thing> tester,
//            Consumer<Thing> block) {
//        for (Thing t : a) {
//            if (tester.test(t)) {
//                block.accept(t);
//            }
//        }
//    }

//    private static List<Mover> movers = new ArrayList<Mover>();
//
//    static private void tick() {
//        Force force = new Force ();
//        force.force =  100.0;
//
//        movers.forEach(p -> p.tick(force));
//    }
}
