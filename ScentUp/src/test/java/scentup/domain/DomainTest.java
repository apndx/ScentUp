package scentup.domain;

import java.sql.Date;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This test class is for testing the classes in the domain package
 *
 * @author apndx
 */
public class DomainTest {

    private final String randomuser;
    private final String randomname;
    private final User testuser;
    private final String randomscent;
    private final String randombrand;
    private final Scent testscent1;
    private final Scent testscent2;
    private final Scent testscent3;
    private final Scent testscent4;
    private final UserScent testUserScent;

    public DomainTest() {
        this.randomuser = UUID.randomUUID().toString().substring(0, 6);
        this.randomname = UUID.randomUUID().toString().substring(0, 6);
        this.testuser = new User(null, randomname, randomuser);

        this.randomscent = UUID.randomUUID().toString().substring(0, 6);
        this.randombrand = UUID.randomUUID().toString().substring(0, 6);
        this.testscent1 = new Scent(null, randomscent, randombrand, 1,
                1, 1);
        this.testscent2 = new Scent(null, randomscent, randombrand, 2,
                2, 2);
        this.testscent3 = new Scent(null, randomscent, randombrand, 1,
                3, 3);
        this.testscent4 = new Scent(null, randomscent, randombrand, 2,
                4, 1);

        this.testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);

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
        UserScent testUserScent2 = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);
        UserScent testUserScent3 = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 3, 1);
        assertEquals(true, testUserScent.equals(testUserScent2));
        assertEquals(false, testUserScent.equals(testUserScent3));
        assertEquals(testUserScent2.hashCode(), testUserScent.hashCode());
    }

    @Test
    public void userEquals() {
        User testUser2 = new User(null, randomname, randomuser);
        assertEquals(true, testuser.equals(testUser2));
        assertEquals(testUser2.hashCode(), testuser.hashCode());
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
                testscent1.toString());
        assertEquals(null + ". " + randomscent + ", " + randombrand + ", night, spring, male",
                testscent2.toString());
        assertEquals(null + ". " + randomscent + ", " + randombrand + ", day, summer, unisex",
                testscent3.toString());
        assertEquals(null + ". " + randomscent + ", " + randombrand + ", night, autumn, female",
                testscent4.toString());

    }
    
    @Test
    public void userScentToString() {
        
        assertEquals(this.testscent1.getName() + ", " + this.testscent1.getBrand() + ", "
                + this.testscent1.scentTimeOfDayString(this.testscent1.getTimeOfDay()) + ", "
                + this.testscent1.scentSeasonString(this.testscent1.getSeason()) + ", "
                + this.testscent1.scentGenderString(this.testscent1.getGender()), testUserScent.toString());

    }
}
