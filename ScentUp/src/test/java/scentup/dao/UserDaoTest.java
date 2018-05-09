package scentup.dao;

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scentup.domain.User;

/**
 * This class tests class UserDao
 *
 * @author apndx
 */
public class UserDaoTest {

    private File file;
    private Database database;
    private UserDao users;
    
    public UserDaoTest() {
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
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void isExistingUsernameIgnored() throws ClassNotFoundException, SQLException {

        String randomuser = UUID.randomUUID().toString().substring(0, 6);
        String randomname = UUID.randomUUID().toString().substring(0, 6);

        User newuser = new User(null, randomname, randomuser);
        users.saveOrNot(newuser);

        assertEquals(false, users.isUsernameFree(randomuser));
        users.delete(randomuser);

    }

}
