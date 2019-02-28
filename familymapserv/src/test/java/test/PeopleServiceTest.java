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
import dataAccess.PersonDao;
import dataAccess.UserDao;
import infoObjects.PeopleRequest;
import models.AuthToken;
import models.Person;
import models.User;
import service.PeopleService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/22/2017.
 */

public class PeopleServiceTest {
    private PeopleService pService;
    private PersonDao pDao;
    private DataBase db;
    private Connection connection;
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private ArrayList<String> authToken;
    private ArrayList<String> authToken2;

    @Before
    public void setUp() throws IOException {
        try {
            UserDao uDao = new UserDao();
            AuthTokenDao aDao = new AuthTokenDao();
            pDao = new PersonDao();
            pService = new PeopleService();
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);
            User user = new User( "name", "password", "email", "first", "last", "m","peep");
            User user2 = new User( "name2", "password2", "email2", "first2", "last2", "f","peep2");
            ;
            assertTrue(uDao.register(user));
            assertTrue(uDao.register(user2));
            AuthToken auth = new AuthToken();
            assertTrue(aDao.insertAuthToken("name", auth));
            AuthToken auth2 = new AuthToken();
            assertTrue(aDao.insertAuthToken("name2", auth2));
            authToken = aDao.getAuthToken("name");
            authToken2 = aDao.getAuthToken("name2");
            person1 = new Person("1", "name", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            person2 = new Person("2", "name2", "fName2", "lName2", "m", "fatherID2", "motherID2", "spouseID2");
            person3 = new Person("3", "name", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3");
            person4 = new Person("4", "name2", "fName4", "lName4", "f", "fatherID4", "motherID4", "spouseID4");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
            assertTrue(pDao.insertPerson(person3));
            assertTrue(pDao.insertPerson(person4));
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
    public void getPeople(){
        //MAKE AUTHTOKENS AND INSERT USERS
        try {
        PeopleRequest request = new PeopleRequest(authToken.get(0));
        PeopleRequest request2 = new PeopleRequest(authToken2.get(0));
        pService = new PeopleService();
        ArrayList<Person> expected = new ArrayList<>(Arrays.asList(person1,person3));
        ArrayList<Person> expected2 = new ArrayList<>(Arrays.asList(person2,person4));
        ArrayList<Person> peopleResult = null;
        ArrayList<Person> peopleResult2 = null;

            peopleResult = pService.getPeople(request).getPeople();
            peopleResult2 = pService.getPeople(request2).getPeople();

        assertEquals(peopleResult,expected);
        assertEquals(peopleResult2,expected2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
