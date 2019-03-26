package scentup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.domain.Category;
import scentup.domain.Scent;

/**
 *
 * @author apndx
 */
public class CategoryDao {

    private final Database database;

    public CategoryDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a category from the database. Uses the method forFindOne for SQL
     *
     * @param categoryId id of the category that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return returns the category if it is found, otherwise returns null
     */
    public Category findOne(Integer categoryId) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category WHERE category_id = ?");
        stmt.setInt(1, categoryId);
        return forFindOne(conn, stmt);
    }

    /**
     * Finds a category from the database. Uses the method forFindOne for SQL
     *
     * @param name of the category that needs to be found
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return returns the category if it is found, otherwise returns null
     */
    public Category findOne(String name) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Scent WHERE categoryname = ?");
        stmt.setString(1, name);
        return forFindOne(conn, stmt);
    }

    private Category forFindOne(Connection conn, PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            closingProceduresRs(conn, rs, stmt);
            return null;
        }
        Category category = new Category(rs.getInt("category_id"), rs.getString("categoryname"));
        closingProceduresRs(conn, rs, stmt);
        return category;
    }

    /**
     * Lists all categories found in the database
     *
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return List returns a list of all categories in the database
     */
    public List<Category> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category");
        ResultSet rs = stmt.executeQuery();
        List<Category> listOfAll = new ArrayList<>();
        while (rs.next()) {
            Category cat = new Category(rs.getInt("category_id"), rs.getString("categoryname"));
            listOfAll.add(cat);
        }
        closingProceduresRs(conn, rs, stmt);
        return listOfAll;
    }

    /**
     * Saves a category if it does not exist already
     *
     * @param object category that needs to be saved
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return object. If the cateogryId of the category is null, private method
     * save is used and a saved category is returned. If id is found, null is
     * returned.
     */
    public Category saveOrNot(Category object) throws SQLException {
        if (object.getCategoryId() == null) {
            return save(object);
        } else {
            return null;
        }
    }

    private Category save(Category category) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Category (categoryname) VALUES (?)");
        stmt.setString(1, category.getCategoryName());
        stmt.executeUpdate();
        stmt.close();
        stmt = conn.prepareStatement("SELECT * FROM Category WHERE categoryname = ?");
        stmt.setString(1, category.getCategoryName());
        ResultSet rs = stmt.executeQuery();
        rs.next(); // only one row
        Category cat = new Category(rs.getInt("category_id"), rs.getString("categoryname"));
        closingProceduresRs(conn, rs, stmt);
        return cat;
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
