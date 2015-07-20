package com.company;

import java.time.LocalDate;

/**
 * Created by mike on 7/19/2015.
 */
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public Sex getSex() {
        return gender;
    }
    public int getAge() {
        return 21;
    }

    public void printPerson() {

    }
}
