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
    private final String randomScentName;
    private final String randomBrandName;
    private final String randomCategoryName;
    private final Scent testscent1;
    private final Scent testscent2;
    private final Scent testscent3;
    private final Scent testscent4;
    private final UserScent testUserScent;
    private final Category testCategory1;
    private final ScentCategory testScentCategory1;

    public DomainTest() {
        this.randomuser = UUID.randomUUID().toString().substring(0, 6);
        this.randomname = UUID.randomUUID().toString().substring(0, 6);
        this.testuser = new User(null, randomname, randomuser);

        this.randomScentName = UUID.randomUUID().toString().substring(0, 6);
        this.randomBrandName = UUID.randomUUID().toString().substring(0, 6);
        this.randomCategoryName = UUID.randomUUID().toString().substring(0, 6);
        this.testscent1 = new Scent(null, randomScentName, randomBrandName, 1,
                1, 1);
        this.testscent2 = new Scent(null, randomScentName, randomBrandName, 2,
                2, 2);
        this.testscent3 = new Scent(null, randomScentName, randomBrandName, 1,
                3, 3);
        this.testscent4 = new Scent(null, randomScentName, randomBrandName, 2,
                4, 1);

        this.testUserScent = new UserScent(testuser, testscent1, new Date(new java.util.Date().getDate()), 2, 1);

        this.testCategory1 = new Category(null, randomCategoryName);
        this.testScentCategory1 = new ScentCategory(testscent1, testCategory1);
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
    public void scentCategoryEquals() {
        ScentCategory testScentCategory2 = new ScentCategory(testscent1, testCategory1);
        assertEquals(true, testScentCategory1.equals(testScentCategory2));
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

        assertEquals(null + ". " + randomScentName + ", " + randomBrandName + ", day, winter, female",
                testscent1.toString());
        assertEquals(null + ". " + randomScentName + ", " + randomBrandName + ", night, spring, male",
                testscent2.toString());
        assertEquals(null + ". " + randomScentName + ", " + randomBrandName + ", day, summer, unisex",
                testscent3.toString());
        assertEquals(null + ". " + randomScentName + ", " + randomBrandName + ", night, autumn, female",
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
