/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        
        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        UserDao users = new UserDao(database);
        
        String randomuser =UUID.randomUUID().toString().substring(0, 6);
        String randomname =UUID.randomUUID().toString().substring(0, 6);
        
        User newuser = new User(null, randomname, randomuser);
        users.saveOrUpdate(newuser);
        
        assertEquals(false, users.isUsernameFree(randomuser));
        users.delete(randomuser);

    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
