package models;

import java.util.ArrayList;

import models.Person;

/**
 * Created by tyler on 2/23/2017.
 * Creates Person/People from our output
 */

public class PeopleCreator {
    public PeopleCreator() {
    }

    /**
     * Creates multiple people from input
     *
     * @PARAM Arraylist of an Arraylist of Strings holding people data
     * @RETURN An arraylist of people objects
     */
    public ArrayList<Person> createPeople(ArrayList<ArrayList<String>> allPeople) {
        if (allPeople.size() == 0) {
            return null;
        }
        ArrayList<Person> outputPeople = new ArrayList<Person>();
        for (ArrayList<String> arr : allPeople) {
            outputPeople.add(createPerson(arr));
        }
        return outputPeople;
    }

    /**
     * Creates one person from input
     *
     * @PARAM Arraylist of of Strings holding people data
     * @RETURN A person object
     */
    public Person createPerson(ArrayList<String> personData) {
        if (personData == null || personData.size() < 8) {
            return null;
        } else {//return a new person
            return new Person(personData.get(0), personData.get(1), personData.get(2), personData.get(3),
                    personData.get(4), personData.get(5), personData.get(6),personData.get(7));
        }
    }
}
