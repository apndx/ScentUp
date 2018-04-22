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
import scentup.domain.User;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 */
public class UserScentDao {

    private Database database;

    public UserScentDao(Database database) {
        this.database = database;
    }

    public boolean checkIfUserScentExists(Integer userId, Integer scentId) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent WHERE user_id = ? AND scent_id = ? ");
        stmt.setInt(1, userId);
        stmt.setInt(2, scentId);

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

    public List<UserScent> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent");
        ResultSet rs = stmt.executeQuery();

        List<UserScent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(UserScent.rowToUserScent(rs));
        }

        stmt.close();
        rs.close();

        conn.close();
        return listOfAll;

    }

    public List<UserScent> findAllForUser(Integer active, Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent WHERE Active = ? AND user_id = ? "
                + "AND scent_id = ? ");

        stmt.setInt(1, active);
        stmt.setInt(2, userId);

        ResultSet rs = stmt.executeQuery();

        List<UserScent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(UserScent.rowToUserScent(rs));
        }

        stmt.close();
        rs.close();

        conn.close();
        return listOfAll;
    }

    public List<Scent> findAllScentsUserHas(Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        // sql not working yet
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent "
                + "LEFT JOIN UserScent ON Scent.scent_id = userscent.scent_id  "
                + "WHERE userscent.user_id = ? ");

        stmt.setInt(1, userId);
        //stmt.executeUpdate();

        ResultSet rs = stmt.executeQuery();
        List<Scent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(Scent.rowToScent(rs));
        }

        stmt.close();
        conn.close();

        return listOfAll;
    }

    public List<Scent> findAllScentsUserHasNotByCriteria(Integer timeOfDay,
            Integer season, Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        // sql not working yet
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE NOT EXISTS "
                + "(SELECT * FROM userScent where user_id = 1 AND scent.scent_id = userScent.scent_id) "
                + "AND scent.timeofday = ? AND scent.season = ?;");

        stmt.setInt(1, timeOfDay);
        stmt.setInt(2, season);
        stmt.setInt(3, userId);
        stmt.executeUpdate();

        ResultSet rs = stmt.executeQuery();

        List<Scent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(Scent.rowToScent(rs));
        }

        stmt.close();
        conn.close();

        return listOfAll;
    }

    public List<Scent> findAllScentsUserHasNot(Integer userId) throws SQLException {

        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE NOT EXISTS "
                + "(SELECT * FROM userScent where user_id = ? AND scent.scent_id = userScent.scent_id);");

        stmt.setInt(1, userId);
        //stmt.executeUpdate();

        ResultSet rs = stmt.executeQuery();

        List<Scent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(Scent.rowToScent(rs));
        }

        stmt.close();
        conn.close();

        return listOfAll;
    }

    public void delete(Integer userId, Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM UserScent WHERE user_id = ? AND scent_id = ?");

        stmt.setInt(1, userId);
        stmt.setInt(2, scentId);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void add(UserScent userScent) throws SQLException {
        try (Connection c = database.getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO UserScent (user_id, scent_id, choicedate, preference, active) VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, userScent.getUser().getUserId());
            ps.setInt(2, userScent.getScent().getScentId());
            ps.setDate(3, userScent.getChoiceDate());
            ps.setInt(4, userScent.getPreference());
            ps.setInt(5, userScent.getActive());

            ps.executeUpdate();

            ps.close();
            c.close();
        }
    }

}
