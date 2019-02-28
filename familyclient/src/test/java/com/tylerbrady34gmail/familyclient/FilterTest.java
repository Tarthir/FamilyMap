package com.tylerbrady34gmail.familyclient;

import com.tylerbrady34gmail.familyclient.FilterRecycler.FilterRows;
import com.tylerbrady34gmail.familyclient.Models.Filter;
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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by tyler on 4/12/2017.
 * Tests our filter
 */

public class FilterTest {

    @Before
    public void setup() {
        //I explain why this function call below is here in my event sorting tests
        Model.clear("YO I guess you can clear the model, And I'm really not sure How you got this");
        ArrayList<Event> eventList = new ArrayList<>();
        Location loc = new Location("100", "150", "city", "country");
        eventList.add(new Event("event4", "user", "id", loc, "Joined Army", "2005"));
        eventList.add(new Event("event1", "user", "id", loc, "birth", "1977"));
        eventList.add(new Event("event3", "user", "id", loc, "marriage", "2000"));
        eventList.add(new Event("event2", "user", "id", loc, "baptism", "1978"));
        eventList.add(new Event("event5", "user", "id", loc, "death", "2017"));


        PeopleResult result = new PeopleResult(new ArrayList<>(Arrays.asList(new Person("id2", "user", "dad", "daddy", "m", "", "", "id3"),
                new Person("id3", "user", "mom", "mommy", "f", "", "", "id2"),
                new Person("id4", "user", "spouse", "wife", "f", "", "", "id"),
                new Person("id", "user", "fName", "lName", "m", "id2", "id3", "id4"))));
        EventsResult eventsResult = new EventsResult(eventList);

        Model.setValues(new ArrayList<>(Arrays.asList(result, eventsResult)));
    }

    @Test
    public void TestFiltersSetup() {
        //testing that the filters get setup properly
        ArrayList<FilterRows> rows = Filter.getInstance().getFilterRows();
        for (FilterRows row : rows) {
            //makes sure all our event types become filters
            assertTrue(Model.getEventTypes().contains(row.getmType()) || isOtherFilters(row.getmType()));
        }
    }
    @Test
    public void TestFilterToggling(){
        //This passing proves that toggling the filters causes them to go on and off
        ArrayList<FilterRows> rows = Filter.getInstance().getFilterRows();
        for (FilterRows row : rows) {
            assertTrue(row.isShowing());//should be showing as a default
            row.toggleFilter();
            assertFalse(row.isShowing());//now should be off
            row.toggleFilter();
            assertTrue(row.isShowing());//on again
        }
    }

    @Test
    public void TestFindingSpecificFilterOn(){
        //should be on
        assertTrue(Filter.getInstance().checkFatherSide());
        assertTrue(Filter.getInstance().checkMotherSide());
        assertTrue(Filter.getInstance().isFilterShowing("birth"));
        assertTrue(Filter.getInstance().isFilterShowing("marriage"));
        assertTrue(Filter.getInstance().isFilterShowing("death"));
        assertTrue(Filter.getInstance().isFilterShowing("baptism"));
    }

    @Test
    public void TestFindingSpecificFilterOff(){
        ArrayList<FilterRows> rows = Filter.getInstance().getFilterRows();
        for (FilterRows row : rows) {
            row.toggleFilter();
        }
        //should be on
        assertFalse(Filter.getInstance().checkFatherSide());
        assertFalse(Filter.getInstance().checkMotherSide());
        assertFalse(Filter.getInstance().isFilterShowing("birth"));
        assertFalse(Filter.getInstance().isFilterShowing("marriage"));
        assertFalse(Filter.getInstance().isFilterShowing("death"));
        assertFalse(Filter.getInstance().isFilterShowing("baptism"));
    }
    /**Checks to make sure that if the filter being tested isn't an eventType filter, then it is just one of the defaults
     * @return boolean
     * */
    private boolean isOtherFilters(String s) {
        //My default filters
        return s.equals("Show Females") || s.equals("Show Males") || s.equals("Father's Side") || s.equals("Mother's Side");
    }
}
