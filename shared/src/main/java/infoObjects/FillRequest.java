package infoObjects;

import models.Location;
import models.User;

/**
 * Created by tyler on 2/13/2017.
 * Contains the info needed to fill a database
 */

public class FillRequest {
    /**The num of generations to generate*/
    private int numOfGenerations;
    /**The userName of the user whose family tree we are going to fill*/
    private String username;
    /**Array of possible locations*/
    private Location[] locations;
    /**Array of possible female names*/
    private String[] fNames;
    /**Array of possible last names*/
    private String[] lNames;
    /**Array of possible male names*/
    private String[] mNames;
    private User user;

    public FillRequest(int numOfGenerations, String username) {
        this.numOfGenerations = numOfGenerations;
        this.username = username;
    }

    public FillRequest() {
    }

    public boolean isValidRequest(){
        return  (!username.isEmpty()) && (numOfGenerations >= 0) &&
                (locations != null) && (fNames != null) &&
                (lNames != null) && (mNames != null);
    }

    /**@RETURN the num of generations to generate*/
    public int getNumOfGenerations() {
        return numOfGenerations;
    }
    /**@RETURN the num of generations to generate*/
    public String getUserName() {
        return username;
    }

    public Location[] getLocations() {
        return locations;
    }

    public String[] getfNames() {
        return fNames;
    }

    public String[] getlNames() {
        return lNames;
    }

    public String[] getmNames() {
        return mNames;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }

    public void setfNames(String[] fNames) {
        this.fNames = fNames;
    }

    public void setlNames(String[] lNames) {
        this.lNames = lNames;
    }

    public void setmNames(String[] mNames) {
        this.mNames = mNames;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
