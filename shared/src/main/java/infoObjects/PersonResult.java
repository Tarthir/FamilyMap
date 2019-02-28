package infoObjects;

import models.Person;

/**
 * Created by tyler on 3/6/2017.
 * THe result of querying aperson from the database
 */

public class PersonResult {
    /**Array of people that is our result*/
    private Person person;
    /**Our exception result*/
    private String message;

    public PersonResult(Person person) {
        this.person = person;
    }

    public PersonResult(String message) {
        this.message = message;
    }

    public PersonResult() {
    }
    public Person getPerson() {
        return person;
    }

    public String getMessage() {
        return message;
    }
}
