package models;

import java.util.ArrayList;

/**
 * Created by tyler on 2/10/2017.
 * This class holds the information for a person
 */

public class Person {
    /**
     * The userName of the user to which this person belongs
     */
    private String descendant;
    /**
     * The personID of this person
     */
    private String personID;
    /**
     * The first name of the person
     * */
    private String firstName;
    /**
     * The last name of the person
     * */
    private String lastName;
    /**
     * The gender of the person, "m" or "f"
     * */
    private String gender;
    /**
     * This persons Father(Person obj). May be null
     * */
    private String father;
    /**
     * The mother(Person obj) of this person. May be null
     * */
    private String mother;
    /**
     * The spouse(Person obj) of this person. May be null
     * */
    private String spouse;

    public Person(String personID,String descendant, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = fatherID;
        this.mother = motherID;
        this.spouse = spouseID;
    }
    public Person(String personID,String descendant, String firstname, String lastname, String gender) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
        this.father = "";
        this.mother = "";
        this.spouse = "";
    }

    public Person() {
    }

    public String getPersonID() {
        return personID;
    }

    public String getfName() {
        return firstName;
    }

    public String getlName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getDescendant() {
        return descendant;
    }
    /**
     * Compares the contents of person objects to see if they are equal
     * @RETURN true if they are equal, false otherwise
     * */
    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()){return false;}
        Person other = (Person)o;
        if(!other.getDescendant().equals(this.getDescendant())){return false;}
        if(!other.getPersonID().equals(this.getPersonID())){return false;}
        if(!other.getGender().equals(this.getGender())){return false;}
        if(!other.getMother().equals(this.getMother())){return false;}
        if(!other.getfName().equals(this.getfName())){return false;}
        if(!other.getlName().equals(this.getlName())){return false;}
        if(!other.getSpouse().equals(this.getSpouse())){return false;}
        if(!other.getFather().equals(this.getFather())){return false;}
        return true;

    }

    @Override
    public String toString() {
        return getfName() + " " + getlName();
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

}
