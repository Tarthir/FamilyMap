package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.DataBase;
import dataAccess.UserDao;
import infoObjects.LoginRequest;
import infoObjects.LoginResult;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;
import service.LoginService;
import service.RegisterService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by tyler on 2/22/2017.
 */

public class LoginServiceTest {
    private LoginService lService;
    private UserDao uDao;
    private DataBase db;
    private Connection connection;
    private RegisterResult result;
    private RegisterResult result2;

    @Before
    public void setUp() throws SQLException, IOException {
        //try {
            lService = new LoginService();
            uDao = new UserDao();
            db = new DataBase();
            connection = db.openConnection();
            db.createTables(connection);
            //setup
            RegisterService rService = new RegisterService();
            RegisterRequest request = new RegisterRequest("username", "password", "email", "first", "last", "f");
            RegisterRequest request2 = new RegisterRequest("username2", "password2", "email", "first", "last", "f");
            result = rService.register(request);
            result2 = rService.register(request2);
        //}catch(SQLException e){e.printStackTrace();}
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
    public void testLogin() {
        try {
            AuthTokenDao authDao = new AuthTokenDao();
            LoginRequest login = new LoginRequest("username", "password");
            LoginRequest login2 = new LoginRequest("username2", "password2");
            LoginResult result1 = lService.login(login);
            LoginResult result2 = lService.login(login2);
            assertNotEquals(result1, null);
            assertNotEquals(result2, null);
            assertNotEquals(result1.getAuthToken(), result2.getAuthToken());
            assertNotEquals(result1.getUserName(), result2.getUserName());
            assertNotEquals(result1.getUserName(), result2.getUserName());
            assertEquals(authDao.getAuthToken(result1.getUserName()).size(), 2);//we now have two auth tokens
            assertEquals(authDao.getAuthToken(result2.getUserName()).size(), 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
