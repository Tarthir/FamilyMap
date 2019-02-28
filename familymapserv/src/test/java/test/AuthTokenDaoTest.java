package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.UserDao;
import models.AuthToken;
import models.User;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/21/2017.
 * Tests authTokenDao
 */

public class AuthTokenDaoTest {
    private AuthTokenDao aDao;
    private UserDao uDao;
    private DataBase db;
    private Connection connection;
    private AuthToken authToken;

    @Before
    public void setUp() throws IOException,SQLException {
        aDao = new AuthTokenDao();
        db = new DataBase();
        connection = db.openConnection();
        db.createTables(connection);
        //to Setup
        uDao = new UserDao();
        User user = new User("name", "password", "email", "first", "last", "m","peep");
        authToken = new AuthToken();
        try {
            new AuthTokenDao().insertAuthToken("name",authToken);
            assertTrue(uDao.register(user));
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
    public void insertAuthTokenTest() {
        AuthToken auth = new AuthToken();
        try {
            assertTrue(aDao.insertAuthToken("name", auth));
            assertFalse(aDao.insertAuthToken("NotInTable", auth));//will throw exception, which is fine in this case

        } catch (SQLException e) {
            //e.printStackTrace();
        }
        try {
            assertFalse(aDao.insertAuthToken("NotInTable", auth));//will throw exception, which is fine in this case
        } catch (SQLException e) {
            //e.printStackTrace();
        }

    }

    @Test
    public void deleteAuthTokenTest() {
        AuthToken auth = new AuthToken();
        try {
            assertTrue(aDao.insertAuthToken("name", auth));
            assertTrue(aDao.deleteAuthToken("name"));
            assertEquals(aDao.getAuthToken("name"),new ArrayList<String>());//empty arraylist of authtokens

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAuthTokenTest() {
        try {
            //Setup
            AuthToken auth = new AuthToken();
            assertTrue(aDao.insertAuthToken("name", auth));
            //endSetup
            ArrayList<String> authToks;
            // ArrayList<String> authToksExpected = new ArrayList<>();
            authToks = aDao.getAuthToken("5");//get the authtoken(s) related to the userID of 5
            for (String str : authToks) {//for every authToken we found
                assertEquals(aDao.getUserIDFromAuthToken(str), "5");//can we see that its related to this userID?
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAuthTokenTestFail() {

        try {
            //Setup
            AuthToken auth = new AuthToken();
            assertTrue(aDao.insertAuthToken("name", auth));
            //endSetup
            ArrayList<String> authToks;
            authToks = aDao.getAuthToken("5");//get the authtoken(s) related to the userID of 5
            for (String str : authToks) {//for every authToken we found
                assertNotEquals(aDao.getUserIDFromAuthToken(str), "notintable");//can we see that its related to this userID?
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUserIDFromAuthtokenTest() {

        try {
            String userID = new AuthTokenDao().getUserIDFromAuthToken(authToken.getAuthToken());

            assertEquals(userID,"name");
            assertNotEquals(userID,"nameNotinTable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ValidateAuthTokenTest() {

        try {
           boolean bool = new AuthTokenDao().validateAuthToken(authToken.getAuthToken());//should be true
            assertTrue(bool);
            bool = new AuthTokenDao().validateAuthToken(new AuthToken().getAuthToken());//should be false
            assertFalse(bool);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
