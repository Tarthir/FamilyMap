package infoObjects;

import java.util.ArrayList;

import models.Person;

/**
 * Created by tyler on 2/23/2017.
 * Our result for getting multiple people
 */

public class PeopleResult {
    /**Array of people that is our result*/
    //@Transient
    private ArrayList<Person> people;
    /**Our exception result*/
    private String message;

    public PeopleResult(ArrayList<Person> people) {
        this.people = people;
    }

    public PeopleResult(String message) {
       this.message = message;
    }

    public PeopleResult() {
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public String getMessage() {
        return message;
    }
}
