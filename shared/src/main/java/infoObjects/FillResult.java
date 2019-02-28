package infoObjects;

/**
 * Created by tyler on 2/13/2017.
 * The result of attempting to fill our database
 */

public class FillResult {
    /**Used when a user is registered and the automatic fill command is called. We need to grab the User's personID*/
    private String userPersonID;
    /**Our error message*/
    private String message;

    public FillResult(int numOfEvents, int numOfPersons, String userPersonID) {
        this.userPersonID = userPersonID;
        message = "Successfully added " + numOfPersons + " people and " + numOfEvents + " events.";
    }

    public FillResult(String message) {
        this.message = message;
    }

    public FillResult() {
    }

    public String getUserPersonID() {
        return userPersonID;
    }

    public String getMessage() {
        return message;
    }
}
