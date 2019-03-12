/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author apndx
 */
public class Category {
    
    private Integer categoryId;
    private final String categoryName;
    
     /**
     * Makes a new category
     *
     * @param categoryId - categoryId is first null, it is created when a category is added
     * to the database
     * @param categoryName name of the category
     */
    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.categoryId);
        hash = 29 * hash + Objects.hashCode(this.categoryName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        if (!Objects.equals(this.categoryId, other.categoryId)) {
            return false;
        }
        return true;
    }
    
     /**
     * Makes a category, this method is used to make listings in dao
     *
     * @param rs result set from scent table of the database
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return Category returns a category
     */
    public static Category rowToCategory(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("category_id"), rs.getString("categoryname"));
    }
    
    @Override
    public String toString() {
        return this.categoryName;
    }
}
