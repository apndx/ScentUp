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
import java.util.stream.Collectors;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import static scentup.ui.ScentUpTextUi.printMenu;

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
     * Constructor for ScentUpService
     * ScentUpService is for the application logic
     * 
     * @param userDao 
     * @param scentDao
     * @param userScentDao
     *
     * @return todennäköisyys kalibroituna
     */
    public ScentUpService(UserDao userDao, ScentDao scentDao, UserScentDao userScentDao) {
        this.userDao = userDao;
        this.scentDao = scentDao;
        this.userScentDao = userScentDao;
    }

    /**
     * the user who has been logged in chooses a new scent
     *
     * @param userId this is the id of the user
     * @param scentIdFor this is the id of the scent
     * @param dateNow date when the scent was chosen by the user, added automatically
     * @param pref tells user's preference (1 dislike, 2 neutral, 3 love)
     * @param act tells if this scent is active in user's collection (true when created)
     * 
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

    public User getLoggedIn() {
        return loggedIn;
    }

    /**
     * list of active scents for this user
     *
     * @return
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

    // list all scent this user has
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

    // list all scent this user has not
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

    public boolean doesScentExist(String scentName, String brandName) throws SQLException {
        if (!scentDao.checkIfScentExists(scentName, brandName)) {
            return false;
        } else {
            return true;
        }
    }

    public void createScent(Scent scent) throws SQLException {

        scentDao.saveOrUpdate(scent);

    }

    public boolean login(String username) throws SQLException {
        User current = userDao.findOne(username);

        if (current == null) {
            return false;
        } else {
            // login and open userpage
            // todo

            loggedIn = current;

            return true;
        }

    }

    public void logout() {
        loggedIn = null;
    }

}
