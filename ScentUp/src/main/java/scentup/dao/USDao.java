/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.dao;

import java.sql.SQLException;
import scentup.domain.UserScent;

/**
 * Interface for UserScentDao
 * 
 * @author apndx
 */
public interface USDao {

    /**
     * Adds a new UserScent in the database
     *
     * @param userScent userScent that is added
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    void add(UserScent userScent) throws SQLException;

    /**
     * Checks if a UserScent exists already by userId and scentId
     *
     * @param userId id for the user
     * @param scentId id for the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean returns true if the UserScent exists, false if it does
     * not
     */
    boolean checkIfUserScentExists(Integer userId, Integer scentId) throws SQLException;

    /**
     * Deletes a UserScent from the database by userId and scentId
     *
     * @param userId id for the user
     * @param scentId if for the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    void delete(Integer userId, Integer scentId) throws SQLException;

    /**
     * Finds a user from the database by username
     *
     * @param userId userId of the user
     * @param scentId scentId of the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return userScent userScent is returned if found, else null is returned.
     */
    UserScent findOne(Integer userId, Integer scentId) throws SQLException;
    
}
