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
import org.junit.Rule;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.domain.Scent;
import scentup.domain.User;

/**
 *
 * @author hdheli
 */
public class ScentDaoTest {
   
    public ScentDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp()  {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isExistingScentIgnored() throws ClassNotFoundException, SQLException {

        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        ScentDao scents = new ScentDao(database);
         
        String randomname = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomname, randombrand, testnumber,
                testnumber, testnumber);

        scents.saveOrUpdate(testscent);

        assertEquals(true, scents.checkIfScentExists(randomname, randombrand));
        scents.delete(randomname, randombrand);

    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
