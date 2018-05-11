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
 * This test class is for integrated test for ScentUpService and Daos
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

    private final String randomname;
    private final String randomuser;
    private User testuser;

    private final String randomscent;
    private final String randombrand;
    private Scent testscent1;

    private final String randomscent2;
    private final String randombrand2;
    private Scent testscent2;

    public ServiceTest() throws ClassNotFoundException, SQLException {

        this.file = new File("TestScentUp.db");
        this.database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();
        this.users = new UserDao(database);
        this.scents = new ScentDao(database);
        this.userScents = new UserScentDao(database);
        this.service = new ScentUpService(users, scents, userScents);

        this.randomuser = UUID.randomUUID().toString().substring(0, 6);
        this.randomname = UUID.randomUUID().toString().substring(0, 6);
        this.testuser = new User(null, randomname, randomuser);
        service.login(randomuser);
        service.createUser(randomuser, randomname);
        testuser = users.findOne(randomuser);

        this.randomscent = UUID.randomUUID().toString().substring(0, 6);
        this.randombrand = UUID.randomUUID().toString().substring(0, 6);
        this.testscent1 = new Scent(null, randomscent, randombrand, 1, 1, 1);
        service.createScent(testscent1);
        testscent1 = scents.findOne(randomscent, randombrand);

        this.randomscent2 = UUID.randomUUID().toString().substring(0, 6);
        this.randombrand2 = UUID.randomUUID().toString().substring(0, 6);
        this.testscent2 = new Scent(null, randomscent2, randombrand2, 2, 2, 2);
        service.createScent(testscent2);
        testscent2 = scents.findOne(randomscent, randombrand);
        service.logout();
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
        file.delete();
    }

    @Test
    public void userAddedCorrectly() throws SQLException {
        
        assertEquals(false, service.createUser(randomuser, randomname));

    }

    @Test
    public void isUserScentAddedCorrectly() throws SQLException {

        service.login(randomuser);
        
        service.createUserScent(testuser.getUserId(), testscent1.getScentId(), 2, 1);
        assertEquals(false, service.createUserScent(testuser.getUserId(), testscent1.getScentId(), 2, 1));

        service.logout();
        
    }

    @Test
    public void isGetScentsUserHasListCorrect() throws SQLException {

        UserScent testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);

        service.login(randomuser);

        List<Scent> scentsHas = service.getScentsUserHas();

        assertEquals(true, scentsHas.get(0).equals(testscent1));
        assertEquals(true, scentsHas.get(scentsHas.size() - 1).equals(testscent1));

        users.delete(randomuser);
        scents.delete(testscent1.getScentId());
        scents.delete(testscent2.getScentId());
        service.logout();
        userScents.delete(testuser.getUserId(), testscent1.getScentId());

    }

    @Test
    public void isGetScentsUserHasNotListCorrect() throws SQLException {

        int listSize = scents.findAll().size();
        service.login(randomuser);

        assertEquals(listSize, service.getScentsUserHasNot().size());
        service.logout();
        users.delete(randomuser);

    }

    @Test
    public void doesServiceFoundIfScentExists() throws SQLException {

 
        service.login(randomuser);

        assertEquals(true, service.doesScentExist(randomscent, randombrand));
        assertEquals(false, service.doesScentExist("somename", "somebrand"));

        service.logout();
        users.delete(randomuser);
        scents.delete(randomscent, randombrand);

    }

    @Test
    public void isUserScenttListCorrect() throws SQLException {

        UserScent testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);
        testUserScent= userScents.findOne(testuser.getUserId(), testscent1.getScentId());
        
        UserScent testUserScent2 = new UserScent(testuser, testscent2, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent2);
        testUserScent2= userScents.findOne(testuser.getUserId(), testscent2.getScentId());

        service.login(randomuser);
        assertEquals(2, service.getUserScentListforUser().size());
        service.logout();
        userScents.delete(testuser.getUserId(), testscent1.getScentId());
        userScents.delete(testuser.getUserId(), testscent2.getScentId()); 

    }

    @Test
    public void doesUserScentPreferenceChangeCorrectly() throws SQLException {

        UserScent testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent);
        testUserScent= userScents.findOne(testuser.getUserId(), testscent1.getScentId());
        
        UserScent testUserScent2 = new UserScent(testuser, testscent2, new Date(new java.util.Date().getDate()), 2, 1);
        userScents.add(testUserScent2);
        testUserScent2= userScents.findOne(testuser.getUserId(), testscent2.getScentId());
        
        service.login(randomuser);
        service.changePreference(testUserScent, 1);
        service.changePreference(testUserScent2, 3);

        assertEquals("dislike", testUserScent.userScentPreferenceString(testUserScent.getPreference()));
        assertEquals("love", testUserScent2.userScentPreferenceString(testUserScent2.getPreference()));
        
        service.logout();
        userScents.delete(testuser.getUserId(), testscent1.getScentId());
        userScents.delete(testuser.getUserId(), testscent2.getScentId());

    }

}
