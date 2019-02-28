package models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tyler on 2/13/2017.
 * An object which holds our AuthTokens
 */

public class AuthToken {
    /**The authorization token allowing us to access the app*/
    private String authToken;
    /**The time this authorization token was created as a long value*/
    private Long timeStamp;

    public AuthToken() {
        UUID uuid = UUID.randomUUID();
        this.authToken = uuid.toString();
        Date date = new Date();
        timeStamp = date.getTime();
    }
    public Long getTimeStamp() {
        return timeStamp;
    }
    public String getAuthToken() {
        return authToken;
    }
}
