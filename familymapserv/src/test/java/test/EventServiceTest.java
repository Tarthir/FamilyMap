package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.EventDao;
import dataAccess.UserDao;
import infoObjects.EventRequest;
import infoObjects.EventResult;
import models.AuthToken;
import models.Event;
import models.Location;
import models.User;
import service.EventService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/23/2017.
 * tests our EventService class
 */

public class EventServiceTest {
    private EventDao eDao;
    private EventService eService;
    private DataBase db;
    private AuthToken authToken;
    private AuthToken authToken2;
    private Connection connection;

    @Before
    public void setUp() throws IOException {
        try {
            eService = new EventService();
            eDao = new EventDao();
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);

            authToken = new AuthToken();
            authToken2 = new AuthToken();
            User user = new User("userID","password","email","first","last","f","personID");
            assertTrue(new UserDao().register(user));
            assertTrue(new AuthTokenDao().insertAuthToken("userID",authToken));

            User user2 = new User("userID2","password2","email2","first2","last2","f","personID3");
            assertTrue(new UserDao().register(user2));
            assertTrue(new AuthTokenDao().insertAuthToken("userID2",authToken2));

            Event event = new Event("eventID", "userID", "personID", new Location( "213.7", "123.7","Provo", "USA"), "Birth", "1994");
            Event event2 = new Event("eventID2", "userID", "personID2", new Location( "213.7", "123.7","Provo", "USA"), "Birth", "1994");
            Event event3 = new Event("eventID3", "userID", "personID3", new Location( "213.7", "123.7","Provo", "USA"), "Birth", "1994");
            Event event4 = new Event("eventID4", "userID2", "personID4", new Location( "213.7", "123.7","Provo", "USA"), "Birth", "1994");
            assertTrue(eDao.insertEvent(event));
            assertTrue(eDao.insertEvent(event2));
            assertTrue(eDao.insertEvent(event3));
            assertTrue(eDao.insertEvent(event4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void testGetEvent() {
       //try {
            Event event3 = new Event("eventID3", "userID", "personID3", new Location( "213.7", "123.7","Provo", "USA"), "Birth","1994");
            EventRequest request = new EventRequest("eventID3",authToken.getAuthToken());

            EventResult expected = new EventResult("userID", event3, "personID3");
            EventResult result = eService.getEvent(request);

            assertEquals(result.getUserID(), expected.getUserID());
            assertEquals(result.getPersonID(), expected.getPersonID());
    }

}
