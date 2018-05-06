/*
 * This class is for making database queries for the Scent table
 */
package scentup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.domain.Scent;

/**
 * @author apndx
 */
public class ScentDao {

    private Database database;

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
    public Scent findOne(String name, String brand) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE name = ? AND brand = ?");
        stmt.setString(1, name);
        stmt.setString(2, brand);
        return forFindOne(conn, stmt);
    }

    private Scent forFindOne(Connection conn, PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProcedures(conn, rs, stmt);
            return null;
        }
        Scent scent = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));
        closingProcedures(conn, rs, stmt);
        return scent;
    }

    public boolean checkIfScentExists(String name, String brand) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE name = ? AND brand = ? ");
        stmt.setString(1, name);
        stmt.setString(2, brand);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProcedures(conn, rs, stmt);
            return false;
        }
        closingProcedures(conn, rs, stmt);
        return true;
    }

    public List<Scent> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent");
        ResultSet rs = stmt.executeQuery();
        List<Scent> listOfAll = new ArrayList<>();
        while (rs.next()) {
            Scent s = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                    rs.getString("brand"), rs.getInt("timeofday"),
                    rs.getInt("season"), rs.getInt("gender"));
            listOfAll.add(s);
        }
        closingProcedures(conn, rs, stmt);
        return listOfAll;
    }

    public Scent saveOrUpdate(Scent object) throws SQLException {
        // if there is no key, the scent has not been yet created, needs to be done
        if (object.getScentId() == null) {
            return save(object);
        } else { // otherwise update scent       
            return update(object);
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
        stmt.executeUpdate();
        stmt.close();
        conn.close();
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
    public void delete(String name, String brandname) throws SQLException {
        Connection conn = database.getConnection();
        Scent removable = findOne(name, brandname);
        Integer scentId = removable.getScentId();
        userScentDelete(conn, scentId);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Scent WHERE name = ?  AND brand = ? ");
        stmt.setString(1, name);
        stmt.setString(2, brandname);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    private void userScentDelete(Connection conn, Integer scentId) throws SQLException {
        PreparedStatement stmtRelated = conn.prepareStatement("DELETE FROM UserScent WHERE scent_id = ?");
        stmtRelated.setInt(1, scentId);
        stmtRelated.executeUpdate();
    }

    private Scent save(Scent scent) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Scent"
                + " (name, brand, timeofday, season, gender)"
                + " VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, scent.getName());
        stmt.setString(2, scent.getBrand());
        stmt.setInt(3, scent.getTimeOfDay());
        stmt.setInt(4, scent.getSeason());
        stmt.setInt(5, scent.getGender());
        stmt.executeUpdate();
        stmt.close();
        stmt = conn.prepareStatement("SELECT * FROM Scent"
                + " WHERE name = ?  AND brand = ?");
        stmt.setString(1, scent.getName());
        stmt.setString(2, scent.getBrand());
        ResultSet rs = stmt.executeQuery();
        rs.next(); // only one row
        Scent s = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"),
                rs.getInt("gender"));
        closingProcedures(conn, rs, stmt);
        return s;
    }

    private Scent update(Scent scent) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Scent SET"
                + " name = ?, brand = ?, WHERE scent_id = ?");
        stmt.setString(1, scent.getName());
        stmt.setString(2, scent.getBrand());
        stmt.setInt(3, scent.getScentId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return scent;
    }

    private void closingProcedures(Connection conn, ResultSet rs, PreparedStatement stmt) throws SQLException {
        stmt.close();
        rs.close();
        conn.close();
    }
}
