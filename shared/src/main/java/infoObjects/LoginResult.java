package infoObjects;

/**
 * Created by tyler on 2/13/2017.
 * THe result of attempting to login to the application
 */

public class LoginResult {
    /** Non-empty auth token string*/
    private String authToken;
    /** User name passed in with request*/
    private String userName;
    /** Non-empty string containing the userID  of the user’s generated object*/
    private String personID;
    /**Our exception result*/
    private String message;



    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public LoginResult(String message) {
        this.message = message;
    }

    public LoginResult() {
    }

    /**@RETURN The authToken of this login result*/
    public String getAuthToken() {
        return authToken;
    }
    /**@RETURN The userName of this login result*/
    public String getUserName() {
        return userName;
    }
    /**@RETURN The personID of this login result*/
    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
