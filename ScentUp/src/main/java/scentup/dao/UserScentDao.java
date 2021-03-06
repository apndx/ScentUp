package scentup.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.domain.Scent;
import scentup.domain.UserScent;

/**
 * This class is for making database queries for the UserScent table
 *
 * @author apndx
 */
public class UserScentDao implements USDao {

    private final Database database;

    public UserScentDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a user from the database by username
     *
     * @param userId userId of the user
     * @param scentId scentId of the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return userScent userScent is returned if found, else null is returned.
     */
    @Override
    public UserScent findOne(Integer userId, Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent, Scent, User WHERE userscent.user_id = ? AND userscent.scent_id = ?"
                + "AND userscent.user_id=user.user_id AND userscent.scent_id = scent.scent_id");
        stmt.setInt(1, userId);
        stmt.setInt(2, scentId);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return null;
        }
        UserScent userScent = UserScent.rowToUserScent(rs); // only one row
        closingProceduresRs(conn, rs, stmt);
        return userScent;
    }

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
    @Override
    public boolean checkIfUserScentExists(Integer userId, Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent WHERE user_id = ? AND scent_id = ? ");
        stmt.setInt(1, userId);
        stmt.setInt(2, scentId);

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
     * Lists all scents the user has by userId
     *
     * @param userId id for the user
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return List returns a list of all scents the user has
     */
    public List<Scent> findAllScentsUserHas(Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent "
                + "LEFT JOIN UserScent ON Scent.scent_id = userscent.scent_id  "
                + "WHERE userscent.user_id = ? ");

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        List<Scent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(Scent.rowToScent(rs));
        }
        closingProceduresRs(conn, rs, stmt);
        return listOfAll;
    }

    /**
     * Lists all scents the user does not have by userId
     *
     * @param userId id for the user
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return List returns a list of all scents the user does not have
     */
    public List<Scent> findAllScentsUserHasNot(Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE NOT EXISTS "
                + "(SELECT * FROM userScent where user_id = ? AND scent.scent_id = userScent.scent_id);");

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        List<Scent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(Scent.rowToScent(rs));
        }
        closingProceduresRs(conn, rs, stmt);
        return listOfAll;
    }

    /**
     * Lists all active scents the user has by userId
     *
     * @param userId id for the user
     * @param active this tells that the scent is in an active collection
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return List returns a list of all active scents the user has
     */
    public List<UserScent> findAllForUser(Integer active, Integer userId) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserScent, User, Scent WHERE userscent.active = ? AND user.user_id = ? "
                + "AND userscent.user_id=user.user_id AND userscent.scent_id=scent.scent_id");
        stmt.setInt(1, active);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        List<UserScent> listOfAll = new ArrayList<>();

        while (rs.next()) {
            listOfAll.add(UserScent.rowToUserScent(rs));
        }
        closingProceduresRs(conn, rs, stmt);
        return listOfAll;
    }

    /**
     * Deletes a UserScent from the database by userId and scentId
     *
     * @param userId id for the user
     * @param scentId if for the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    @Override
    public void delete(Integer userId, Integer scentId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM UserScent WHERE user_id = ? AND scent_id = ?");
        stmt.setInt(1, userId);
        stmt.setInt(2, scentId);
        closingProceduresUpdate(conn, stmt);
    }

    /**
     * Adds a new UserScent in the database
     *
     * @param userScent userScent that is added
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    @Override
    public void add(UserScent userScent) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserScent (user_id, scent_id, choicedate, preference, active) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, userScent.getUser().getUserId());
            stmt.setInt(2, userScent.getScent().getScentId());
            stmt.setDate(3, userScent.getChoiceDate());
            stmt.setInt(4, userScent.getPreference());
            stmt.setInt(5, userScent.getActive());
            closingProceduresUpdate(conn, stmt);
        }
    }

    /**
     * Changes the preference of a userScent
     *
     * @param userScent userScent that is altered
     * @param preference the new preference: 1 dislike, 2 neutral, 3 love
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changePreference(UserScent userScent, Integer preference) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE UserScent SET preference =? WHERE user_id = ? AND scent_id = ?");
        stmt.setInt(1, preference);
        stmt.setInt(2, userScent.getUser().getUserId());
        stmt.setInt(3, userScent.getScent().getScentId());
        closingProceduresUpdate(conn, stmt);
    }

    /**
     * Changes activation status of a userScent
     *
     * @param userScent userScent that is altered
     * @param active the new status: 0 no, 1 yes
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changeActiveStatus(UserScent userScent, Integer active) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE UserScent SET active =? WHERE user_id = ? AND scent_id = ?");
        stmt.setInt(1, active);
        stmt.setInt(2, userScent.getUser().getUserId());
        stmt.setInt(3, userScent.getScent().getScentId());
        closingProceduresUpdate(conn, stmt);
    }

    /**
     * Changes timestamp of a userScent
     *
     * @param userScent userScent that is altered
     * @param now a new timestamp 
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changeDate(UserScent userScent, Date now) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE UserScent SET choicedate =? WHERE user_id = ? AND scent_id = ?");
        stmt.setDate(1, now);
        stmt.setInt(2, userScent.getUser().getUserId());
        stmt.setInt(3, userScent.getScent().getScentId());
        closingProceduresUpdate(conn, stmt);
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
