/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

import java.util.Objects;

/**
 *
 * @author hdheli
 */
public class User {
    
    private Integer userId;
    private String name;
    private String username;

    public User(Integer userId, String name, String username) {
        this.userId=userId;
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        return username.equals(other.username);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.userId);
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
    

}
