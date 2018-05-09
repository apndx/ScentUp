package scentup.dao;

/*
 * This class tests class UserDao
 */

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scentup.dao.Database;
import scentup.dao.UserDao;
import scentup.domain.User;

/**
 *
 * @author hdheli
 */
public class UserDaoTest  {
    
    
    
    public UserDaoTest() {
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
    public void isExistingUsernameIgnored() throws ClassNotFoundException, SQLException {
        
        File file = new File("ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        UserDao users = new UserDao(database);
        
        String randomuser =UUID.randomUUID().toString().substring(0, 6);
        String randomname =UUID.randomUUID().toString().substring(0, 6);
        
        User newuser = new User(null, randomname, randomuser);
        users.saveOrNot(newuser);
        
        assertEquals(false, users.isUsernameFree(randomuser));
        users.delete(randomuser);

    }
    
}
