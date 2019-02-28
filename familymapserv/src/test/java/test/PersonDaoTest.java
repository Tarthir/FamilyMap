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
import infoObjects.PersonRequest;
import models.AuthToken;
import models.Person;
import models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/22/2017.
 * Tests our PersonDao class
 */

public class PersonDaoTest {

    private PersonDao pDao;
    private DataBase db;
    private Connection connection;
    private AuthToken authToken;
    private AuthToken authToken2;

    @Before
    public void setUp() throws IOException, SQLException {
        pDao = new PersonDao();
        db = new DataBase();
        connection = db.openConnection();
        db.createTables(connection);

        //setup
        UserDao userDao = new UserDao();
        assertTrue(userDao.register(new User("userName","password","email","fName","lName","f","e")));
        authToken = new AuthToken();
        new AuthTokenDao().insertAuthToken("userName",authToken);

        assertTrue(userDao.register(new User("userName2","password","email","fName","lName","f","e2")));
        authToken2 = new AuthToken();
        new AuthTokenDao().insertAuthToken("userName2",authToken2);

        assertTrue(pDao.insertPerson(new Person("personID", "userName", "fName", "lName", "m", "fatherID", "motherID", "spouseID")));
        assertTrue(pDao.insertPerson(new Person("personID2", "userName", "fName2", "lName2", "m", "fatherID2", "motherID2", "spouseID2")));
        assertTrue(pDao.insertPerson(new Person("personID3", "userName", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3")));
        assertTrue(pDao.insertPerson(new Person("personID4", "userName2", "fName4", "lName4", "f", "fatherID4", "motherID4", "spouseID4")));
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
    public void getUserOfPersonTest(){
        try {
            assertEquals(pDao.getUserOfPerson("personID"),"userName");
            assertEquals(pDao.getUserOfPerson("personID4"),"userName2");
            assertNotEquals(pDao.getUserOfPerson("personID"),"userName2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertPerson() {
        try {
            Person person1 = new Person("5","userName", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person person2 = new Person("6","userName2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeletePerson() {
        try {

            assertTrue(pDao.deletePerson("userName"));
            assertEquals(pDao.getPerson(new PersonRequest("personID",authToken.getAuthToken())),null);
            assertEquals(pDao.getPerson(new PersonRequest("personID2",authToken.getAuthToken())),null);
            assertEquals(pDao.getPerson(new PersonRequest("personID3",authToken.getAuthToken())),null);
            assertNotEquals(pDao.getPerson(new PersonRequest("personID4",authToken2.getAuthToken())),null);
            assertTrue(pDao.deletePerson("userName2"));
            assertEquals(pDao.getPerson(new PersonRequest("personID4",authToken2.getAuthToken())),null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetPerson() {
        try {
            //Person person1 = new Person("5","userID", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            //Person person2 = new Person("6","userID2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2");
            PersonRequest request = new PersonRequest("personID",authToken.getAuthToken());
            PersonRequest request2 = new PersonRequest("personID4",authToken2.getAuthToken());
            //assertTrue(pDao.insertPerson(person1));
            //assertTrue(pDao.insertPerson(person2));
            ArrayList<String> dataExpected = new ArrayList<>(Arrays.asList("personID", "userName", "fName", "lName", "m", "fatherID", "motherID", "spouseID"));
            assertEquals(dataExpected, pDao.getPerson(request));
            ArrayList<String> dataExpected2 = new ArrayList<>(Arrays.asList("personID4", "userName2", "fName4", "lName4", "f", "fatherID4", "motherID4", "spouseID4"));
            assertEquals(dataExpected2, pDao.getPerson(request2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetPersonFail() {//trying personIDs that dont exist will fail
        try {
            PersonRequest request = new PersonRequest("personID6",authToken.getAuthToken());
            PersonRequest request2 = new PersonRequest("personID7",authToken2.getAuthToken());
            assertEquals(null, pDao.getPerson(request));
            assertEquals(null, pDao.getPerson(request2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetPeople() {
        try {
            Person person1 = new Person("1", "userID", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person person2 = new Person("2", "userID2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2");
            Person person3 = new Person("3", "userID", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3");
            Person person4 = new Person("4", "userID", "fName4", "lName4", "m", "fatherID4", "motherID4", "spouseID4");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
            assertTrue(pDao.insertPerson(person3));
            assertTrue(pDao.insertPerson(person4));
            ArrayList<String> expected1 = new ArrayList<>(Arrays.asList("1", "userID", "fName", "lName", "m", "fatherID", "motherID", "spouseID"));
            ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("3", "userID", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3"));
            ArrayList<String> expected4 = new ArrayList<>(Arrays.asList("4", "userID", "fName4", "lName4", "m", "fatherID4", "motherID4", "spouseID4"));
            ArrayList<ArrayList<String>> allPeopleExpected = new ArrayList<>(Arrays.asList(expected1, expected3, expected4));
            assertEquals(allPeopleExpected, pDao.getPeople("userID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetPeopleFail() {
        try {
            Person person1 = new Person("1", "userID", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person person2 = new Person("2", "userID2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2");
            Person person3 = new Person("3", "userID", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3");
            Person person4 = new Person("4", "userID", "fName4", "lName4", "m", "fatherID4", "motherID4", "spouseID4");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
            assertTrue(pDao.insertPerson(person3));
            assertTrue(pDao.insertPerson(person4));
            ArrayList<String> expected1 = new ArrayList<>(Arrays.asList("1", "userID", "fName", "lName", "m", "fatherID", "motherID", "spouseID"));
            ArrayList<String> expected3 = new ArrayList<>(Arrays.asList("3", "userID", "fName3", "lName3", "m", "fatherID3", "motherID3", "spouseID3"));
            ArrayList<String> expected4 = new ArrayList<>(Arrays.asList("4", "userID", "fName4", "lName4", "m", "fatherID4", "motherID4", "spouseID4"));
            ArrayList<ArrayList<String>> allPeopleExpected = new ArrayList<>(Arrays.asList(expected1, expected3, expected4));
            assertNotEquals(allPeopleExpected, pDao.getPeople("userIDNotinTable"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
