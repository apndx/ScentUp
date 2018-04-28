/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;

/**
 *
 * @author hdheli
 */
public class ScentUpService {

    /**
     * application logic is here
     */
    private User loggedIn;
    private final UserDao userDao;
    private final ScentDao scentDao;
    private final UserScentDao userScentDao;

    /**
     * Constructor for ScentUpService ScentUpService is for the application
     * logic
     *
     * @param userDao
     * @param scentDao
     * @param userScentDao
     */
    public ScentUpService(UserDao userDao, ScentDao scentDao, UserScentDao userScentDao) {
        this.userDao = userDao;
        this.scentDao = scentDao;
        this.userScentDao = userScentDao;
    }

    /**
     * The user who has been logged in chooses a new scent, scent is added to
     * user's collection
     *
     * @param userId this is the id of the user
     * @param scentIdFor this is the id of the scent
     * @param dateNow date when the scent was chosen by the user, added
     * automatically
     * @param pref tells user's preference (1 dislike, 2 neutral, 3 love)
     * @param act tells if this scent is active in user's collection (true when
     * created)
     * @throws SQLException
     * @return boolean - did the creation succeed
     */
    public boolean createUserScent(Integer userId, Integer scentIdFor, Date dateNow,
            Integer pref, Integer act) throws SQLException {

        //adding a new scent for the user logged in
        UserScent addForUser = new UserScent(loggedIn, scentDao.findOne(scentIdFor), dateNow, pref, act);

        if (!userScentDao.checkIfUserScentExists(userId, scentIdFor)) {
            try {
                userScentDao.add(addForUser);
            } catch (Exception ex) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Who has logged in
     *
     * @return who has logged in
     */
    public User getLoggedIn() {
        return loggedIn;
    }

    /**
     * List of active scents for this user
     *
     * @return list of active scents for this user
     */
    public List<UserScent> getActive() {
        List<UserScent> active = new ArrayList<>();
        if (loggedIn == null) {
            return active;
        }
        try {
            active = userScentDao.findAllForUser(1, loggedIn.getUserId());
        } catch (Exception ex) {
            return new ArrayList<>();
        }
        return active;
    }

    /**
     * List all scent this user has
     *
     * @return list of scents this user has
     */
    public List<Scent> getScentsUserHas() {
        List<Scent> scentsHas = new ArrayList<>();
        if (loggedIn == null) {
            return scentsHas;
        }
        try {
            scentsHas = userScentDao.findAllScentsUserHas(loggedIn.getUserId());
        } catch (Exception ex) {
            return scentsHas;
        }
        return scentsHas;
    }

    /**
     * List all scent this user does not have
     *
     * @return list of scents user does not have
     */
    public List<Scent> getScentsUserHasNot() {
        List<Scent> hasNot = new ArrayList<>();

        if (loggedIn == null) {
            return hasNot;
        }
        try {

            hasNot = userScentDao.findAllScentsUserHasNot(loggedIn.getUserId());
        } catch (Exception ex) {
            return new ArrayList<>();

        }
        return hasNot;
    }

    /**
     * If the username is free, creates a new user
     *
     * @param username  username for the user
     * @param name  name for the user
     * @return boolean, returns true if the creation succeeded
     * @throws SQLException
     */
    public boolean createUser(String username, String name) throws SQLException {
        if (userDao.isUsernameFree(username)) {
            // if the username is free
            User newuser = new User(null, name, username);
            userDao.saveOrUpdate(newuser);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Does the scent exist already in the database?
     *
     * @param scentName  name of the scent
     * @param brandName  brand of the scent
     * @throws SQLException
     * @return boolean  returns true if the scent already exists
     */
    public boolean doesScentExist(String scentName, String brandName) throws SQLException {
        if (!scentDao.checkIfScentExists(scentName, brandName)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Add a scent in the database
     *
     * @param scent - scent to be added
     * @throws SQLException
     *
     */
    public void createScent(Scent scent) throws SQLException {
        scentDao.saveOrUpdate(scent);
    }

    /**
     * user login
     *
     * @param username  username of the user who is logging in
     * @throws SQLException
     * @return boolean  returns true if the login is successful
     */
    public boolean login(String username) throws SQLException {
        User current = userDao.findOne(username);

        if (current == null) {
            return false;
        } else {
            loggedIn = current;
            return true;
        }
    }

    /**
     * User logout
     */
    public void logout() {
        loggedIn = null;
    }
}
