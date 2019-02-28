package infoObjects;

import java.util.ArrayList;

import models.Event;

/**
 * Created by tyler on 2/13/2017.
 * Gets the results of our query of the database for all the events of a users family
 */

public class EventsResult {
    /**The array of events*/
    ArrayList<Event> events;
    /**Our exception message*/
    private String message;

    public EventsResult(ArrayList<Event> events) {
        this.events = events;
    }

    public EventsResult(String message) {
        this.message = message;
    }

    public EventsResult() {
    }

    /**@RETURN The events array*/
    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getMessage() {
        return message;
    }
}
