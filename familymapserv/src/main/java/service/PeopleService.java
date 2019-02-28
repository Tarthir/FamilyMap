package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.AuthTokenDao;
import dataAccess.MultiDao;
import models.PeopleCreator;
import dataAccess.PersonDao;
import infoObjects.PeopleRequest;
import infoObjects.PeopleResult;

/**
 * Created by tyler on 2/14/2017.
 * Called by our PersonHandler to grab all the people related to a user
 */

public class PeopleService {

    public PeopleService() {
    }
    /***
     * Calls/Creates our DAOs to interact with our database and get the requested people
     * @PARAM PeopleRequest, the request to find all people related to a user
     * @Return All people related to the user are returned
     */
    public PeopleResult getPeople(PeopleRequest request) {
        AuthTokenDao aDao = new AuthTokenDao();
        MultiDao mDao = new MultiDao();
        PeopleCreator create = new PeopleCreator();
        ArrayList<ArrayList<String>> allPeople = new ArrayList<>();
        try {
            if(mDao.validate(request)){
                PersonDao pDao = new PersonDao();

                String userID = aDao.getUserIDFromAuthToken(request.getAuthToken());
                allPeople = pDao.getPeople(userID);

                if(allPeople == null){//if no people found
                    return new PeopleResult("No People found under this userName");
                }
            }
            else{
                return new PeopleResult("Invalid Authtoken");
            }
        }
        catch(SQLException e){return new PeopleResult(e.getMessage());}
        return new PeopleResult(create.createPeople(allPeople));
    }


}
