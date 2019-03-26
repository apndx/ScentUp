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
public class ScentCategory {

    private final Scent scent;
    private final Category category;
    
     /**
     * Makes a new scentCategory  - this means a certain scent belongs to a certain category
     *
     * @param scent scent that gets a category
     * @param category a category that is attached to a scent
     */
    public ScentCategory(Scent scent, Category category) {
        this.scent = scent;
        this.category = category;
    }

    public Scent getScent() {
        return scent;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.scent);
        hash = 89 * hash + Objects.hashCode(this.category);
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
        final ScentCategory other = (ScentCategory) obj;
        if (!Objects.equals(this.scent, other.scent)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }
    
      /**
     * Makes a ScentCategory, this method is used to make listings in Dao
     *
     * @param rs result set from ScentCategory table of the database
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return ScentCategory returns a ScentCategory
     */
    public static ScentCategory rowToUserScent(ResultSet rs) throws SQLException {
        return new ScentCategory(Scent.rowToScent(rs), Category.rowToCategory(rs));
    }

    @Override
    public String toString() {
        return this.scent.toString() +", " + this.category.toString();
              
    }
            
    
}
