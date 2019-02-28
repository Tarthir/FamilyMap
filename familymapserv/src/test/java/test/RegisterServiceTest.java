package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;
import service.RegisterService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by tyler on 2/22/2017.
 * Tests our register service class
 */

public class RegisterServiceTest {
    private RegisterService rService;
    private DataBase db;
    private Connection connection;

    @Before
    public void setUp() throws IOException, SQLException {
        rService = new RegisterService();
        db = new DataBase();
        connection = db.openConnection();
        db.createTables(connection);
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
    public void testRegister() {
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email", "first", "last", "f");
            AuthTokenDao authDao = new AuthTokenDao();
            RegisterResult result = rService.register(request);
            assertEquals(result.getUserName(), "username");
            assertNotEquals(result.getUserName(), "usernameNotInTable");
            assertEquals(result.getAuthToken(), authDao.getAuthToken("username").get(0));
            assertEquals("username", authDao.getUserIDFromAuthToken(result.getAuthToken()));//should get same userID
        }catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterMultiple() {
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email", "first", "last", "f");
            RegisterRequest request2 = new RegisterRequest("username2", "password2", "email2", "first", "last2", "f");
            RegisterRequest request3 = new RegisterRequest("username", "password", "email", "first", "last", "f");
            RegisterResult result = rService.register(request);
            RegisterResult result2 = rService.register(request2);
            RegisterResult result3 = rService.register(request3);
            assertNotEquals(result.getAuthToken(), result2.getAuthToken());
            assertNotEquals(result.getUserName(), result2.getUserName());
            assertNotEquals(result.getPersonID(), result2.getPersonID());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void multiRegisterSame() throws IOException {
        //try {
            RegisterRequest request = new RegisterRequest("username", "password", "email", "first", "last", "f");
            RegisterRequest request2 = new RegisterRequest("username2", "password2", "email2", "first", "last2", "f");
            RegisterRequest request3 = new RegisterRequest("username2", "password", "email", "first", "last", "f");

            RegisterResult result = rService.register(request);
            RegisterResult result2 = rService.register(request2);
            RegisterResult result3 = rService.register(request3);

            assertEquals(result.getMessage(), null);
            assertEquals(result2.getMessage(), null);
            assertEquals(result3.getMessage(), "UserName already in use");
        //} catch (SQLException e) {
         //   e.printStackTrace();
       // }
    }
}
