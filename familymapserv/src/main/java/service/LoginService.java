package service;

import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.UserDao;
import infoObjects.LoginRequest;
import infoObjects.LoginResult;
import models.AuthToken;

/**
 * Created by tyler on 2/14/2017.
 * Called by LoginHandler to use our DAO classes to try and login
 */

public class LoginService {

    public LoginService() {
    }
    /**
     * Gets the result of trying to login
     * @PARAM LoginRequest: the request to login into the application. holds userName and password
     * @RETURN the result of trying to login
     * */
    public LoginResult login(LoginRequest request) {
        try {
            UserDao dao = new UserDao();
            if (request.isValidRequest()) {//if this is a valid request

                String userPersonID = dao.login(request);
                if(!userPersonID.equals("")) {//if this user exists
                    AuthTokenDao authDao = new AuthTokenDao();
                    AuthToken authToken = new AuthToken();//gets the timestamp and UUID in the model object
                    if (authDao.insertAuthToken(request.getUserName(), authToken)) {

                        return new LoginResult(authToken.getAuthToken(), request.getUserName(), userPersonID);
                    }
                    else{
                        return new LoginResult("Inserting Authtoken into DataBase failed");
                    }
                }
            }
            return new LoginResult("Invalid Request");
        }

       catch(SQLException e){
           return new LoginResult(e.getMessage());
       }
    }


}
