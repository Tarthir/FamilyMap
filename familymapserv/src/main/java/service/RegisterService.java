package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import dataAccess.AuthTokenDao;
import dataAccess.UserDao;
import encode.JsonData;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;
import models.AuthToken;
import models.User;

/**
 * Created by tyler on 2/14/2017.
 * Called/Created by our RegisterHandler to use the DAO to try and register a new user and add them to the Database
 */

public class RegisterService {
    //private User newUser;

    public RegisterService() {
    }

    /**
     * Calls/Creates our DAO classes to interact with the database and get the RegisterResult
     *
     * @PARAM request: to register a new user
     * @RETURN Gets the result of trying to register a new user
     * @Exception throws SQLException
     */
    public RegisterResult register(RegisterRequest request) throws IOException{
        if (request.isValidRequest()) {
            try {
                User newUser = makeUserModel(request);
                newUser.setPersonID(UUID.randomUUID().toString());//set his personID, so it can be registered
                UserDao dao = new UserDao();
                if (!dao.checkUserName(request.getUserName())) {//check to see if the userName already exists then if register is successful
                    if (dao.register(newUser)) {

                        return getResult(newUser);
                    }
                } else {
                    return new RegisterResult("UserName already in use");
                }

            } catch (SQLException e) {
                return new RegisterResult(e.getMessage());
            }
        }
        return new RegisterResult("Invalid Request");
    }

    /**
     * Gets the result of registering a user
     *
     * @RETURN Gets the result of trying to register a new user
     * @PARAM the user that was made
     * @Exception throws SQLException
     */
    private RegisterResult getResult(User newUser) throws IOException {
        try {
            AuthToken authToken = new AuthToken();//gets the timestamp and UUID in the model object
            if (new AuthTokenDao().insertAuthToken(newUser.getUserName(), authToken)) {
                int numOfGenerations = 4;
                FillRequest fillRequest = new FillRequest(numOfGenerations, newUser.getUserName());
                fillRequest.setUser(newUser);
                FillResult fResult = new FillService().fill(new JsonData().setupJSONArrays(fillRequest));

                if (fResult.getUserPersonID() == null || fResult.getUserPersonID().equals("")) {
                    return new RegisterResult(fResult.getMessage());
                } else {//if we didnt have an error
                    return new RegisterResult(authToken.getAuthToken(), newUser.getUserName(), fResult.getUserPersonID());
                }
            }
            return new RegisterResult("Inserting the authtoken failed");
        }catch(SQLException e){
            return new RegisterResult(e.getMessage());
        }
    }

    /**
     * Makes the user for the register method to use
     *
     * @PARAM request, the request we are creating the user from
     * @RETURN The user object
     */
    private User makeUserModel(RegisterRequest request) {
        return new User(request.getUserName(), request.getPassWord(),
                request.getEmail(), request.getfName(), request.getlName(), request.getGender());
    }

}
