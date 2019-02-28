package models;

import java.util.ArrayList;

import models.User;

/**
 * Created by tyler on 2/10/2017.
 * Creates users for the database. Adds them in
 */

public class UserCreator {
    public UserCreator() {
    }

    /**
     * Creates one person from input
     *
     * @PARAM Arraylist of of Strings holding user data
     * @RETURN A person object
     */
    public User createUser(ArrayList<String> userData) {
        if (userData == null || userData.size() < 6) {//7 is the number of parameters we should have
            return null;
        } else {//return a new person
            if(userData.size()  == 7){//if includes the personID
                return new User(userData.get(0), userData.get(1), userData.get(2), userData.get(3),
                        userData.get(4), userData.get(5),userData.get(6));
            }
            //no personID
            return new User(userData.get(0), userData.get(1), userData.get(2), userData.get(3),
                    userData.get(4), userData.get(5));
        }
    }
}
