package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import infoObjects.PersonRequest;
import infoObjects.PersonResult;
import models.AuthToken;
import models.Person;
import models.User;
import service.PersonService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/22/2017.
 * Tests our PersonService class
 */

public class PersonServiceTest {
    private PersonService pService;
    private PersonDao pDao;
    private DataBase db;
    private Connection connection;
    private AuthToken authTok;

    @Before
    public void setUp() throws IOException {
        try {
            pDao = new PersonDao();
            pService = new PersonService();
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);
            User user = new User("name","password","email","first","last","m","2");
            assertTrue(new UserDao().register(user));
            authTok = new AuthToken();
            new AuthTokenDao().insertAuthToken("name2",authTok);
            Person person1 = new Person("1", "name", "fName", "lName", "m", "fatherID", "motherID", "spouseID");
            Person person2 = new Person("2", "name2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2");
            assertTrue(pDao.insertPerson(person1));
            assertTrue(pDao.insertPerson(person2));
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
    public void getPerson() {
            PersonRequest request = new PersonRequest("2",authTok.getAuthToken());
            PersonResult personExpected = new PersonResult(new Person("2", "name2", "fName2", "lName2", "f", "fatherID2", "motherID2", "spouseID2"));
            pService = new PersonService();
            PersonResult result= pService.getPerson(request);
            assertEquals(result.getPerson(), personExpected.getPerson());
    }
}
