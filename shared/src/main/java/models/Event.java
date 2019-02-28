package models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by tyler on 2/10/2017.
 * This object is an Event, IE: marriage, birth.
 */

public class Event{
    /**The user this event belongs to*/
    private String descendant;
    /**The unique event ID for this event*/
    private String eventID;
    /**The person this event belongs to*/
    private String personID;
    /**The latitude this event took place at*/
    private String latitude;
    /**The longitude this event occured at*/
    private String longitude;
    /**The country of this location*/
    private String country;
    /**The city of this location*/
    private String city;
    /**The eventType of this event*/
    private String eventType;
    /**The year this event took place*/
    private String year;


    public Event(String eventID, String descendant, String personID, Location location, String eventType, String year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.city = location.getCity();
        this.country = location.getCountry();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.eventType = eventType.toLowerCase();//make it lower case
        this.year = year;
    }


    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()){return false;}
        Event other = (Event)o;
        if(!other.getCountry().equals(this.getCountry())){return false;}
        if(!other.getEventType().equals(this.getEventType())){return false;}
        if(!other.getDescendant().equals(this.getDescendant())){return false;}
        if(!other.getEventID().equals(this.getEventID())){return false;}
        if(!other.getYear().equals(this.getYear())){return false;}
        if(!other.getPersonID().equals(this.getPersonID())){return false;}
        if(!other.getCity().equals(this.getCity())){return false;}
        if(!other.getLatitude().equals(this.getLatitude())){return false;}
        if(!other.getLongitude().equals(this.getLongitude())){return false;}
        return true;
    }

    @Override
    public String toString() {
        return getEventType() +": " + getCity() +"," + getCountry() +"(" + getYear() + ")";
    }

    public String getEventID() {
        return eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public String getYear() {
        return year;
    }

    public int getYearInt() {
        return Integer.parseInt(year);
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
