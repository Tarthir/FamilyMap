package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.DataBase;
import dataAccess.UserDao;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import models.Location;
import models.User;
import service.DataGenerator;
import service.FillService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tyler on 2/27/2017.
 * Tests our data generator
 */

public class DataGeneratorTest {
    private DataGenerator dGen;
    private DataBase db;
    private Connection connection;
    private Location[] locations = {new Location("lat","long","provo","USA"),new Location("lat2","long2","tucson","USA"),new Location("lat3","long3","Richland","USA")};
    private String[] fNames = {"sally","jo","sue"};
    private String[] mNames = {"Bob","Bobby","billy"};
    private String[] lNames = {"Matthews","Brady","Spicer"};
    private UserDao uDao;
    private FillRequest request;

    @Before
    public void setUp(){
        try {
            dGen = new DataGenerator();
            uDao = new UserDao();
            db = new DataBase();
            connection = db.openConnection();
            db.dropTables(connection);
            connection = db.openConnection();
            db.createTables(connection);
            User user = new User("userName","password","email","first","last","m","personID");
            assertTrue(uDao.register(user));
            request = new FillRequest(4, "userName");
            request.setfNames(fNames);
            request.setlNames(lNames);
            request.setLocations(locations);
            request.setmNames(mNames);
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
    public void genData() {
        try {
            FillService fill = new FillService();
            FillResult actual = fill.fill(request);
            assertEquals(actual.getMessage(), "Successfully added 31 people and 123 events.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
