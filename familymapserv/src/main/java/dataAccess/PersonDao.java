package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import infoObjects.PersonRequest;
import models.Person;

/**
 * Created by tyler on 2/10/2017.
 * Our DAO to access the database, and get a person or multiple people from it
 */

public class PersonDao {
    DataBase db;
    private String INSERT = "INSERT into person (personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID) values (?,?,?,?,?,?,?,?)";
    private String SELECT = "SELECT personID,descendant,firstName,lastName, gender,fatherID,motherID,spouseID FROM person WHERE personID = ?";
    private String SELECT_ON_USER = "SELECT personID,descendant,firstName,lastName,gender,fatherID,motherID,spouseID FROM person WHERE descendant = ?";
    public PersonDao() {
        db = new DataBase();
    }

    /***
     * A method to insert a user's ancestor's info
     *
     * @PARAM Person, the person to be inserted
     * @RETURN boolean
     * @EXCEPTION throws SQLException
     */
    public boolean insertPerson(Person person)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;//insert statement
        //CHECK TO SEE if USER IS UNIQUE
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1,person.getPersonID());
            stmt.setString(2,person.getDescendant());
            stmt.setString(3,person.getfName());
            stmt.setString(4,person.getlName());
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFather());
            stmt.setString(7,person.getMother());
            stmt.setString(8,person.getSpouse());
            if(stmt.executeUpdate() == 1){//execute the statement
                db.closeConnection(true, conn);
                //ALSO LOG US ON
                return true;
            }
            if(!conn.isClosed()){db.closeConnection(false, conn);}
        }catch(SQLException e){
            e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
            //THROW AN ERROR HERE SO THAT THE RESULT CREATED HOLDS AN ERROR?
        }
        finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }

    /***
     * A method to delete a user's ancestor's info
     *
     * @PARAM A String: the UserID of the person being deleted
     * @RETURN boolean
     * @EXCEPTION throws SQLException
     */
    public boolean deletePerson(String userName)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;//insert statement
        //CHECK TO SEE if USER IS UNIQUE
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement("DELETE FROM person WHERE descendant =?");
            stmt.setString(1,userName);
            if(stmt.executeUpdate() >= 0){//execute the statement
                db.closeConnection(true, conn);
                //ALSO LOG US ON
                return true;
            }
            if(!conn.isClosed()){db.closeConnection(false, conn);}
        }catch(SQLException e){
            e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }

    /***
     * A method to get a user's ancestor's info
     *
     * @PARAM PersonREquest, has info needed to make request
     * @RETURN returns the data needed to start to make a person object
     * @EXCEPTION throws SQLException
     */
    public ArrayList<String> getPerson(PersonRequest request)throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;//insert statement
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(SELECT);
            stmt.setString(1,request.getPersonID());
            rs = stmt.executeQuery();//execute the statement
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            ArrayList<String> columns = new ArrayList<>();
            if(rs.next()){
                for(int i = 1; i <= columnCount; i++){
                    columns.add(rs.getString(i));
                }
            }
            if(columns.size() == 0){db.closeConnection(false, conn);}
            else{//if we got a result
                db.closeConnection(true, conn);
                return columns;
            }
        }catch(SQLException e){
            e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(rs);
            DataBase.safeClose(stmt);
        }
        return null;
    }


    /***
     * A method to get all of a users ancestor's
     *
     * @PARAM userID, the ID for a specific user
     * @RETURN returns the data needed to start to make people objects
     * @EXCEPTION throws SQLException
     */
    public ArrayList<ArrayList<String>> getPeople(String userID)throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<ArrayList<String>> allRows = new ArrayList<>();//all the people
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(SELECT_ON_USER);
            stmt.setString(1,userID);
            rs = stmt.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            while(rs.next()){
                ArrayList<String> columns = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++){
                    columns.add(rs.getString(i));
                }
                allRows.add(columns);
            }
            if(allRows.size() == 0) {//nothing was added
                db.closeConnection(false, conn);
            }
            else{
                db.closeConnection(true, conn);
                return allRows;
            }
        }
        catch(SQLException e){
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(rs);
            DataBase.safeClose(stmt);
        }
        return null;
    }

    /***
     * A method to get the userName of a person
     *
     * @PARAM userID, the ID for a specific user
     * @RETURN returns the data needed to start to make people objects
     * @EXCEPTION throws SQLException
     */
    public String getUserOfPerson(String personID)throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<ArrayList<String>> allRows = new ArrayList<>();//all the people
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement("SELECT descendant FROM person WHERE personID = ?");
            stmt.setString(1,personID);
            rs = stmt.executeQuery();
            if(rs.next()){
                String userName = rs.getString(1);
                db.closeConnection(true, conn);
                return userName;//get that userName
            }
            db.closeConnection(false, conn);
        }
        catch(SQLException e){
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(rs);
            DataBase.safeClose(stmt);
        }
        return null;
    }

    //DO DELETES
}
