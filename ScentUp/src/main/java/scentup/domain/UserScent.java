/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author hdheli
 */
public class UserScent {

    private User user;
    private Scent scent;
    private Date choiceDate;     //when was this choice last activated
    private Integer preference; // 1 dislike, 2 neutral, 3 love
    private Integer active;     // is this choice active for this user 0 no, 1 yes

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

    public void setUser(User user) {
        this.user = user;
    }

    public Scent getScent() {
        return scent;
    }

    public void setScent(Scent scent) {
        this.scent = scent;
    }

    public Date getChoiceDate() {
        return choiceDate;
    }

    public void setChoiceDate(Date choiceDate) {
        this.choiceDate = choiceDate;
    }

    public Integer getPreference() {
        return preference;
    }

    public void setPreference(Integer preference) {
        this.preference = preference;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
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

    public static UserScent rowToUserScent(ResultSet rs) throws SQLException {
        return new UserScent(User.rowToUser(rs), Scent.rowToScent(rs), rs.getDate("choicedate"), rs.getInt("preference"), rs.getInt("active"));

    }

}
