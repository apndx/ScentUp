
package scentup.dao;

import java.sql.SQLException;
import scentup.domain.Scent;

/**
 *
 * @author apndx
 */
public interface SDao {

    /**
     * Checks if Scent exists in the database
     *
     * @param name name of the scent that needs to be found
     * @param brand brand of the scent that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return true if found, else false
     */
    boolean checkIfScentExists(String name, String brand) throws SQLException;

    /**
     * Deletes a Scent and all UserScents that are related to it, finds them by
     * name and brand
     *
     * @param name name of the scent that will be deleted
     * @param brandname brand of the scent that will be deleted
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    void delete(String name, String brandname) throws SQLException;

    /**
     * Finds a scent from the database. Uses the method forFindOne for SQL
     *
     * @param name name of the scent that needs to be found
     * @param brand brand of the scent that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return returns the scent if it is found, otherwise returns null
     */
    Scent findOne(String name, String brand) throws SQLException;

    /**
     * Saves a scent if it does not exist already
     *
     * @param object scent that needs to be saved
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return object. If the scentId of the scent is null, private method save
     * is used and a saved scent is returned. If id is found, null is returned.
     */
    Scent saveOrNot(Scent object) throws SQLException;
    
}
