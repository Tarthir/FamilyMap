package service;

import java.sql.SQLException;

import dataAccess.MultiDao;
import infoObjects.ClearResult;

/**
 * Created by tyler on 2/14/2017.
 * Called/Created By ClearHandler to access the Appropriate DAO class and clear our database
 */

public class ClearService {
    public ClearService() {
    }

    /***
     * Gets the result of the attempt to clear the database
     * @RETURN the result of a successful or not attempt to clear the database
     */
    public ClearResult clear(){
        MultiDao mDao = new MultiDao();
        try {
            mDao.doClear();
            return new ClearResult();//if worked!
        } catch (SQLException e) {
            return new ClearResult(e.getMessage());//Exception was thrown
        }
    }
}
