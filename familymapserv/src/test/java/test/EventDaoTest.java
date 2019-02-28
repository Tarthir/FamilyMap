package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.EventDao;
import models.EventsCreator;
import dataAccess.UserDao;
import infoObjects.EventRequest;
import models.AuthToken;
import models.Event;
import models.Location;
import models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/23/2017.
 * Tests our eventDao
 */

public class EventDaoTest {
    private EventDao eDao;
    private DataBase db;
    private Connection connection;
    private AuthToken authToken;
    @Before
    public void setUp() throws IOException, SQLException {
        eDao = new EventDao();
        db = new DataBase();
        try {
            connection = db.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.createTables(connection);

        //setup
        User user = new User("name","password","email","first","last","m","personID");
        new UserDao().register(user);
        authToken = new AuthToken();
        new AuthTokenDao().insertAuthToken("name",authToken);
    }

    @After
    public void tearDown() throws SQLException {
        connection = db.openConnection();
        try {
            db.dropTables(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    @Test
    public void testInsertEvent() {
        try {
            Event event = new Event("eventID", "userID", "personID", new Location( "213.7", "123.7", "Provo","USA"), "1994", "Birth");
            assertTrue(eDao.insertEvent(event));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetEvent() {
        try {
            Event event = new Event("eventID", "userID", "personID", new Location( "213.7", "123.7", "Provo","USA"), "1994", "Birth");
            EventsCreator eventsCreator = new EventsCreator();

            assertTrue(eDao.insertEvent(event));
            ArrayList<String> expected = new ArrayList<>(Arrays.asList("eventID", "userID", "personID", "Birth", "1994", "Provo", "213.7", "123.7", "USA"));
            EventRequest request = new EventRequest("eventID",authToken.getAuthToken());
            ArrayList<String> result = eDao.getEvent(request);
            assertEquals(expected, result);
            Event e = eventsCreator.createEvent(expected);
            Event e2 = eventsCreator.createEvent(result);
            assertEquals(e,e2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteEvent() {
        try {
            System.out.print("");
            assertTrue(eDao.insertEvent(new Event("eventID", "userID", "personID",new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth")));
            assertTrue(eDao.insertEvent(new Event("eventID2", "userID", "personID2",new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth")));
            assertTrue(eDao.insertEvent(new Event("eventID3", "userID", "personID3",new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth")));
            assertTrue(eDao.insertEvent(new Event("eventID4", "userID2", "personID4",new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth2")));
            assertTrue(eDao.deleteEvents("userID"));
            EventRequest request = new EventRequest("eventID",authToken.getAuthToken());
            ArrayList<String> actual = eDao.getEvent(request);
            assertEquals(null, actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetEventFail() {
        try {
            Event event = new Event("eventID", "name", "personID",new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth");
            assertTrue(eDao.insertEvent(event));
            ArrayList<String> expected = new ArrayList<>(Arrays.asList("eventID", "name", "personID", "1994", "Birth","Provo","213.7", "123.7", "USA"));
            EventRequest request = new EventRequest("eventID2",authToken.getAuthToken());
            assertNotEquals(expected, eDao.getEvent(request));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetEvents() {
        try {
            Event event = new Event("eventID", "userID", "personID", new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth");
            Event event2 = new Event("eventID2", "userID", "personID2", new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth");
            Event event3 = new Event("eventID3", "userID", "personID3", new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth");
            Event event4 = new Event("eventID4", "userID2", "personID2", new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth2");
            ArrayList<String> expected = new ArrayList<>(Arrays.asList("eventID", "userID", "personID", "Birth", "1994",  "123.7","Provo",  "213.7", "USA"));
            ArrayList<String> expected2 = new ArrayList<>(Arrays.asList("eventID2", "userID", "personID2", "Birth","1994", "123.7","Provo",  "213.7", "USA"));
            ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("eventID3", "userID", "personID3", "Birth","1994", "123.7","Provo",  "213.7",  "USA"));
            assertTrue(eDao.insertEvent(event));
            assertTrue(eDao.insertEvent(event2));
            assertTrue(eDao.insertEvent(event3));
            assertTrue(eDao.insertEvent(event4));
            ArrayList<ArrayList<String>> events = new ArrayList<>(Arrays.asList(expected, expected2, expected3));
            assertEquals(events, eDao.getEvents("userID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testgetEventsFail() {
        try {
            Event event = new Event("eventID", "userID", "personID", new Location( "213.7", "123.7","Provo", "USA"), "1994", "Birth");
            Event event2 = new Event("eventID2", "userID", "personID2", new Location( "213.7", "123.7","Provo", "USA"), "1994", "Birth");
            Event event3 = new Event("eventID3", "userID", "personID3", new Location( "213.7", "123.7","Provo", "USA"), "1994", "Birth");
            Event event4 = new Event("eventID4", "userID2", "personID2", new Location( "213.7", "123.7","Provo", "USA"), "1994", "Birth2");
            ArrayList<String> expected = new ArrayList<>(Arrays.asList("eventID", "userID", "personID", "1994", "Birth",  "213.7", "123.7","Provo", "USA"));
            ArrayList<String> expected2 = new ArrayList<>(Arrays.asList("eventID2", "userID", "personID2", "1994", "Birth",  "213.7", "123.7","Provo", "USA"));
            ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("eventID3", "userID", "personID3", "1994", "Birth",  "213.7", "123.7","Provo",  "USA"));
            assertTrue(eDao.insertEvent(event));
            assertTrue(eDao.insertEvent(event2));
            assertTrue(eDao.insertEvent(event3));
            assertTrue(eDao.insertEvent(event4));
            ArrayList<ArrayList<String>> events = new ArrayList<>(Arrays.asList(expected, expected2, expected3));
            assertNotEquals(events, eDao.getEvents("userID3"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
