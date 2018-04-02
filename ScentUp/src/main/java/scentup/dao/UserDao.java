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

    public UserDao(Database database) {
        this.database = database;
    }

    public User findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        User user = new User(rs.getInt("userid"), rs.getString("name"),
                rs.getString("username"));

        stmt.close();
        rs.close();

        conn.close();

        return user;
    }

    public List<User> findAll() throws SQLException {

        return null;
    }

    public User saveOrUpdate(User object) throws SQLException {
        // if there is no key, the user has not been yet created to database
        // so it needs to be created

        if (object.getUserId() == null) {
            return save(object);
        } else {
            // muulloin päivitetään asiakas
            return update(object);
        }

    }

    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE id = ?");

        stmt.setInt(1, key);
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
                + " WHERE username = ? AND name = ?");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getName());

        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos

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
