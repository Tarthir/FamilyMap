package infoObjects;

/**
 * Created by tyler on 2/14/2017.
 * The request object for all people from the database belonging to one user
 */

public class PeopleRequest {
    /**The authtoken belonging to the user of our people*/
    private String authToken;

    public PeopleRequest(String authToken) {
        this.authToken = authToken;
    }

    public PeopleRequest() {
    }

    public String getAuthToken() {
        return authToken;
    }

}
