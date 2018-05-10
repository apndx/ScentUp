package scentup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.domain.Scent;

/**
 * This class is for making database queries for the Scent table
 *
 * @author apndx
 */
public class ScentDao implements SDao {

    private final Database database;

    public ScentDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a scent from the database. Uses the method forFindOne for SQL
     *
     * @param scentId id of the scent that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return returns the scent if it is found, otherwise returns null
     */
    public Scent findOne(Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE scent_id = ?");
        stmt.setInt(1, scentId);
        return forFindOne(conn, stmt);
    }

    /**
     * Finds a scent from the database. Uses the method forFindOne for SQL
     *
     * @param name name of the scent that needs to be found
     * @param brand brand of the scent that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return returns the scent if it is found, otherwise returns null
     */
    @Override
    public Scent findOne(String name, String brand) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE scentname = ? AND brand = ?");
        stmt.setString(1, name);
        stmt.setString(2, brand);
        return forFindOne(conn, stmt);
    }

    private Scent forFindOne(Connection conn, PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return null;
        }
        Scent scent = new Scent(rs.getInt("scent_id"), rs.getString("scentname"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));
        closingProceduresRs(conn, rs, stmt);
        return scent;
    }

    /**
     * Checks if Scent exists in the database
     *
     * @param name name of the scent that needs to be found
     * @param brand brand of the scent that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return true if found, else false
     */
    @Override
    public boolean checkIfScentExists(String name, String brand) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE scentname = ? AND brand = ? ");
        stmt.setString(1, name);
        stmt.setString(2, brand);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return false;
        }
        closingProceduresRs(conn, rs, stmt);
        return true;
    }

    /**
     * Lists all scents found in the database
     *
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return List returns a list of all scents in the database
     */
    public List<Scent> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent");
        ResultSet rs = stmt.executeQuery();
        List<Scent> listOfAll = new ArrayList<>();
        while (rs.next()) {
            Scent s = new Scent(rs.getInt("scent_id"), rs.getString("scentname"), rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));
            listOfAll.add(s);
        }
        closingProceduresRs(conn, rs, stmt);
        return listOfAll;
    }

    /**
     * Saves a scent if it does not exist already
     *
     * @param object scent that needs to be saved
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return object. If the scentId of the scent is null, private method save
     * is used and a saved scent is returned. If id is found, null is returned.
     */
    @Override
    public Scent saveOrNot(Scent object) throws SQLException {
        if (object.getScentId() == null) {
            return save(object);
        } else {
            return null;
        }
    }

    /**
     * Deletes a Scent and all UserScents that are related to it, finds them by
     * scentId
     *
     * @param scentId id of the scent that will be deleted
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void delete(Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        userScentDelete(conn, scentId);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Scent WHERE scent_id = ?");
        stmt.setInt(1, scentId);
        closingProceduresUpdate(conn, stmt);
    }

    /**
     * Deletes a Scent and all UserScents that are related to it, finds them by
     * name and brand
     *
     * @param name name of the scent that will be deleted
     * @param brandname brand of the scent that will be deleted
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    @Override
    public void delete(String name, String brandname) throws SQLException {
        Connection conn = database.getConnection();
        Scent removable = findOne(name, brandname);
        Integer scentId = removable.getScentId();
        userScentDelete(conn, scentId);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Scent WHERE scentname = ?  AND brand = ? ");
        stmt.setString(1, name);
        stmt.setString(2, brandname);
        closingProceduresUpdate(conn, stmt);
    }

    private void userScentDelete(Connection conn, Integer scentId) throws SQLException {
        PreparedStatement stmtRelated = conn.prepareStatement("DELETE FROM UserScent WHERE scent_id = ?");
        stmtRelated.setInt(1, scentId);
        stmtRelated.executeUpdate();
    }

    private Scent save(Scent scent) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Scent (scentname, brand, timeofday, season, gender) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, scent.getName());
        stmt.setString(2, scent.getBrand());
        stmt.setInt(3, scent.getTimeOfDay());
        stmt.setInt(4, scent.getSeason());
        stmt.setInt(5, scent.getGender());
        stmt.executeUpdate();
        stmt.close();
        stmt = conn.prepareStatement("SELECT * FROM Scent WHERE scentname = ?  AND brand = ?");
        stmt.setString(1, scent.getName());
        stmt.setString(2, scent.getBrand());
        ResultSet rs = stmt.executeQuery();
        rs.next(); // only one row
        Scent s = new Scent(rs.getInt("scent_id"), rs.getString("scentname"), rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));
        closingProceduresRs(conn, rs, stmt);
        return s;
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
