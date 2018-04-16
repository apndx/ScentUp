/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import scentup.domain.Scent;
import scentup.domain.User;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 */
public class UserScentDaoTest {

    public UserScentDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
//
//    @Test
//    public void isNewUserScentAdded() throws ClassNotFoundException, SQLException {
//
//        File file = new File("db", "ScentUp.db");
//        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
//        UserScentDao userScents = new UserScentDao(database);
//        UserDao users = new UserDao(database);
//        ScentDao scents = new ScentDao(database);
//
//        String randomuser = UUID.randomUUID().toString().substring(0, 6);
//        String randomname = UUID.randomUUID().toString().substring(0, 6);
//
//        User testuser = new User(null, randomname, randomuser);
//        users.saveOrUpdate(testuser);
//
//        String randomscent = UUID.randomUUID().toString().substring(0, 6);
//        String randombrand = UUID.randomUUID().toString().substring(0, 6);
//        Integer testnumber = 1;
//
//        Scent testscent = new Scent(null, randomscent, randombrand, testnumber,
//                testnumber, testnumber);
//        
//        UserScent testUserScent = new UserScent(testuser, testscent, Date.valueOf(LocalDate.MAX), 2, 1);
//        userScents.add(testUserScent);
//
//        assertEquals(testUserScent, userScents.)
//        
//    }

    @Test
    public void isExistingUserScentFound() throws ClassNotFoundException, SQLException {

        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        UserScentDao userScents = new UserScentDao(database);
        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrUpdate(testuser);
        testuser = users.findOne(randomuser);
        
        String randomscent = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomscent, randombrand, testnumber,
                testnumber, testnumber);

        scents.saveOrUpdate(testscent);
        testscent = scents.findOne(randomscent, randombrand);
        
        UserScent testUserScent= new UserScent(testuser, testscent, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);
        
        assertEquals(true, userScents.checkIfUserScentExists(users.findOne(randomuser).getUserId(), testscent.getScentId()));
        
        users.delete(randomuser);
        scents.delete(testscent.getScentId());
        userScents.delete(testuser.getUserId(), testscent.getScentId());
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}