/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import scentup.domain.User;

/**
 *
 * @author hdheli
 */
public class UserDao {

    private Database database;

     /**
     * Makes a new UserDao. This is used to communicate with the user table in
     * database.
     *
     * @param database database
     */
    public UserDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a user from the database.
     *
     * @param username  username of the user that needs to be found
     * @throws SQLException if this database query does not succeed, this exception is thrown
     * @return User  user is returned if found, else null is returned.
     */
    public User findOne(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            stmt.close();
            rs.close();
            conn.close();

            return null;
        }

        User user = new User(rs.getInt("user_id"), rs.getString("name"),
                rs.getString("username"));

        stmt.close();
        rs.close();
        conn.close();

        return user;
    }

     /**
     * Finds if a username is already in the database
     *
     * @param username  username that needs to be checked
     * @throws SQLException if this database query does not succeed, this exception is thrown
     * @return boolean if username is free, returns true, else false
     */
    public boolean isUsernameFree(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            stmt.close();
            rs.close();
            conn.close();
            return true;
        } else {
            stmt.close();
            rs.close();
            conn.close();
            return false;
        }
    }

      /**
     * Not implemented yet - finds all users in the database
     *
     * @throws SQLException if this database query does not succeed, this exception is thrown
     * @return List of all users 
     */
    public List<User> findAll() throws SQLException {
        //this is not yet needed, can be implemented later
        return null;
    }

     /**
     * Saves or updates a user
     *
     * @param object user that needs to be saved or updated (updating not yet needed)
     * @throws  SQLException if this database query does not succeed, this exception is thrown
     * @return object. If the userId of the user is null, private method save is used and a saved user is returned
     * otherwise the user is updated using private update method, and updated user is returned
     */
    public User saveOrUpdate(User object) throws SQLException {
        // if there is no key, the user has not been yet created to database
        // so it needs to be created

        if (object.getUserId() == null) {
            return save(object);
        } else {
            // otherwise update user
            return update(object);
        }
    }

    /**
     * Deletes a user and all the UserScents the user has from the database
     *
     * @param username username of the user that needs to be deleted
     * 
     *@throws SQLException if this database query does not succeed, this exception is thrown
     * 
     */
    public void delete(String username) throws SQLException {
        Connection conn = database.getConnection();
        User removable = findOne(username);
        Integer userId = removable.getUserId();

        PreparedStatement stmtRelated = conn.prepareStatement("DELETE FROM UserScent WHERE user_id = ?");
        stmtRelated.setInt(1, userId);
        stmtRelated.executeUpdate();
        stmtRelated.close();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE username = ?");

        stmt.setString(1, username);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    private User save(User user) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User"
                + " (name, username)"
                + " VALUES (?, ?)");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM User"
                + " WHERE name = ?  AND username = ?");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        ResultSet rs = stmt.executeQuery();
        rs.next(); // just one row
        User u = new User(rs.getInt("user_id"), rs.getString("name"),
                rs.getString("username"));

        stmt.close();
        rs.close();
        conn.close();
        return u;
    }

    private User update(User user) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE User SET"
                + " name = ?, username = ?, WHERE id = ?");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.setInt(3, user.getUserId());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return user;
    }

}
