package models;

import java.util.Comparator;

/**
 * Created by tyler on 3/27/2017.
 * A comparator class for Events
 */

public class EventComparator implements Comparator<Event> {
    public EventComparator() {
    }

    @Override
    public int compare(Event event, Event event2) {
        int comp = compareBirthDeath(event,event2);
        if(comp != 0){
            return comp;
        }

        comp = compareYearAndSpelling(event,event2);
        return comp;
    }
    /**Checking two events, and returning whether one happened earlier than the other. If no dates are provided
     * then we order alpabetically
     * */
    private static int compareYearAndSpelling(Event event, Event event2) {
        if(event.getYear().equals("") && !event2.getYear().equals("")){return 1;}//if there isnt a year in one
        else if(!event.getYear().equals("") && event2.getYear().equals("")){return -1;}//if the other doesnt have year
        else if(event.getYear().equals("") && event2.getYear().equals("")){//else compare spellings
            return event.getEventType().compareTo(event2.getEventType());
        }

        if(event.getYearInt() < event2.getYearInt()){
            return -1;
        }
        else if(event.getYearInt() > event2.getYearInt()){
            return 1;
        }
        else{//might return 1,-1, or zero
            return event.getEventType().compareTo(event2.getEventType());

        }
    }

    /**
     * Compares two events, checking if either is a birth or death event and returning accordingly
     * */
    private static int compareBirthDeath(Event event, Event event2){
        if(event.getEventType().toLowerCase().equals("birth") && !event2.getEventType().toLowerCase().equals("birth") ){
            return -1;
        }
        else if(!event.getEventType().toLowerCase().equals("birth") && event2.getEventType().toLowerCase().equals("birth")){
            return 1;
        }
        if(event.getEventType().toLowerCase().equals("death")){
            return 1;
        }
        else if(event2.getEventType().toLowerCase().equals("death")){
            return -1;
        }
        return 0;
    }
}
