package service;

import java.sql.SQLException;

import dataAccess.AuthTokenDao;
import dataAccess.EventDao;
import models.EventsCreator;
import dataAccess.MultiDao;
import infoObjects.EventRequest;
import infoObjects.EventResult;
import models.Event;

/**
 * Created by tyler on 2/14/2017.
 * Called by the EventHandler class to get a particular event through our DAO in our database
 */

public class EventService {
    //EventRequest request;
    public EventService() {}
    /**Gets the result of trying to get one particular event
     * @PARAM request, the request to get a particular event
     * @PARAM String, an authToken
     * @RETURN The result of attempting to get a particular event. May return an error
     * */
    public EventResult getEvent(EventRequest request){
        try {
            EventDao eDao = new EventDao();
            MultiDao mDao = new MultiDao();
            EventsCreator create = new EventsCreator();
            AuthTokenDao aDao = new AuthTokenDao();
            Event event = null;
            if (mDao.validate(request)){
                event = create.createEvent(eDao.getEvent(request));
                if (event != null) {
                    return new EventResult(event.getDescendant(), event, event.getPersonID());
                }
            }
            else{
                return new EventResult("Invalid AuthToken");
            }
        }catch(SQLException e){
            return new EventResult(e.getMessage());
        }
        return new EventResult("No such Event found");
    }
}
