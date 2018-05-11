
package scentup.dao;

import java.sql.SQLException;
import scentup.domain.User;

/**
 * Interface for UserDao
 *
 * @author apndx
 */
public interface UDao {

    /**
     * Deletes a user and all the UserScents the user has from the database
     *
     * @param username username of the user that needs to be deleted
     *
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     *
     */
    void delete(String username) throws SQLException;

    /**
     * Finds a user from the database.
     *
     * @param username username of the user that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return User user is returned if found, else null is returned.
     */
    User findOne(String username) throws SQLException;

    /**
     * Finds if a username is already in the database
     *
     * @param username username that needs to be checked
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean if username is free, returns true, else false
     */
    boolean isUsernameFree(String username) throws SQLException;

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
    User saveOrNot(User object) throws SQLException;
    
}
