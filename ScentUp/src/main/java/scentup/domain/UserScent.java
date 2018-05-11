package scentup.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Makes a new UserScent - this means a certain user has a certain scent
 * 
 * @author apndx
 */
public class UserScent {

    private final User user;
    private final Scent scent;
    private final Date choiceDate;
    private Integer preference;
    private final Integer active;

    /**
     * Makes a new UserScent - this means a certain user has a certain scent
     *
     * @param user user who is logged in
     * @param scent scent that was chosen
     * @param choiceDate timestamp of when this choice wast last activated
     * @param preference 1 dislike, 2 neutral, 3 love
     * @param active is this choice active for this user? 0 no, 1 yes. Active is
     * yes when a UserScent is created
     */
    public UserScent(User user, Scent scent, Date choiceDate, Integer preference, Integer active) {
        this.user = user;
        this.scent = scent;
        this.choiceDate = choiceDate;
        this.preference = preference;
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public Scent getScent() {
        return scent;
    }

    public Date getChoiceDate() {
        return choiceDate;
    }

    public Integer getPreference() {
        return preference;
    }

    public Integer getActive() {
        return active;
    }

    public void setPreference(Integer preference) {
        this.preference = preference;
    }

    /**
     * Changes preference integer to more understandable string form
     *
     * @param preference 1 dislike, 2 neutral, 3 love
     * @return returns String
     */
    public String userScentPreferenceString(Integer preference) {
        if (preference == 1) {
            return "dislike";
        }
        if (preference == 2) {
            return "neutral";
        } else {
            return "love";
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.user);
        hash = 71 * hash + Objects.hashCode(this.scent);
        hash = 71 * hash + Objects.hashCode(this.choiceDate);
        hash = 71 * hash + Objects.hashCode(this.preference);
        hash = 71 * hash + Objects.hashCode(this.active);
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
        final UserScent other = (UserScent) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.scent, other.scent)) {
            return false;
        }
        if (!Objects.equals(this.choiceDate, other.choiceDate)) {
            return false;
        }
        if (!Objects.equals(this.preference, other.preference)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return true;
    }

    /**
     * Makes a UserScent, this method is used to make listings in Dao
     *
     * @param rs result set from user table of the database
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return UserScent returns a UserScent
     */
    public static UserScent rowToUserScent(ResultSet rs) throws SQLException {
        return new UserScent(User.rowToUser(rs), Scent.rowToScent(rs), rs.getDate("choicedate"),
                rs.getInt("preference"), rs.getInt("active"));

    }

    @Override
    public String toString() {
        return this.scent.getName() + ", " + this.scent.getBrand() + ", "
                + this.scent.scentTimeOfDayString(this.scent.getTimeOfDay()) + ", "
                + this.scent.scentSeasonString(this.scent.getSeason()) + ", "
                + this.scent.scentGenderString(this.scent.getGender());
              
    }
}
