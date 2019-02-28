package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.DataBase;
import infoObjects.LoadRequest;
import infoObjects.LoadResult;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import service.LoadService;

import static org.junit.Assert.assertEquals;

/**
 * Created by tyler on 2/24/2017.
 */

public class LoadServiceTest {
    private LoadService lService;
    private DataBase db;
    private Connection connection;
    private Person[] persons = {new Person("personID", "userName", "fName", "lName", "m", "", "", ""), new Person("personID2", "userName2", "fName", "lName", "m", "", "", "")};
    private Event[] events = {new Event("eventID", "userName", "personID", new Location("lat", "long", "city", "USA"), "2999", "Birth"),
            new Event("eventID2", "userName2", "personID2", new Location("lat", "long", "city", "USA"), "2999", "Birth")};
    private User[] users = {new User("userName", "password", "email", "name", "lname", "m","peep"), new User("userName2", "password", "email", "name", "lname", "m","peep")};

    @Before
    public void setUp(){
        //try {
        lService = new LoadService();
        db = new DataBase();
        try {
            connection = db.openConnection();
            db.createTables(connection);
        }
        catch (SQLException e) {
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
        //return;
    }

    @Test
    public void genDataAndDeletion() {
        LoadResult result = null;
        try {
            result = lService.load(new LoadRequest(users, persons, events));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LoadResult expected = new LoadResult(2, 2, 2);
        assertEquals(result.getResultMessage(), expected.getResultMessage());
    }
}
