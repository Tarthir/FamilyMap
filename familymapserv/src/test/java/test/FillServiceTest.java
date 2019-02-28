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
import infoObjects.EventRequest;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import infoObjects.PersonRequest;
import models.AuthToken;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import service.FillService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/24/2017.
 */

public class FillServiceTest {
    private FillService fService;
    private DataBase db;
    private Connection connection;
    private Location[] locations = {new Location("lat","long","provo","USA"),new Location("lat2","long2","tucson","USA"),new Location("lat3","long3","Richland","USA")};
    private String[] fNames = {"sally","jo","sue"};
    private String[] mNames = {"Bob","Bobby","billy"};
    private String[] lNames = {"Matthews","Brady","Spicer"};
    private UserDao uDao;
    private PersonDao pDao;
    private EventDao eDao;
    private AuthToken authToken;
    private AuthToken authToken2;

    @Before
    public void setUp(){
        try {
            fService = new FillService();
            uDao = new UserDao();
            db = new DataBase();
            pDao = new PersonDao();
            eDao = new EventDao();
            connection = db.openConnection();
            db.createTables(connection);
            //setup
            User user = new User("userName","password","email","first","last","m","personID");
            assertTrue(uDao.register(user));
            authToken = new AuthToken();
            new AuthTokenDao().insertAuthToken("userName",authToken);

            User user2 = new User("userName2","password","email","first","last","m","personID");
            assertTrue(uDao.register(user2));
            authToken2 = new AuthToken();
            new AuthTokenDao().insertAuthToken("userName2",authToken2);

            Person p = new Person("personID", "userName2", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person p2 = new Person("personID2", "userName2", "fName", "lName", "m", "fatherID", "motherID", "spouseID");

            assertTrue(pDao.insertPerson(p));
            assertTrue(pDao.insertPerson(p2));
            Event event = new Event("eventID", "userName2", "personID", new Location( "213.7", "123.7", "Provo","USA"), "1994", "Birth");
            Event event2 = new Event("eventID2", "userName2", "personID2", new Location( "213.7", "123.7", "Provo","USA"), "1994", "Birth");

            assertTrue(eDao.insertEvent(event));
            assertTrue(eDao.insertEvent(event2));
            //setup end
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
    public void genDataAndDeletion() {
        try {

            FillRequest request = new FillRequest(4, "userName2");
            request.setfNames(fNames);
            request.setlNames(lNames);
            request.setLocations(locations);
            request.setmNames(mNames);
            FillResult actual = fService.fill(request);
            Event event = new Event("eventID", "userName2", "personID", new Location( "213.7", "123.7", "Provo","USA"), "1994", "Birth");

            assertNotEquals(eDao.getEvent(new EventRequest("eventID",authToken2.getAuthToken())),event);
            assertNotEquals(actual.getUserPersonID(), pDao.getPerson(new PersonRequest("personID2",authToken2.getAuthToken())));
            assertEquals(actual.getMessage(),"Successfully added 31 people and 123 events.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
