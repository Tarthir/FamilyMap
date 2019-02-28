package com.tylerbrady34gmail.familyclient.Models;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import infoObjects.EventsResult;
import infoObjects.PeopleResult;
import models.Event;
import models.EventComparator;
import models.Person;

/**
 * Created by tyler on 3/20/2017.
 * A Singleton class for our Model
 */
public class Model {
    /**Our one instance of the model object*/
    private static Model ourInstance;
    /**A map with personIds and keys and People as values*/
    private static Map<String,Person> people;
    /**A map with EventIds as keys and events as values*/
    private static Map<String,Event> events;
    /**A map with People IDs as keys and a List of events as a value*/
    private static Map<String,List<Event>> persnEvntMap;
    /**Our current settings*/
    private static Settings settings;
    /**Our current Filter*/
    private static Filter filter;
    /**A set of Strings which are are event types*/
    private static Set<String> eventTypes;
    /**Our user's person object*/
    private static Person user;
    /**Our user's paternal Ancestors*/
    private static Set<String> paternalAncestors;
    /**OOur user's maternal Ancestors*/
    private static Set<String> maternalAncestors;
    /**A map with A personID as a key and a List of their children as the value*/
    private static Map<String,List<Person>> personChildren;
    /**Holds the colors ofr the different event markers*/
    private static Map<String,Float> eventColors = new TreeMap<>();

    /**Current map type*/
    private static MapType currMapType;
    /**Curr port*/
    private static String portNum;

    /**Curr host*/
    private static String IPAdress;
    static{
        initialize();
    }

    private Model() {

    }

    private static void initialize() {
        people = new TreeMap<>();
        events = new TreeMap<String, Event>();
        persnEvntMap = new TreeMap<String, List<Event>>();
        settings = new Settings();
        filter = Filter.getInstance();
        eventTypes = new TreeSet<>();
        paternalAncestors = new TreeSet<>();
        maternalAncestors = new TreeSet<>();
        personChildren = new TreeMap<>();
        currMapType = MapType.Normal;

    }
    //I felt bad making a clear function to make my tests work, So it needs a password. Which is
    //in plaintext right here as you can see....so not secure at all but it makes me feel better..
    //And I guess i dont care enough to change the tests
    public static void clear(String permissions){
        if(permissions.equals("YO I guess you can clear the model, And I'm really not sure How you got this")) {
            paternalAncestors = null;
            maternalAncestors  = null;
            persnEvntMap = null;
            personChildren = null;
            eventTypes = null;
            events = null;
            people = null;
            filter = null;
        }
        initialize();
    }


    public static Model getInstance() {
        if(ourInstance == null){
            ourInstance = new Model();
            return ourInstance;
        }
        return ourInstance;
    }

    public static MapType getMapType(){return currMapType; }

    public static void setMapType(MapType mapType){currMapType = mapType;}

    public static String getIPAdress() {
        return IPAdress;
    }

    public static String getPortNum() {
        return portNum;
    }

    public static Map<String, List<Person>> getPersonChildren() {
        return personChildren;
    }

    public static Map<String, Person> getPeople() {
        return people;
    }

    public static Map<String, Event> getEvents() {
        return events;
    }

    public static Map<String, List<Event>> getPersnEvntMap() {
        return persnEvntMap;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static Filter getFilter() {
        return filter;
    }

    public static Set<String> getEventTypes() {
        return eventTypes;
    }

    public static Person getUser() {
        return user;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public static Map<String, Float> getEventColors() {
        return eventColors;
    }

    /**
     * Sets the values of our model objects
     * @param values the people and events related to the user
     * */
    public static void setValues(ArrayList<Object> values) {
        //setup people
        PeopleResult pResult = (PeopleResult) values.get(0);
        ArrayList<Person> peeps = pResult.getPeople();
        //Set the User
        user = peeps.get(peeps.size()-1);
        //set people Map
        for(Person person : peeps){
            people.put(person.getPersonID(),person);
        }
        setupPeopleVariables();

        //set up events
        EventsResult eResult = (EventsResult) values.get(1);
        ArrayList<Event> evnts = eResult.getEvents();
        //setup map of events and eventIDs
        for (Event event : evnts) {
            events.put(event.getEventID(),event);
        }
        setUpEventVariables();
        Filter.getInstance().setUpEventFilterRows();
        setUpMarkerColors();

    }
    /**sets up a map which holds what colors each type of event should be*/
    private static void setUpMarkerColors() {
        float [] markerColors = Utils.getMarkerColors();
        int counter = 0;
        for(String eventType : eventTypes){
            if(!eventColors.containsKey(eventType)){
                eventColors.put(eventType,markerColors[counter]);
                counter++;
            }
        }
    }

    /**Sets up the variables involving events
     **/
    private static void setUpEventVariables() {
        Set<String> eventIDs = events.keySet();
        for(String ID : eventIDs){//for every event

            String personID = events.get(ID).getPersonID();
            if(persnEvntMap.containsKey(personID)){//if this key is already in here
                persnEvntMap.get(personID).add(events.get(ID));//add the event
                eventTypes.add(events.get(ID).getEventType().toLowerCase());
            }
            else{//the key isnt already in the Map
                LinkedList<Event> listOfEvents = new LinkedList<>();
                listOfEvents.add(events.get(ID));
                //add event Types as well
                eventTypes.add(events.get(ID).getEventType().toLowerCase());
                //add the new List to the map keyed with its person
                persnEvntMap.put(personID,listOfEvents);
            }

        }
        setUpPrsnEventMap();
    }

    /**Sets up the person event map variable*/
    private static void setUpPrsnEventMap() {
        EventComparator comparator = new EventComparator();
        Set<String> keys = persnEvntMap.keySet();
        for(String key : keys){//ordering of the lists of events for each person
            Collections.sort(persnEvntMap.get(key),comparator);//sort
        }
        //Log.d("EventSetup", "done");
    }

    /**Sets up the variables involving people
     **/
    private static void setupPeopleVariables() {
        grabKids(user.getFather(),user.getPersonID());
        grabKids(user.getMother(),user.getPersonID());
        //get the ancestors
        paternalAncestors = setUpFamily(user.getFather(),new TreeSet<String>());
        maternalAncestors = setUpFamily(user.getMother(),new TreeSet<String>());
    }

    /**
     * Sets up paternal ancestors
     * @param person a string ID of the user's father
     * */
    private static TreeSet<String> setUpFamily(String person,TreeSet<String> sideOfFamily) {
        if(!person.equals("")){//do nothing if person doesnt exists
            sideOfFamily.add(person);
            //get this persons parents
            String newFather = people.get(person).getFather();
            String newMother = people.get(person).getMother();

            grabKids(newFather, person);
            grabKids(newMother, person);

            //if the parents exist, keep going down the tree
            if(!newFather.equals("")) {
                sideOfFamily = setUpFamily(newFather, sideOfFamily);
            }
            if(!newMother.equals("")) {
                sideOfFamily = setUpFamily(newMother, sideOfFamily);
            }
        }
        return sideOfFamily;
    }



    /**
     * Maps parents to their kids
     * @param parent  the parent of the kid
     * @param kid the kid of the parent
     * */
    private static void grabKids(String parent,String kid) {
        if(parent.equals("")){return;}
        if(personChildren.containsKey(parent)){//if this parent is already in the list
            if(!personChildren.get(parent).contains(people.get(kid))) {//if this kid is not already in the list
                personChildren.get(parent).add(people.get(kid));
            }
        }
        else{//make a new list to put the parent/children in
            LinkedList<Person> list = new LinkedList<>();
            list.add(people.get(kid));
            personChildren.put(parent, list);
        }
    }

    public static void setPortNum(String portNum) {
        Model.portNum = portNum;
    }

    public static void setIPAdress(String IPAdress) {
        Model.IPAdress = IPAdress;
    }
}
