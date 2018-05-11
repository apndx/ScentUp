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
 * Makes a new scent
 * 
 * @author apndx
 */
public class Scent {

    private Integer scentId;
    private final String scentName;
    private final String brand;
    private final Integer timeOfDay;
    private final Integer season;
    private final Integer gender;

    /**
     * Makes a new scent
     *
     * @param scentId - scentId is first null, it is created when scent is added
     * to the database
     * @param scentName name of the scent
     * @param brand brand of the scent
     * @param timeOfDay 1 day, 2 night
     * @param season 1 winter, 2 spring, 3 summer, 4 autumn
     * @param gender 1 female, 2 male, 3 unisex
     */
    public Scent(Integer scentId, String scentName, String brand, Integer timeOfDay,
            Integer season, Integer gender) {

        this.scentId = scentId;
        this.scentName = scentName;
        this.brand = brand;
        this.timeOfDay = timeOfDay;
        this.season = season;
        this.gender = gender;
    }

    /**
     * Changes season integer to more understandable string form
     *
     * @param season 1 winter, 2 spring, 3 summer, 4 autumn
     * @return returns string
     */
    public String scentSeasonString(Integer season) {
        if (season == 1) {
            return "winter";
        }
        if (season == 2) {
            return "spring";
        }
        if (season == 3) {
            return "summer";
        } else {
            return "autumn";
        }
    }

    /**
     * Changes time of day integer to more understandable string form
     *
     * @param time 1 day, 2 night
     * @return returns String
     */
    public String scentTimeOfDayString(Integer time) {

        if (time == 1) {
            return "day";
        } else {
            return "night";
        }
    }

    /**
     * Changes gender integer to more understandable string form
     *
     * @param gender 1 female, 2 male, 3 unisex
     * @return returns String
     */
    public String scentGenderString(Integer gender) {
        if (gender == 1) {
            return "female";
        }
        if (gender == 2) {
            return "male";
        } else {
            return "unisex";
        }
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

    public String getBrand() {
        return brand;
    }

    public Integer getTimeOfDay() {
        return timeOfDay;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getGender() {
        return gender;
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

    /**
     * Makes a scent, this method is used to make listings in dao
     *
     * @param rs result set from scent table of the database
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return Scent returns a scent
     */
    public static Scent rowToScent(ResultSet rs) throws SQLException {
        return new Scent(rs.getInt("scent_id"), rs.getString("scentname"),
                rs.getString("brand"), rs.getInt("timeofday"), rs.getInt("season"),
                rs.getInt("gender"));
    }

    @Override
    public String toString() {
        return this.scentId + ". " + this.scentName + ", " + this.brand + ", "
                + scentTimeOfDayString(this.timeOfDay) + ", " + scentSeasonString(this.season) + ", "
                + scentGenderString(this.gender);
    }
}
