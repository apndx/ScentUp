/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.domain;

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

}
