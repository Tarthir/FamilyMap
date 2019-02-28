package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import infoObjects.ClearResult;
import models.AuthToken;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import service.ClearService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/27/2017.
 * Tets our clear service class
 */

public class ClearServiceTest {
    private ClearService cService;
    private PersonDao pDao;
    private EventDao eDao;
    private AuthTokenDao aDao;
    private DataBase db;
    private Connection connection;
    private UserDao uDao;

    @Before
    public void setUp(){
        try {
            cService = new ClearService();
            db = new DataBase();
            uDao = new UserDao();
            pDao = new PersonDao();
            eDao = new EventDao();
            aDao = new AuthTokenDao();
            connection = db.openConnection();
            db.createTables(connection);

            User user = new User("name", "password", "email", "first", "last", "f","peep");
            User user2 = new User("name2", "password2", "email2", "first2", "last2", "f","peep");
            assertTrue(uDao.register(user));
            assertTrue(uDao.register(user2));
            assertTrue(aDao.insertAuthToken("name", new AuthToken()));
            assertTrue(aDao.insertAuthToken("name2", new AuthToken()));
            assertTrue(pDao.insertPerson(new Person("personID", "name", "fName", "lName", "m", "fatherID", "motherID", "spouseID")));
            assertTrue(pDao.insertPerson(new Person("personID2", "name", "fName2", "lName2", "m", "fatherID2", "motherID2", "spouseID2")));
            assertTrue(pDao.insertPerson(new Person("personID3", "name", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3")));
            assertTrue(pDao.insertPerson(new Person("personID4", "name2", "fName4", "lName4", "f", "fatherID4", "motherID4", "spouseID4")));

            assertTrue(eDao.insertEvent(new Event("eventID", "name", "personID", new Location("213.7", "123.7","Provo", "USA"), "Birth", "1994")));
            assertTrue(eDao.insertEvent(new Event("eventID2", "name", "personID2", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth")));
            assertTrue(eDao.insertEvent(new Event("eventID3", "name", "personID3", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth")));
            assertTrue(eDao.insertEvent(new Event("eventID4", "name2", "personID4", new Location("213.7", "123.7","Provo", "USA"), "1994", "Birth2")));
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
    public void clearTest(){
        ClearResult actual = cService.clear();
        ClearResult expected = new ClearResult();
        assertEquals(actual.getMessage(),expected.getMessage());
    }
}
