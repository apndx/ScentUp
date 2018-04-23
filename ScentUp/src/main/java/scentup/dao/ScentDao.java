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
import java.util.ArrayList;
import java.util.List;
import scentup.domain.Scent;

/**
 *
 * @author hdheli
 */
public class ScentDao {

    private Database database;

    public ScentDao(Database database) {
        this.database = database;
    }

    public Scent findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE scent_id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            stmt.close();
            rs.close();
            conn.close();
            return null;
        }

        Scent scent = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));

        stmt.close();
        rs.close();
        conn.close();
        return scent;
    }

    public Scent findOne(String name, String brand) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE name = ? AND brand = ?");
        stmt.setString(1, name);
        stmt.setString(2, brand);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            stmt.close();
            rs.close();
            conn.close();
            return null;
        }

        Scent scent = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"), rs.getInt("gender"));
        stmt.close();
        rs.close();
        conn.close();
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
            stmt.close();
            rs.close();
            conn.close();
            return false;
        }

        stmt.close();
        rs.close();
        conn.close();
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

        stmt.close();
        rs.close();
        conn.close();
        return listOfAll;
    }

    public Scent saveOrUpdate(Scent object) throws SQLException {
        // if there is no key, the user has not been yet created to database
        // so it needs to be created
        if (object.getScentId() == null) {
            return save(object);
        } else { // otherwise update scent       
            return update(object);
        }

    }

    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Scent WHERE scent_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void delete(String name, String brandname) throws SQLException {
        Connection conn = database.getConnection();

        Scent poistettava = findOne(name, brandname);
        Integer scentId = poistettava.getScentId();
        PreparedStatement stmtriippuvuudet = conn.prepareStatement("DELETE FROM UserScent WHERE scent_id = ?");
        stmtriippuvuudet.setInt(1, scentId);
        stmtriippuvuudet.executeUpdate();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Scent WHERE name = ?  AND brand = ? ");

        stmt.setString(1, name);
        stmt.setString(2, brandname);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
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
        rs.next(); // vain 1 tulos

        Scent s = new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"),
                rs.getInt("gender"));

        stmt.close();
        rs.close();
        conn.close();
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
}
