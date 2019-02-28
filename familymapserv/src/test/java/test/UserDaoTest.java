package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.DataBase;
import models.UserCreator;
import dataAccess.UserDao;
import infoObjects.LoginRequest;
import models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/20/2017.
 */

public class UserDaoTest {
    private UserDao uDao;
    private DataBase db;
    private Connection connection;

    @Before
    public void setUp() throws IOException, SQLException {
        //System.out.print("Res");
        uDao = new UserDao();
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
        try{
        User user = new User("name","password","email","first","last","m","peep");
        assertTrue(uDao.register(user));
        assertTrue(uDao.checkUserName("name"));
            assertFalse(uDao.checkUserName("name2"));
    }catch(SQLException e){e.printStackTrace();}
    }

    @Test
    public void testLogin() {
        try{
        User user = new User("name","password","email","first","last","m","peep");
        assertTrue(uDao.register(user));
        LoginRequest request = new LoginRequest("name","password");
        LoginRequest request2 = new LoginRequest("name2","password2");//not in table
        LoginRequest request3 = new LoginRequest("name3","password3");//not in table
        assertEquals(uDao.login(request),"peep");//login returns the personID of the user, so it should match
        assertNotEquals(uDao.login(request2),"peep");
        assertNotEquals(uDao.login(request3),"peep");
    }catch(SQLException e){e.printStackTrace();}
    }

    @Test
    public void testUpdateUser() {
        try{
            User user = new User("name","password","email","first","last","m","peep");
            assertTrue(uDao.register(user));
            User userUpdate = new User("name","password","email","first","last","m","peepNew");
            uDao.updateUser(userUpdate);
            User newUser = new UserCreator().createUser(uDao.selectAllFromUser("name"));
            assertNotEquals(newUser,user);
            assertEquals(newUser.getUserName(),user.getUserName());
            assertNotEquals(newUser.getPersonID(),user.getPersonID());
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test
    public void selectAllFromUserTest() {
        try{
            //setup
            User user = new User("name","password","email","first","last","m","peep");
            assertTrue(uDao.register(user));

            User user2 = new UserCreator().createUser(uDao.selectAllFromUser("name"));//grab from Database
            assertEquals(user,user2);//should be the same
            User userUpdate = new User("name","password","email","first","last","m","peepNew");//update that user
            uDao.updateUser(userUpdate);

            User updatedUser = new UserCreator().createUser(uDao.selectAllFromUser("name"));
            assertNotEquals(user,updatedUser);//not the same anymore after the update
        }catch(SQLException e){e.printStackTrace();}
    }

}
