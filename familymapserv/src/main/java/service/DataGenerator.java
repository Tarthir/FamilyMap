package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import models.Event;
import models.Location;
import models.Person;
import models.User;
import sun.rmi.runtime.Log;

/**
 * Created by tyler on 2/20/2017.
 * Generates data when a user is registered
 */

public class DataGenerator {
    /**
     * Total amount of generations we are making
     */
    private int TOTAL_GENERATIONS;
    /**
     * The people we are generating
     */
    private ArrayList<Person> people;
    /*
    *To ensure one event per city
    */
    private Map<String,Location> eventLocMap = new TreeMap<>();

    public DataGenerator() {
        events = new ArrayList<>();
        people = new ArrayList<>();//one arraylist per generation
    }
    /**Holds our events*/
    private ArrayList<Event> events;

    /**
     * Gets fresh data for a user
     *
     * @PARAM FillRequest object
     * @RETURN whether the generation process was successful or not
     * @EXCEPTION SQLException, IllegalArgumentException, Exception
     */
    public FillResult genData(FillRequest request) throws SQLException, IllegalArgumentException, Exception {
        TOTAL_GENERATIONS = request.getNumOfGenerations();
        int currGeneration = TOTAL_GENERATIONS;
        int adderIndex = 0;//allows us to set mother/father indicies for children
        if(TOTAL_GENERATIONS > 0) {
            for (int j = currGeneration; j > 0; j--) {//create all the generations starting from the top
                adderIndex = genPeople(j, request, adderIndex);
            }
            //put the user in the table
        }
        genUserData(request);
        return insertData(request);
    }
    /**
     * Used to generate data for the user
     * @PARAM User, the user
     * @PARAM FillRequest, the request holding info needed to gen this user's data
     * @RETURN void
     * @EXCEPTION Exception
     * */
    private void genUserData(FillRequest request) throws Exception {
        String father = "";
        String mother = "";
        if(TOTAL_GENERATIONS != 0) {
            father = people.get(people.size() - 2).getPersonID();
            mother = people.get(people.size() - 1).getPersonID();
        }
        User user = request.getUser();
        UserDao uDao = new UserDao();
        String newPersonID = UUID.randomUUID().toString();//GRAB the new personID for the user
        user.setPersonID(newPersonID);//set it
        uDao.updateUser(user);//update it in the user table
        people.add(new Person(newPersonID, user.getUserName(), user.getfName(), user.getlName(), user.getGender(), father, mother, ""));
        genEvents(people.get(people.size() - 1), request.getLocations(), 0, user.getUserName());
    }

    /**
     * Generates a generation of people
     *
     * @PARAM int current generation
     * @PARAM FillRequest, the request to fill
     * @PARAM int adderindex
     * @RETURN adderindex
     * @EXCEPTION Exception
     */
    private int genPeople(int currGeneration, FillRequest request, int adderIndex) throws Exception {
        int numOfPeople = (int) Math.pow(2, currGeneration);
        for (int i = 0; i < numOfPeople; i++) {//create all people of this generation with all their connections
            if (i % 2 == 0) {//male
                people.add(getMale(request.getmNames(), request.getlNames(),request.getUserName()));
                genEvents(people.get(people.size() - 1), request.getLocations(), currGeneration,request.getUserName());

            } else {//female
                people.add(getFemale(request.getfNames(), request.getlNames(),request.getUserName()));
                genEvents(people.get(people.size() - 1), request.getLocations(), currGeneration,request.getUserName());

                //add spouses together
                if (currGeneration == TOTAL_GENERATIONS) {
                    people.get(i - 1).setSpouse(people.get(i).getPersonID());
                    people.get(i).setSpouse(people.get(i - 1).getPersonID());
                }
                createMarriage(currGeneration, people.get(people.size()-2), people.get(people.size()-1), request.getLocations(),request.getUserName());//marry this couple

                if (currGeneration < TOTAL_GENERATIONS) {//once we are past the oldest generation
                    adderIndex = setMothersAndFathers(adderIndex);
                }
            }//else
        }//for
        return adderIndex;
    }

    /**
     * Sets the values of mothers and Fathers
     * @PARAM Int, adder index to keep track of where we are at in the arraylist
     * @RETURN int, the adderindex so its parent can keep it updated
     * */
    private int setMothersAndFathers(int adderIndex){
        people.get(people.size() - 2).setSpouse(people.get(people.size() - 1).getPersonID());
        people.get(people.size() - 1).setSpouse(people.get(people.size() - 2).getPersonID());
        people.get(people.size() - 2).setFather(people.get(adderIndex).getPersonID());
        adderIndex++;
        people.get(people.size() - 2).setMother(people.get(adderIndex).getPersonID());
        adderIndex++;
        people.get(people.size() - 1).setFather(people.get(adderIndex).getPersonID());
        adderIndex++;
        people.get(people.size() - 1).setMother(people.get(adderIndex).getPersonID());
        adderIndex++;
        return adderIndex;
    }
    /**
     * Gets female persons
     *
     * @PARAM fNames, the array of possible male first names
     * @PARAM lNames, the array of possible last names
     * @RETURN whether the generation process was successful or not
     * @EXCEPTION Exception
     */
    private Person getFemale(String[] fNames, String[] lNames,String userName) throws Exception {
        UUID uuid = UUID.randomUUID();
        Random lNameRand = new Random();
        Random fNameRand = new Random();
        return new Person(uuid.toString(),userName, fNames[fNameRand.nextInt(fNames.length)],
                lNames[lNameRand.nextInt(lNames.length)], "f", "", "", "");
    }

    /**
     * Gets male persons
     *
     * @PARAM lNames, the array of possible last names
     * @PARAM mNames, the array of possible male first names
     * @RETURN whether the generation process was successful or not
     * @EXCEPTION Exception
     */
    private Person getMale(String[] mNames, String[] lNames,String userName) throws Exception {
        Random mNameRand = new Random();
        Random lNameRand = new Random();
        UUID uuid = UUID.randomUUID();
        return new Person(uuid.toString(),userName, mNames[mNameRand.nextInt(mNames.length)],
                lNames[lNameRand.nextInt(lNames.length)], "m", "", "", "");
    }

    /**
     * Generates the events of the people generated. Called after the data has been generated
     *
     * @PARAM people, the array of people that has been generated
     * @PARAM locations, the array of locations to choose from
     * @RETURN void
     * @EXCEPTION Exception
     */
    private void genEvents(Person person, Location[] locations, int currGeneration,String userName) throws Exception {
        int startYear = 1970 - (currGeneration * 50);
        String[] eventType = {"Birth", "Baptism", "Death"};
        Random locRand = new Random();
        for (String eventT : eventType) {//for every kind of event
            UUID uuid = UUID.randomUUID();
            int location = locRand.nextInt(locations.length);
            int year = getYear(eventT, startYear);
            Location loc = locations[location];

           // if(eventLocMap){
                events.add(new Event(uuid.toString(), userName, person.getPersonID(), loc, Integer.toString(year), eventT));
           // }
           // eventLocMap.put(loc,locd);
        }
    }

    /**
     * Gets an appropriate year to use for our new event
     *
     * @PARAM index, where along the generation tree we are
     * @PARAM eventType, the type of event we are gettinga year for
     * @PARAM perviousYear, the previous year used for this person in an event
     * @RETURN year, the year of our new event
     * @EXCEPTION Exception
     */
    private int getYear(String eventType, int startYear) throws Exception {
        switch (eventType) {
            case "Birth":
                return getBirthDates(startYear);
            case "Baptism":
                return getBaptismDates(startYear);
            default: //death
                return getDeathDates(startYear);
        }


    }

    /**
     * Gets the dates for births
     *
     * @PARAM startYear, where along the generation tree we are year wise
     * @RETURN the year for said event
     * @EXCEPTION Exception
     */
    private int getBirthDates(int startYear) throws Exception {
        Random yearRand = new Random();
        return (yearRand.nextInt((startYear + 5) - startYear) + startYear);//born btwn 0-5 years after "start" date for this generation
    }

    /**
     * Gets the dates for baptisms
     *
     * @PARAM startYear, where along the generation tree we are year wise
     * @RETURN the year for said event
     * @EXCEPTION Exception
     */
    private int getBaptismDates(int startYear) throws Exception {
        Random yearRand = new Random();
        return (yearRand.nextInt((startYear + 10) - (startYear + 5)) + (startYear + 5));//baptized btwn 5-10
    }
    //private Set<String> stuff = new TreeSet<>();
    /**
     * Gets the dates for marriages
     *
     * @PARAM yearRand, the random number generator
     * @RETURN the year for said event
     * @EXCEPTION Exception
     */
    private void createMarriage(int currGeneration, Person father, Person mother, Location[] location,String userName) throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Random locRand = new Random();

        int loc = locRand.nextInt(location.length);
        int startYear = 2017 - (currGeneration * 50);
        Random yearRand = new Random();
        /*//System.out.println(father.getPersonID() + "\n" + mother.getPersonID());
        if(stuff.contains(father.getPersonID()) || stuff.contains(mother.getPersonID())){
            events = events;//TODO we are going back to the begininng somehow of the gen when doing these marraiges
        }
        else{
            stuff.add(father.getPersonID());
            stuff.add(mother.getPersonID());
        }*/
        int marriageYear = (yearRand.nextInt((startYear + 35) - (startYear + 18)) + (startYear + 18));//getting married between 18-35
        events.add(new Event(uuid.toString(), userName, father.getPersonID(), location[loc], Integer.toString(marriageYear), "Marriage"));
        events.add(new Event(uuid2.toString(), userName, mother.getPersonID(), location[loc], Integer.toString(marriageYear), "Marriage"));
    }

    /**
     * Gets the dates for deaths
     *
     * @PARAM startYear, the year we start with in this generation/person
     * @RETURN the year for said event
     * @EXCEPTION Exception
     */
    private int getDeathDates(int startYear) throws Exception {
        Random yearRand = new Random();
        int year = (yearRand.nextInt((startYear + 90) - (startYear + 50)) + (startYear + 50));//die btwn 50-80ish years after birth
        if (year > 2017) {
            return 2016;
        }
        return year;
    }

    /**
     * Inserts the events and people generated
     * @PARAM FillRequest
     * @RETURN void
     * @EXCEPTION Exception,SQLException
     */
    private FillResult insertData(FillRequest request) throws SQLException,Exception{
        PersonDao pDao = new PersonDao();
        EventDao eDao = new EventDao();
        for (int i = 0; i < people.size(); i++) {
            pDao.insertPerson(people.get(i));
        }
        for (Event e : events) {
            eDao.insertEvent(e);
        }
        return new FillResult( events.size(),people.size(),people.get(people.size() -1).getPersonID());
    }

}
