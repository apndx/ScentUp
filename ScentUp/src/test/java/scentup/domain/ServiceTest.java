package scentup.domain;

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
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;

/**
 *
 * @author apndx
 */
public class ServiceTest {

    private final File file;
    private final Database database;
    private final UserDao users;
    private final ScentDao scents;
    private final UserScentDao userScents;
    private final ScentUpService service;

    public ServiceTest() throws ClassNotFoundException {

        this.file = new File("ScentUp.db");
        this.database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        this.users = new UserDao(database);
        this.scents = new ScentDao(database);
        this.userScents = new UserScentDao(database);
        this.service = new ScentUpService(users, scents, userScents);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void userAddedCorrectly() throws SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        service.createUser(randomuser, randomname);

        assertEquals(false, service.createUser(randomuser, randomname));
        users.delete(randomuser);

    }

    @Test
    public void isUserScentAddedCorrectly() throws SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrNot(testuser);
        testuser = users.findOne(randomuser);

        String randomscent = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomscent, randombrand, testnumber,
                testnumber, testnumber);

        service.createScent(testscent);
        testscent = scents.findOne(randomscent, randombrand);

        service.createUserScent(testuser.getUserId(), testscent.getScentId(), 2, 1);

        assertEquals(false, service.createUserScent(testuser.getUserId(), testscent.getScentId(), 2, 1));

        users.delete(randomuser);
        scents.delete(testscent.getScentId());

    }

    @Test
    public void isGetScentsUserHasListCorrect() throws SQLException {

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

        service.login(randomuser);

        List<Scent> scentsHas = service.getScentsUserHas();

        assertEquals(true, scentsHas.get(0).equals(testscent));
        assertEquals(true, scentsHas.get(scentsHas.size() - 1).equals(testscent));

        users.delete(randomuser);
        scents.delete(testscent.getScentId());
        scents.delete(testscent2.getScentId());
        service.logout();

    }

    @Test
    public void isGetScentsUserHasNotListCorrect() throws SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrNot(testuser);

        int listSize = scents.findAll().size();
        service.login(randomuser);

        assertEquals(listSize, service.getScentsUserHasNot().size());
        service.logout();
        users.delete(randomuser);

    }

    @Test
    public void doesServiceFoundIfScentExists() throws SQLException {

        String randomscent = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomscent, randombrand, testnumber,
                testnumber, testnumber);

        scents.saveOrNot(testscent);

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User testuser = new User(null, randomname, randomuser);
        users.saveOrNot(testuser);
        testuser = users.findOne(randomuser);

        service.login(randomuser);

        assertEquals(true, service.doesScentExist(randomscent, randombrand));
        assertEquals(false, service.doesScentExist("somename", "somebrand"));

        service.logout();
        users.delete(randomuser);
        scents.delete(randomscent, randombrand);

    }
    
     @Test
    public void isUserScenttListCorrect() throws SQLException {

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
        UserScent testUserScent2 =  new UserScent(testuser, testscent2, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent2);
        
        service.login(randomuser);
        assertEquals(2, service.getUserScentListforUser().size());
        service.logout();
        users.delete(randomuser);
        scents.delete(testscent1.getScentId());
        scents.delete(testscent2.getScentId());
        
    }
    
}