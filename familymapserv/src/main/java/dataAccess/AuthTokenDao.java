package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.AuthToken;

/**
 * Created by tyler on 2/10/2017.
 * Gets our Auth Tokens from our database
 */

public class AuthTokenDao {
    /**Our insert string to fill our authtoken table*/
    private String insertIntoAuth = "insert into authToken (descendant, authToken, timeStamp) values ( ?, ?, ? )";
    /**Our insert string to get an authToken*/
    private String getAuthToken = "SELECT authToken FROM authToken WHERE descendant = ?";
    /**Our Database object*/
    private DataBase db;
    public AuthTokenDao() {
        db = new DataBase();
    }

    /**Inserts authtokens into the database keyed to userIDs
     * @PARAM userID, the userId we are inserting
     * @PARAM authTok, the authTok we are inserting
     * @RETURN whether the insert was successful or not
     * @EXCEPTION throws SQLException
     * */
    public boolean insertAuthToken(String userID,AuthToken authTok) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(insertIntoAuth);
            stmt.setString(1, userID);
            stmt.setString(2, authTok.getAuthToken());
            stmt.setLong(3, authTok.getTimeStamp());
            if (stmt.executeUpdate() == 1) {//execute the statement
                db.closeConnection(true, conn);
                return true;
            }
            if (!conn.isClosed()) {
                db.closeConnection(false, conn);
            }
        } catch (SQLException e) {
            db.closeConnection(false, conn);
            throw e;
        } finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }

    /**Deletes authtokens from the database that are keyed to userIDs
     * @PARAM userID, the userId we are inserting
     * @RETURN whether the deletion was successful or not
     * @EXCEPTION throws SQLException
     * */
    public boolean deleteAuthToken(String userID) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement("DELETE FROM authToken WHERE descendant = ?");
            stmt.setString(1, userID);
            if (stmt.executeUpdate() >= 1) {//execute the statement
                db.closeConnection(true, conn);
                return true;
            }
            if (!conn.isClosed()) {
                db.closeConnection(false, conn);
            }
        } catch (SQLException e) {
            db.closeConnection(false, conn);
            throw e;
        } finally {
            DataBase.safeClose(stmt);
        }
        return false;
    }

    /**Gets the Authtoken(s) of a particular user
     * @PARAM userID, The userID of the AuthToken(s) we are getting
     * @RETURN ArrayList, the list of authtokens belonging to this user
     * @EXCEPTION throws SQLException
     */
    public ArrayList<String> getAuthToken(String userID) throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(getAuthToken);
            stmt.setString(1,userID);
            rs = stmt.executeQuery();
            while(rs.next()){//execute the statement
                output.add(rs.getString("authToken"));
            }
            if(output.size() == 0){//nothing was added
                db.closeConnection(false, conn);
            }
            else{
                db.closeConnection(true, conn);
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
        return output;
    }

    /**
     * A method to get a user ID from the database from an authToken
     * @Param authToken, get a user by an Authtoken
     * @RETURN the userID related to the authToken
     * @EXCEPTION throws SQLException
     */
    public String getUserIDFromAuthToken(String authToken) throws SQLException{
        String SQLString = "SELECT descendant FROM authToken WHERE authToken = ?";
        Connection conn = null;
        PreparedStatement stmt = null;//insert statement
        ResultSet rs = null;
        String output = "";
        try {
            conn = db.openConnection();
            stmt = conn.prepareStatement(SQLString);
            stmt.setString(1,authToken);
            rs = stmt.executeQuery();//execute the statement
            if(rs.next()){
                output = rs.getString(1);
                db.closeConnection(true, conn);
            }
            if(!conn.isClosed()){db.closeConnection(false, conn);}
        }catch(SQLException e){
            db.closeConnection(false, conn);
            throw e;
        }
        finally {
            DataBase.safeClose(rs);
            DataBase.safeClose(stmt);
        }
        return output;
    }

    /**
     * Validates authtokens given by the user
     * @PARAM String, an Authtoken
     * @RETURN boolean, if is validated that this authToken exists
     * @EXCEPTION SQLException
     * */
    public boolean validateAuthToken(String authTok)throws SQLException{
        AuthTokenDao aDao = new AuthTokenDao();
        String userID = aDao.getUserIDFromAuthToken(authTok);//grab the userID associated with this authtoken
        if(!userID.equals("")) {
            return true;
        }
        return false;
    }
}
