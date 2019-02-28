/*
package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.DataBase;
import infoObjects.ClearResult;
import infoObjects.EventRequest;
import infoObjects.EventResult;
import infoObjects.EventsRequest;
import infoObjects.EventsResult;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import infoObjects.LoadRequest;
import infoObjects.LoadResult;
import infoObjects.LoginRequest;
import infoObjects.LoginResult;
import infoObjects.PeopleRequest;
import infoObjects.PeopleResult;
import infoObjects.PersonRequest;
import infoObjects.PersonResult;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import server.FamilyMapServerProxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

*/
/**
 * Created by tyler on 3/8/2017.
 * Tets our family map server
 *//*


public class FamilyMapServerProxyTest {
    private DataBase db;
    private Connection connection;
    //private FamilyMapServerProxy SINGLETON;
    private FamilyMapServerProxy proxy;

    @Before
    public void setUp(){
        try {
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);
            proxy = new FamilyMapServerProxy();
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
    public void ClearTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        ClearResult result2 = proxy.clear();
        PersonResult result3 = proxy.getPerson(new PersonRequest(result.getPersonID(),result.getAuthToken()));
        assertEquals(result3.getPerson(), null);
        assertNotEquals(result3.getMessage(),null);
        assertEquals(result2.getMessage(),"Clear Succeeded");
    }

    @Test
    public void RegisterTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),"user");
        result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),null);
        assertEquals(result.getMessage(),"UserName already in use");
        proxy.clear();
    }
    @Test
    public void LoginTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertNotEquals(result.getMessage(),"UserName already in use");
        LoginResult lResult = proxy.login(new LoginRequest("user","password"));
        assertEquals(lResult.getUserName(),"user");
        assertNotEquals(lResult.getAuthToken(),result.getAuthToken());
        proxy.clear();
    }
    @Test
    public void LoadTest(){
        Person [] people = {new Person("personID", "username", "fName", "lName", "m", "fatherID", "motherID", "spouseID")};
        Event[] events = {new Event("eventID", "username", "personID", new Location("Provo", "213.7", "123.7", "USA"), "1994", "Birth")};
        User[] users = {new User("username","password","email","fName","lName","m","personID")};
        LoadResult result = proxy.load(new LoadRequest(users,people,events));
        assertEquals(result.getResultMessage(),"Successfully added 1 users 1 persons and 1 events to the database");
        proxy.clear();
    }
    @Test
    public void FillTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertNotEquals(result.getMessage(),"UserName already in use");
        FillResult result2 = proxy.fill(new FillRequest(2,"user"));
        assertEquals(result2.getMessage(),"Successfully added 7 people and 27 events.");
        proxy.clear();
    }
    @Test
    public void getEventTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),"user");
        EventsResult result2 = proxy.getEvents(new EventsRequest(result.getAuthToken()));
        assertEquals(result2.getEvents().size(), 123);
        assertEquals(result2.getMessage(),null);
        Event event = result2.getEvents().get(0);
        EventResult result3 = proxy.getEvent(new EventRequest(event.getEventID(),result.getAuthToken()));
        assertEquals(event,result3.getEvent());
        proxy.clear();
    }
    @Test
    public void getEventsTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),"user");
        EventsResult result2 = proxy.getEvents(new EventsRequest(result.getAuthToken()));
        assertEquals(result2.getEvents().size(), 123);
        assertEquals(result2.getMessage(),null);
        proxy.clear();
    }
    @Test
    public void getPersonTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),"user");
        PersonResult result2 = proxy.getPerson(new PersonRequest(result.getPersonID(),result.getAuthToken()));
        assertEquals(result2.getPerson().getDescendant(), result.getUserName());
        assertEquals(result2.getPerson().getPersonID(), result.getPersonID());
        assertEquals(proxy.getAuthToken(), result.getAuthToken());
        proxy.clear();
    }
    @Test
    public void getPeopleTest(){
        RegisterResult result = proxy.register(new RegisterRequest("user","password","email","firstname","lastname","m"));
        assertEquals(result.getUserName(),"user");
        PeopleResult result2 = proxy.getPeople(new PeopleRequest(proxy.getAuthToken()));
        assertEquals(result2.getPeople().size(),31);
        assertEquals(result2.getMessage(),null);
        proxy.clear();
    }
}
*/
