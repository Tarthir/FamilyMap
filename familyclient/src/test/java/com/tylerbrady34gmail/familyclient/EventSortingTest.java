package com.tylerbrady34gmail.familyclient;

import com.tylerbrady34gmail.familyclient.Models.FamilyMapServerProxy;
import com.tylerbrady34gmail.familyclient.Models.Model;
import com.tylerbrady34gmail.familyclient.ui.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infoObjects.EventsResult;
import infoObjects.LoadRequest;
import infoObjects.LoginRequest;
import infoObjects.PeopleResult;
import infoObjects.RegisterRequest;
import models.Event;
import models.Location;
import models.Person;
import models.User;

/**
 * Created by tyler on 4/12/2017.
 * Test our eventSorting
 */

public class EventSortingTest {

    @Before
    public void setUp(){
        //I felt bad making a clear function to make these test work, So it needs a password. Which is
        //in plaintext right here as you can see....so not secure at all but it makes me feel better... This could
        //of been fixed by adding different events to the model each test and dynamically searching for those ones
       Model.clear("YO I guess you can clear the model, And I'm really not sure How you got this");
    }

    //SEE MY COMPARATOR CLASS IN MY FAMILYSERVER module (model package) to see how I do the sorting..if you want
    @Test
    public void TestEventSuccess(){
        //ALL have years, should all come in order asserted. Ordered mainly by the given years in this case
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event4","user","id",loc,"Joined Army","2005"));
        eventList.add(new Event("event1","user","id",loc,"birth","1977"));
        eventList.add(new Event("event3","user","id",loc,"marriage","2000"));
        eventList.add(new Event("event2","user","id",loc,"baptism","1978"));
        eventList.add(new Event("event5","user","id",loc,"death","2017"));
        PeopleResult result = new PeopleResult(new ArrayList<>(Arrays.asList(new Person("id","user","fName","lName","m"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<>(Arrays.asList(result,eventsResult)));

        List<Event> returnedEvents = Model.getPersnEvntMap().get("id");
        assertEquals("birth",returnedEvents.get(0).getEventType());
        assertEquals("baptism",returnedEvents.get(1).getEventType());
        assertEquals("marriage",returnedEvents.get(2).getEventType());
        assertEquals("joined army",returnedEvents.get(3).getEventType());
        assertEquals("death",returnedEvents.get(4).getEventType());
    }

    @Test
    public void TestEventSomeYears(){
        //tests the sorting of events that dont have years and where death doesnt have a date
        //Birth should still be first and death last, marraige is second b/c it has a year
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event4","user","id",loc,"Joined Army",""));
        eventList.add(new Event("event1","user","id",loc,"birth",""));
        eventList.add(new Event("event3","user","id",loc,"marriage","1999"));
        eventList.add(new Event("event2","user","id",loc,"baptism",""));
        eventList.add(new Event("event5","user","id",loc,"death",""));
        PeopleResult result = new PeopleResult(new ArrayList<Person>(Arrays.asList(new Person("id","user","fName","lName","m"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<Object>(Arrays.asList(result,eventsResult)));

        List<Event> returnedEvents = Model.getPersnEvntMap().get("id");
        assertEquals("birth",returnedEvents.get(0).getEventType());
        assertEquals("marriage",returnedEvents.get(1).getEventType());
        assertEquals("baptism",returnedEvents.get(2).getEventType());
        assertEquals("joined army",returnedEvents.get(3).getEventType());
        assertEquals("death",returnedEvents.get(4).getEventType());
    }

    @Test
    public void TestEventNoYears(){
        //tests the sorting of events that dont have years and where death doesnt have a date
        //Birth should still be first and death last, marriage is second b/c it has a year
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event4","user","id",loc,"Joined Army",""));
        eventList.add(new Event("event1","user","id",loc,"birth",""));
        eventList.add(new Event("event3","user","id",loc,"marriage","1999"));
        eventList.add(new Event("event2","user","id",loc,"baptism",""));
        eventList.add(new Event("event5","user","id",loc,"death",""));
        PeopleResult result = new PeopleResult(new ArrayList<Person>(Arrays.asList(new Person("id","user","fName","lName","m"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<Object>(Arrays.asList(result,eventsResult)));

        List<Event> returnedEvents = Model.getPersnEvntMap().get("id");
        assertEquals("birth",returnedEvents.get(0).getEventType());
        assertEquals("marriage",returnedEvents.get(1).getEventType());
        assertEquals("baptism",returnedEvents.get(2).getEventType());
        assertEquals("joined army",returnedEvents.get(3).getEventType());
        assertEquals("death",returnedEvents.get(4).getEventType());
    }

    @Test
    public void TestNoBirthNoYears(){
        //tests the sorting of events where there is no birth and no Years
        //Order will be alphabetical
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event4","user","id",loc,"Joined Army",""));
        eventList.add(new Event("event3","user","id",loc,"marriage",""));
        eventList.add(new Event("event2","user","id",loc,"baptism",""));
        eventList.add(new Event("event5","user","id",loc,"death",""));
        PeopleResult result = new PeopleResult(new ArrayList<Person>(Arrays.asList(new Person("id","user","fName","lName","m"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<>(Arrays.asList(result,eventsResult)));

        List<Event> returnedEvents = Model.getPersnEvntMap().get("id");

        assertEquals("baptism",returnedEvents.get(0).getEventType());
        assertEquals("joined army",returnedEvents.get(1).getEventType());
        assertEquals("marriage",returnedEvents.get(2).getEventType());
        assertEquals("death",returnedEvents.get(3).getEventType());
    }

    @Test
    public void TestDeathHasYears(){
        //tests the sorting of events whered only death has a year. But it should still be last
        //Order will be alphabetical otherwise
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event4","user","id",loc,"Joined Army",""));
        eventList.add(new Event("event3","user","id",loc,"marriage",""));
        eventList.add(new Event("event2","user","id",loc,"baptism",""));
        eventList.add(new Event("event5","user","id",loc,"death","2049"));
        PeopleResult result = new PeopleResult(new ArrayList<Person>(Arrays.asList(new Person("id","user","fName","lName","m"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<>(Arrays.asList(result,eventsResult)));

        List<Event> returnedEvents = Model.getPersnEvntMap().get("id");

        assertEquals("baptism",returnedEvents.get(0).getEventType());
        assertEquals("joined army",returnedEvents.get(1).getEventType());
        assertEquals("marriage",returnedEvents.get(2).getEventType());
        assertEquals("death",returnedEvents.get(3).getEventType());
    }
}
