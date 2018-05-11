package scentup.dao;

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
import scentup.domain.Scent;

/**
 *
 * @author apndx
 */
public class ScentDaoTest {

    private File file;
    private Database database;
    private ScentDao scents;

    public ScentDaoTest() throws ClassNotFoundException {
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
        this.scents = new ScentDao(database);
    }

    @After
    public void tearDown() {
          file.delete();
    }

    @Test
    public void isExistingScentIgnored() throws ClassNotFoundException, SQLException {

        String randomname = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomname, randombrand, testnumber,
                testnumber, testnumber);

        scents.saveOrNot(testscent);

        assertEquals(true, scents.checkIfScentExists(randomname, randombrand));
        scents.delete(randomname, randombrand);

    }

    @Test
    public void isScentFoundById() throws SQLException {

        String randomname = UUID.randomUUID().toString().substring(0, 6);
        String randombrand = UUID.randomUUID().toString().substring(0, 6);
        Integer testnumber = 1;

        Scent testscent = new Scent(null, randomname, randombrand, testnumber,
                testnumber, testnumber);

        testscent = scents.saveOrNot(testscent);

        Integer testScentId = testscent.getScentId();

        assertEquals(randomname, scents.findOne(testScentId).getName());

        scents.delete(randomname, randombrand);

    }

}
