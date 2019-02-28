package com.tylerbrady34gmail.familyclient;

import com.tylerbrady34gmail.familyclient.Models.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import infoObjects.EventsResult;
import infoObjects.PeopleResult;
import models.Event;
import models.Location;
import models.Person;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by tyler on 4/12/2017.
 * Tests our family relationships
 */

public class FamilyRelationshipTest {

    @Before
    public void setup(){
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100","150","city","country");
        eventList.add(new Event("event1","user","id",loc,"birth",""));
        PeopleResult result = new PeopleResult(new ArrayList<>(Arrays.asList(new Person("id2","user","dad","daddy","m","","","id3"),
                new Person("id3","user","mom","mommy","f","","","id2"),
                new Person("id4","user","spouse","wife","f","","","id"),
                new Person("id","user","fName","lName","m","id2","id3","id4"))));
        EventsResult eventsResult = new EventsResult(eventList);
        Model.setValues(new ArrayList<Object>(Arrays.asList(result,eventsResult)));
    }
    @Test
    public void TestSpouses(){
        assertEquals(Model.getPeople().get("id").getSpouse(),Model.getPeople().get("id4").getPersonID());//husband/wife
        assertEquals(Model.getPeople().get("id").getPersonID(),Model.getPeople().get("id4").getSpouse());//previous reversed

        assertEquals(Model.getPeople().get("id2").getPersonID(),Model.getPeople().get("id3").getSpouse());//husband/wife
        assertEquals(Model.getPeople().get("id2").getSpouse(),(Model.getPeople().get("id3").getPersonID()));//previous reversed
    }

    @Test
    public void TestNotRightSpouses(){
        assertNotEquals(Model.getPeople().get("id2").getSpouse(),"id");//Not husband/wife
        assertNotEquals("id",Model.getPeople().get("id2").getSpouse());//previous reversed

        assertNotEquals("id",Model.getPeople().get("id3").getSpouse());//Not husband/wife
        assertNotEquals(Model.getPeople().get("id").getSpouse(),"id3");//previous reversed
    }

    @Test
    public void TestKids(){
        //Does dad have his kid as his kid
        assertEquals(Model.getPersonChildren().get("id2").get(0), Model.getPeople().get("id"));
        assertEquals(Model.getPersonChildren().get("id3").get(0), Model.getPeople().get("id"));
    }
    @Test
    public void TestNoKids(){
        assertEquals(Model.getPersonChildren().get("id"),null);
        assertEquals(Model.getPersonChildren().get("id4"),null);
    }

    @Test
    public void TestSidesOfFamily(){
        //mother
        assertTrue(Model.getMaternalAncestors().contains("id3"));
        //father
        assertTrue(Model.getPaternalAncestors().contains("id2"));
        //spouse isnt actually on either side actually since she has no parents....hopefully we dont get data like this
        assertFalse(Model.getMaternalAncestors().contains("id4"));
        assertFalse(Model.getPaternalAncestors().contains("id4"));
        //user isnt on either side
        assertFalse(Model.getPaternalAncestors().contains("id"));
        assertFalse(Model.getMaternalAncestors().contains("id"));
    }
}
