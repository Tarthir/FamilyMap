package models;

import java.util.ArrayList;

/**
 * Created by tyler on 2/10/2017.
 * This class holds the information for a User
 */

public class User {
    /**The user name of this user*/
    private String userName;
    /**Password of this user*/
    private String password;
    /**THe user's email address*/
    private String email;
    /**The users first name*/
    private String firstName;
    /**The users last name*/
    private String lastName;
    /**The users gender*/
    private String gender;
    /**This user's PersonID*/
    private String personID;


    public User(String username, String password, String email, String firstname, String lastname, String gender) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
    }

    public User() {
    }


    public User(String username, String password, String email, String firstname, String lastname, String gender, String personID) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
        this.personID = personID;
    }
    /**Checks an object against a user object, seeing if it holds the same data*/
    @Override
    public boolean equals(Object o) {
        if(!this.getClass().equals(o.getClass())){return false;}
        User other = (User) o;
        if(!this.getPassWord().equals(other.getPassWord())){return false;}
        if(!this.getGender().equals(other.getGender())){return false;}
        if(!this.getPersonID().equals(other.getPersonID())){return false;}
        if(!this.getfName().equals(other.getfName())){return false;}
        if(!this.getlName().equals(other.getlName())){return false;}
        if(!this.getUserName().equals(other.getUserName())){return false;}
        if(!this.getEmail().equals(other.getEmail())){return false;}
        return true;

    }

    /**@RETRUN the userName of this user*/
    public String getUserName() {
        return userName;
    }
    /**@RETRUN the passWord of this user*/
    public String getPassWord() {
        return password;
    }
    /**@RETRUN the email address of this user*/
    public String getEmail() {
        return email;
    }
    /**@RETRUN the first name of this user*/
    public String getfName() {
        return firstName;
    }
    /**@RETRUN the last name of this user*/
    public String getlName() {
        return lastName;
    }
    /**@RETRUN the gender of this user*/
    public String getGender() {
        return gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
