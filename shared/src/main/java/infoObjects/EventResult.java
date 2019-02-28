package infoObjects;

import models.Event;

/**
 * Created by tyler on 2/13/2017.
 * Contains the reference to one particular event of a users family member
 */

public class EventResult {
    /**
     * The userID of this event
     * */
    private String descendant;
    /**The event object*/
    private Event event;
    /**The personID string*/
    private String personID;
    /**An error message*/
    private String message;


    public EventResult(String descendant,Event event, String personID) {
        this.descendant = descendant;
        this.event = event;
        this.personID = personID;
    }

    public EventResult(String message) {
        this.message = message;
    }

    public EventResult() {
    }

    public Event getEvent() {
        return event;
    }

    public String getPersonID() {
        return personID;
    }
    public String getUserID(){return descendant;}

    public String getMessage() {
        return message;
    }
}
