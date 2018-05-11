package scentup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import scentup.domain.User;

/**
 * This class is for making database queries for the User table
 *
 * @author apndx
 */
public class UserDao implements UDao{

    private final Database database;

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
     * Finds a user from the database by username
     *
     * @param username username of the user that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return User user is returned if found, else null is returned.
     */
    @Override
    public User findOne(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return null;
        }

        User user = new User(rs.getInt("user_id"), rs.getString("name"),
                rs.getString("username"));

        closingProceduresRs(conn, rs, stmt);
        return user;
    }

    /**
     * Finds if a username is already in the database
     *
     * @param username username that needs to be checked
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean if username is free, returns true, else false
     */
    @Override
    public boolean isUsernameFree(String username) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return true;
        } else {
            closingProceduresRs(conn, rs, stmt);
            return false;
        }
    }

    /**
     * Saves a user if it does not exist already
     *
     * @param object user that needs to be saved
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return object. If the userId of the user is null, private method save is
     * used and a saved user is returned. If user is found with this id, method
     * returns null.
     */
    @Override
    public User saveOrNot(User object) throws SQLException {

        if (object.getUserId() == null) {
            return save(object);
        } else {
            return null;
        }
    }

    /**
     * Deletes a user and all the UserScents the user has from the database
     *
     * @param username username of the user that needs to be deleted
     *
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     *
     */
    @Override
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
        closingProceduresUpdate(conn, stmt);
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

        closingProceduresRs(conn, rs, stmt);
        return u;
    }

    private void closingProceduresRs(Connection conn, ResultSet rs, PreparedStatement stmt) throws SQLException {
        stmt.close();
        rs.close();
        conn.close();
    }

    private void closingProceduresUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
