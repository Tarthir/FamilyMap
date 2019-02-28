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
import dataAccess.PersonDao;
import dataAccess.UserDao;
import infoObjects.EventsRequest;
import infoObjects.EventsResult;
import models.AuthToken;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import service.EventsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/23/2017.
 * tests our EventsService Class
 */

public class EventsServiceTest {
    private EventDao eDao;
    private EventsService eService;
    private DataBase db;
    private Connection connection;
    private AuthToken  authToken;
    private AuthToken authToken2;
    private Event event;
    private Event event2;
    private Event event3;
    private Event event4;

    @Before
    public void setUp() throws IOException {
        try {
            eService = new EventsService();
            UserDao uDao = new UserDao();
            PersonDao pDao = new PersonDao();
            AuthTokenDao aDao = new AuthTokenDao();
            eDao = new EventDao();
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);
            User user = new User("name", "password", "email", "first", "last", "m","peep");
            User user2 = new User("name2", "password2", "email2", "first2", "last2", "f","peep2");
            ;
            assertTrue(uDao.register(user));
            assertTrue(uDao.register(user2));
            authToken = new AuthToken();
            assertTrue(aDao.insertAuthToken("name", authToken));
            authToken2 = new AuthToken();
            assertTrue(aDao.insertAuthToken("name2", authToken2));
            Person person1 = new Person("personID", "name", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person person2 = new Person("personID2", "name", "fName2", "lName2", "m", "fatherID2", "motherID2", "spouseID2");
            Person person3 = new Person("personID3", "name", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3");
            Person person4 = new Person("personID4", "name2", "fName4", "lName4", "f", "fatherID4", "motherID4", "spouseID4");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
            assertTrue(pDao.insertPerson(person3));
            assertTrue(pDao.insertPerson(person4));
            event = new Event("eventID", "name", "personID", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth");
            event2 = new Event("eventID2", "name", "personID2", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth");
            event3 = new Event("eventID3", "name", "personID3", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth");
            event4 = new Event("eventID4", "name2", "personID4", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth2");
            assertTrue(eDao.insertEvent(event));
            assertTrue(eDao.insertEvent(event2));
            assertTrue(eDao.insertEvent(event3));
            assertTrue(eDao.insertEvent(event4));
        }catch(SQLException e){e.printStackTrace();}
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
    public void testGetEvents() {
       // try {
            ArrayList<Event> expected = new ArrayList<>(Arrays.asList(event, event2, event3));
            ArrayList<Event> expected2 = new ArrayList<>(Arrays.asList(event4));
            EventsRequest request = new EventsRequest(authToken.getAuthToken());
            EventsRequest request2 = new EventsRequest(authToken2.getAuthToken());
            EventsResult resultExpected = new EventsResult(expected);
            EventsResult resultExpected2 = new EventsResult(expected2);
            assertEquals(eService.getEvents(request).getEvents().size(), resultExpected.getEvents().size());
            assertEquals(eService.getEvents(request2).getEvents().size(), resultExpected2.getEvents().size());
        //}catch(SQLException e){e.printStackTrace();}
    }
}
