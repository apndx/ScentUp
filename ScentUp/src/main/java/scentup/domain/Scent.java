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
 * @author hdheli
 */
public class Scent {

    private Integer scentId;
    private String scentName;
    private String brand;
    private Integer timeOfDay; // 1 day, 2 night
    private Integer season;  // 1 winter, 2 spring, 3 summer, 4 autumn
    private Integer gender; // 1 female, 1 male, 3 uni

    public Scent(Integer scentId, String scentName, String brand, Integer timeOfDay,
            Integer season, Integer gender) {

        this.scentId = scentId;
        this.scentName = scentName;
        this.brand = brand;
        this.timeOfDay = timeOfDay;
        this.season = season;
        this.gender = gender;
    }

    public Integer getScentId() {
        return scentId;
    }

    public void setScentId(Integer scentId) {
        this.scentId = scentId;
    }

    public String getName() {
        return scentName;
    }

    public void setName(String name) {
        this.scentName = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(Integer timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.scentName);
        hash = 79 * hash + Objects.hashCode(this.brand);
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
        final Scent other = (Scent) obj;
        if (!Objects.equals(this.scentName, other.scentName)) {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        return true;
    }

    public static Scent rowToScent(ResultSet rs) throws SQLException {
        return new Scent(rs.getInt("scent_id"), rs.getString("name"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"),
                rs.getInt("gender"));
    }

    @Override
    public String toString() {
        return this.scentId + ". " + this.scentName + ", " + this.brand;
    }
}
