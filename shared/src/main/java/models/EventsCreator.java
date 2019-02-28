package models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tyler on 2/23/2017.
 * creates our events
 */

public class EventsCreator {
    public EventsCreator() {
    }

    /**
     * Creates multiple people from input
     *
     * @PARAM Arraylist of an Arraylist of Strings holding event data
     * @RETURN An arraylist of event objects
     */
    public ArrayList<Event> createEvents(ArrayList<ArrayList<String>> allEvents) {
        if (allEvents == null || allEvents.size() == 0) {
            return null;
        }
        ArrayList<Event> outputEvents= new ArrayList<Event>();
        for (ArrayList<String> arr : allEvents) {
            outputEvents.add(createEvent(arr));
        }
        return outputEvents;
    }

    /**
     * Creates one person from input
     *
     * @PARAM Arraylist of of Strings holding event data
     * @RETURN A event object
     */
    public Event createEvent(ArrayList<String> eventData) {
        if (eventData == null || eventData.size() < 9) {//9 is the number of parameters we should have
            return null;
        } else {//return a new person
            LocationCreator locCreator = new LocationCreator();
            ArrayList<String> location = new ArrayList<>(Arrays.asList(eventData.get(6),eventData.get(7),eventData.get(5),eventData.get(8)));
            Location loc = locCreator.createLocation(location);
            return new Event(eventData.get(0), eventData.get(1), eventData.get(2),loc, eventData.get(3),
                    eventData.get(4));
        }
    }
}
