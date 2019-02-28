package encode;

import models.Event;
import models.Person;
import models.User;

/**
 * Created by tyler on 3/4/2017.
 * Used to hold data for Loading
 */

public class LoadDataHolder {
    /**
     * An array of User objects
     */
    private User[] users;
    /**
     * An array of Person objects
     */
    private Person[] persons;
    /**
     * An array of Event objects
     */
    private Event[] events;


    public LoadDataHolder(User[] users, Person[] persons, Event[] events) {

        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}


