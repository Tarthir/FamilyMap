package service;

import java.sql.SQLException;

import dataAccess.MultiDao;
import models.UserCreator;
import dataAccess.UserDao;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import models.User;

/**
 * Created by tyler on 2/14/2017.
 * Called by our FillHandler/RegisterService and uses our DAO classes to fill up our Database with 4 generations of data
 */

public class FillService {

    public FillService() {
    }

    /**
     * Gets the result of a request to fill the database
     *
     * @PARAM request, the request to fill the database
     * @RETURN the result of attempting to fill the database
     */
    public FillResult fill(FillRequest request){
        try {
            if (request.isValidRequest()) {//if given a non negative integer
                MultiDao multiDao = new MultiDao();
                UserDao uDao = new UserDao();

                if (uDao.checkUserName(request.getUserName())) {
                    User user = new UserCreator().createUser(uDao.selectAllFromUser(request.getUserName()));
                    if (multiDao.deleteFromDataBase(request.getUserName())) {//if the deletion works right
                        if(request.getUser() == null){request.setUser(user);}
                        DataGenerator dataGenerator = new DataGenerator();
                        return dataGenerator.genData(request);//if it generates right
                    }
                    else{
                        return new FillResult("DataBase Error");
                    }
                }
            }
            return new FillResult("Invalid Request Data Found");
        } catch (SQLException e) {
            return new FillResult(e.getMessage());//return the error
        } catch (Exception e) {
            return new FillResult(e.getMessage());//return the error
        }

    }
}
