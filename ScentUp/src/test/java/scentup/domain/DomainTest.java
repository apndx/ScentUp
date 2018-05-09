package scentup.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scentup.domain.Scent;
import scentup.domain.User;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 */
public class DomainTest {

    private final String randomuser;
    private final String randomname;
    private final User testuser;
    private final String randomscent;
    private final String randombrand;
    private final Integer testnumber;
    private final Scent testscent;
    private final UserScent testUserScent;

    public DomainTest() {
        this.randomuser = UUID.randomUUID().toString().substring(0, 6);
        this.randomname = UUID.randomUUID().toString().substring(0, 6);
        this.testuser = new User(null, randomname, randomuser);

        this.randomscent = UUID.randomUUID().toString().substring(0, 6);
        this.randombrand = UUID.randomUUID().toString().substring(0, 6);
        this.testnumber = 1;
        this.testscent = new Scent(null, randomscent, randombrand, testnumber,
                testnumber, testnumber);

        this.testUserScent = new UserScent(testuser, testscent, new Date(new java.util.Date().getDate()), 2, 1);

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

    @Test
    public void userScentEquals() {

        UserScent testUserScent2 = new UserScent(testuser, testscent, new Date(new java.util.Date().getDate()), 2, 1);

        assertEquals(true, testUserScent.equals(testUserScent2));
    }

    @Test
    public void userEquals() {
        User testUser2 = new User(null, randomname, randomuser);
        assertEquals(true, testuser.equals(testUser2));
    }

    @Test
    public void userNotEquals() {

        String randomUser2 = UUID.randomUUID().toString().substring(0, 6);
        User testUser2 = new User(null, randomname, randomUser2);
        assertEquals(false, testuser.equals(testUser2));
    }

    @Test
    public void scentToString() {

        assertEquals(null + ". " + randomscent + ", " + randombrand + ", day, winter, female",
                testscent.toString());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
