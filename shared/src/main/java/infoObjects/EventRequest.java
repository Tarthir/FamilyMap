package infoObjects;

/**
 * Created by tyler on 2/13/2017.
 * Requests one particular event from a user's descendent
 */

public class EventRequest {
    /**The userID of this Event Request*/
    private String eventID;
    /**THe user's authtoken*/
    private String authToken;

    public EventRequest(String eventID,String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    public EventRequest() {
    }
    /**
     * Checks to see if request is valid
     * @RETURN BOOLEAN, whether request is valid or not
     * */
    public boolean isValidRequest(){
        return !eventID.equals("");
    }
    /**@RETURN the eventID of this Event Request*/
    public String getEventID() {
        return eventID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
