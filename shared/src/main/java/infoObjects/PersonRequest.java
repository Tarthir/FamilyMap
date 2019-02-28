package infoObjects;

/**
 * Created by tyler on 2/14/2017.
 * The request object for a particular person from the database for a user
 */

public class PersonRequest {
    public PersonRequest() {
    }

    /**The ID of this personRequest*/
    private String personID;
    /**The user's authtoken */
    private String authToken;

    public PersonRequest(String personID,String authToken) {
        this.personID = personID;
        this.authToken = authToken;
    }
    /**
     * @RETURN Gets the personID
     * */
    public String getPersonID() {
        return personID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
