package scentup.domain;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;

/**
 * ScentUpService is for the application logic
 *
 * @author apndx
 */
public class ScentUpService {

    private User loggedIn;
    private final UserDao userDao;
    private final ScentDao scentDao;
    private final UserScentDao userScentDao;

    public ScentUpService(UserDao userDao, ScentDao scentDao, UserScentDao userScentDao) {
        this.userDao = userDao;
        this.scentDao = scentDao;
        this.userScentDao = userScentDao;
    }

    /**
     * The user who has been logged in chooses a new scent, scent is added to
     * user's collection
     *
     * @param userId this is the id of the user
     * @param scentIdFor this is the id of the scent
     * @param pref tells user's preference (1 dislike, 2 neutral, 3 love)
     * @param act tells if this scent is active in user's collection (true when
     * created)
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean - did the creation succeed
     */
    public boolean createUserScent(Integer userId, Integer scentIdFor,
            Integer pref, Integer act) throws SQLException {

        //adding a new scent for the user logged in
        UserScent addForUser = new UserScent(loggedIn, scentDao.findOne(scentIdFor),
                new Date((int) new java.util.Date().getTime()), pref, act);

        if (!userScentDao.checkIfUserScentExists(userId, scentIdFor)) {
            try {
                userScentDao.add(addForUser);
            } catch (Exception ex) {
                System.out.println("Scent could not be added.");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * List of UserScents for this User
     *
     * @return list of UserScents for this User
     * @param active is the UserScent active for this user: 0 no, 1 yes
     */
    public List<UserScent> getUserScentListforUser(Integer active) {
        List<UserScent> activeList = new ArrayList<>();
        if (loggedIn == null) {
            return activeList;
        }
        try {
            activeList = userScentDao.findAllForUser(active, loggedIn.getUserId());
        } catch (Exception ex) {
            System.out.println("The list could not be found.");
            return new ArrayList<>();
        }
        return activeList;
    }

    /**
     * Who has logged in
     *
     * @return who has logged in
     */
    public User getLoggedIn() {
        return loggedIn;
    }

    /**
     * List all scent this user has
     *
     * @return list of scents this user has
     */
    public List<Scent> getScentsUserHas() {
        List<Scent> scentsHas = new ArrayList<>();
        if (loggedIn == null) {
            return scentsHas;
        }
        try {
            scentsHas = userScentDao.findAllScentsUserHas(loggedIn.getUserId());
        } catch (Exception ex) {
            System.out.println("The list could not be found.");
            return scentsHas;
        }
        return scentsHas;
    }

    /**
     * List all scent this user does not have
     *
     * @return list of scents user does not have
     */
    public List<Scent> getScentsUserHasNot() {
        List<Scent> hasNot = new ArrayList<>();

        if (loggedIn == null) {
            return hasNot;
        }
        try {

            hasNot = userScentDao.findAllScentsUserHasNot(loggedIn.getUserId());
        } catch (Exception ex) {
            System.out.println("The list could not be found.");
            return new ArrayList<>();

        }
        return hasNot;
    }

    /**
     * If the username is free, creates a new user
     *
     * @param username username for the user
     * @param name name for the user
     * @return boolean, returns true if the creation succeeded
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public boolean createUser(String username, String name) throws SQLException {
        if (userDao.isUsernameFree(username)) {
            // if the username is free
            User newuser = new User(null, name, username);
            userDao.saveOrNot(newuser);
            return true;
        } else {
            System.out.println("User could not be added.");
            return false;
        }
    }

    /**
     * Does the scent exist already in the database?
     *
     * @param scentName name of the scent
     * @param brandName brand of the scent
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean returns true if the scent already exists
     */
    public boolean doesScentExist(String scentName, String brandName) throws SQLException {
        if (!scentDao.checkIfScentExists(scentName, brandName)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Add a scent in the database
     *
     * @param scent - scent to be added
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void createScent(Scent scent) throws SQLException {
        scentDao.saveOrNot(scent);
    }

    /**
     * User login
     *
     * @param username username of the user who is logging in
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return boolean returns true if the login is successful
     */
    public boolean login(String username) throws SQLException {
        User current = userDao.findOne(username);

        if (current == null) {
            return false;
        } else {
            loggedIn = current;
            return true;
        }
    }

    /**
     * Change a preference of a userScent
     *
     * @param userScent userScent that is altered
     * @param preference the new preference: 1 dislike, 2 neutral, 3 love
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changePreference(UserScent userScent, Integer preference) throws SQLException {
        userScent.setPreference(preference);
        userScentDao.changePreference(userScent, preference);
    }

    /**
     * Change an activation status of a userScent
     *
     * @param userScent userScent that is altered
     * @param active the new status: 0 no, 1 yes
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changeActivationStatus(UserScent userScent, Integer active) throws SQLException {
        userScent.setActive(active);
        userScentDao.changeActiveStatus(userScent, active);
    }

    /**
     * Change a timestamp of a userScent
     *
     * @param userScent userScent that is altered
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     */
    public void changeDate(UserScent userScent) throws SQLException {
        Date now = new Date((int) new java.util.Date().getTime());
        userScent.setChoiceDate(now);
        userScentDao.changeDate(userScent, now);
    }

    /**
     * User logout
     */
    public void logout() {
        loggedIn = null;
    }
}
