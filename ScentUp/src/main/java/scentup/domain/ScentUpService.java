/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

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

    public ScentUpService(UserDao userDao, ScentDao scentDao, UserScentDao userScentDao) {
        this.userDao = userDao;
        this.scentDao = scentDao;
        this.userScentDao = userScentDao;
    }

    /**
     * the user who has been logged in chooses a new scent
     */
    public boolean createUserScent(UserScent userScent) {

        try {
            userScentDao.add(userScent);
        } catch (Exception ex) {
            return false;
        }
        return true;
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

}
