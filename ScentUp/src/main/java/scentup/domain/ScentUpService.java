/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
        List<UserScent> aktiiviset = new ArrayList<>();

        if (loggedIn == null) {
            return aktiiviset;
        }
        try {

            aktiiviset = userScentDao.findAllForUser(1, loggedIn.getUserId());
        } catch (Exception ex) {
            return new ArrayList<>();

        }
        return aktiiviset;
    }

}
