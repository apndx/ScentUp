package scentup.dao;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
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
 * @author apndx
 */
public class UserScentDaoTest {

    private File file;
    private Database database;
    private UserDao users;
    private ScentDao scents;
    private UserScentDao userScents;

    public UserScentDaoTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        this.file = new File("TestScentUp.db");
        this.database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();
        this.users = new UserDao(database);
        this.scents = new ScentDao(database);
        this.userScents = new UserScentDao(database);
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void isExistingUserScentFound() throws ClassNotFoundException, SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrNot(testuser);
        testuser = users.findOne(randomuser);

        String randomscent1 = UUID.randomUUID().toString().substring(0, 6);
        String randomscent2 = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);

        Scent testscent1 = new Scent(null, randomscent1, randombrand, 1, 1, 1);
        Scent testscent2 = new Scent(null, randomscent2, randombrand, 2, 2, 2);

        scents.saveOrNot(testscent1);
        scents.saveOrNot(testscent2);
        testscent1 = scents.findOne(randomscent1, randombrand);
        testscent2 = scents.findOne(randomscent2, randombrand);

        UserScent testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);

        assertEquals(true, userScents.checkIfUserScentExists(users.findOne(randomuser).getUserId(), testscent1.getScentId()));
        assertEquals(false, userScents.checkIfUserScentExists(users.findOne(randomuser).getUserId(), testscent2.getScentId()));

        users.delete(randomuser);
        scents.delete(testscent1.getScentId());
        scents.delete(testscent2.getScentId());

    }

    @Test
    public void isHasScentsListingCorrect() throws ClassNotFoundException, SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrNot(testuser);
        testuser = users.findOne(randomuser);

        String randomscent = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        String randomscent2 = UUID.randomUUID().toString().substring(0, 6);
        String randombrand2 = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber2 = 2;

        Scent testscent = new Scent(null, randomscent, randombrand, testnumber,
                testnumber, testnumber);

        Scent testscent2 = new Scent(null, randomscent2, randombrand2, testnumber2,
                testnumber2, testnumber2);

        scents.saveOrNot(testscent);
        scents.saveOrNot(testscent2);
        testscent = scents.findOne(randomscent, randombrand);
        testscent2 = scents.findOne(randomscent2, randombrand2);

        UserScent testUserScent = new UserScent(testuser, testscent, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);

        List<Scent> scentsHas = userScents.findAllScentsUserHas(testuser.getUserId());
        assertEquals(true, scentsHas.get(0).equals(testscent));
        assertEquals(true, scentsHas.get(scentsHas.size() - 1).equals(testscent));

        users.delete(randomuser);
        scents.delete(testscent.getScentId());
        scents.delete(testscent2.getScentId());

    }
}
