package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import infoObjects.EventRequest;
import models.Event;

/**
 * Created by tyler on 2/10/2017.
 * Our DAO class for events
 */

public class EventDao {
    DataBase db;
    /**Select on the whole table where eventID = ?*/
    private String SELECT = "SELECT eventID,descendant,personID,year,eventType,city,latitude,longitude,country FROM events WHERE eventID = ?";
    /**Select on the whole table where descendant = ?*/
    private String SELECT_STAR = "SELECT eventID,descendant,personID,year,eventType,city,latitude,longitude,country FROM events WHERE descendant = ?";
    /**Insert into every column of the table the table creating a new row*/
    private String insertEvents = "insert into events (eventID,descendant,personID,year,eventType,city,latitude,longitude,country) values (?,?,?,?,?,?,?,?,?)";
    public EventDao() {
        db = new DataBase();
    }
    /**
     * Inserts Events into our database
     * @PARAM event, the event to insert
     * @RETURN boolean
     * @EXCEPTION throws SQL exception
     * */
    public boolean insertEvent(Event event) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(insertEvents);
            stmt.setString(1,event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, event.getYear());
            stmt.setString(5, event.getEventType());
            stmt.setString(6, event.getCity());
            stmt.setString(7, event.getLatitude());
            stmt.setString(8, event.getLongitude());
            stmt.setString(9, event.getCountry());
            if(stmt.executeUpdate() == 1){//execute the statement
                db.closeConnection(true, conn);
                return true;
            }
            if(!conn.isClosed()){db.closeConnection(false, conn); }
        }
        catch(SQLException e){
           // e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }
    /***
     * A method to get a specific event
     *
     * @PARAM request, the info for a specific event
     * @RETURN gets the result of attempting to get a specific event
     * @EXCEPTION throws SQL exception
     */
    public ArrayList<String> getEvent(EventRequest request) throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;//insert statement
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(SELECT);
            stmt.setString(1,request.getEventID());
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
            //e.printStackTrace();
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
     * A method to get all of a user's ancestor's events
     *
     * @PARAM the descendant we are getting all the events from
     * @RETURN gets the result of attempting to get all the users ancestor's events
     * @EXCEPTION throws SQL exception
     */
    public ArrayList<ArrayList<String>> getEvents(String descendant) throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;//insert statement
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(SELECT_STAR);
            stmt.setString(1,descendant);
            rs = stmt.executeQuery();//execute the statement
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            ArrayList<ArrayList<String>> rows = new ArrayList<>();
            while(rs.next()){
                ArrayList<String> columns = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++){
                    columns.add(rs.getString(i));
                }
                rows.add(columns);
            }
            if(rows.size() == 0){db.closeConnection(false, conn);}
            else{//if we got a result
                db.closeConnection(true, conn);
                return rows;
            }
        }catch(SQLException e){
           // e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(rs);
            DataBase.safeClose(stmt);
        }
        return null;
    }

    /**
     * deletes from the event table
     * @PARAM String descendant,the user ID of event that needs to be deleted
     * @RETURN whether the deletion was successful or not
     * @EXCEPTION throws SQLException
     * */
    public boolean deleteEvents(String descendant)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement("DELETE FROM events WHERE descendant = ?");
            stmt.setString(1,descendant);
            if(stmt.executeUpdate() >=0 ){//execute the statement
                db.closeConnection(true, conn);
                return true;
            }
            if(!conn.isClosed()){db.closeConnection(false, conn);}
        }
        catch(SQLException e){
            //e.printStackTrace();
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }
//DO DELETES
}
