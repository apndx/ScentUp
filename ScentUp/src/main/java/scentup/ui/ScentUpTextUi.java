/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import scentup.domain.Scent;
import scentup.domain.ScentUpService;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 *
 * This is a text user interface - Main menu options:
 *
 * "What to do next?" 
 * "1. Create a new User" 
 * "2. Add a new Scent" 
 * "3. ScentIn" 
 * "4. ScentOut"
 *
 */
public class ScentUpTextUi {

    private Scanner reader;
    private ScentUpService scentUpService;

    public ScentUpTextUi(Scanner reader, ScentUpService scentUpService) {

        this.reader = reader;
        this.scentUpService = scentUpService;
    }

    public void start() throws ClassNotFoundException, SQLException {
        //File file = new File("db", "ScentUp.db");
        //Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());

        System.out.println("Welcome to ScentUp! ");
        System.out.println(" ");
        printMenu();

        // here should be made sure that input is a number of the menu, otherwise declined
        while (true) {
            String chosen = reader.nextLine();
            if (chosen.matches("1")) {

                menu1(reader, scentUpService);

            } else if (chosen.matches("2")) {
                // add a new scent

                menu2(reader, scentUpService);

            } else if (chosen.matches("3")) {
                // login
                // todo
                menu3(reader, scentUpService);

            } else if (chosen.matches("4")) {
                menu4();
                break;
            } else {

                System.out.println("Please type a number mentioned in the menu.");
            }

        }

    }

    public static void menu1(Scanner reader, ScentUpService service) throws SQLException {
        // creates a new user to the database

        System.out.println("What is your name?");
        String name = reader.nextLine();
        if (name.matches(".{1,200}")) {
            System.out.println("Please type an username (min 5 characters)");
            String username = reader.nextLine();

            if (username.matches(".{5,200}")) { // is it within the size limits

                if (service.createUser(username, name)) {
                    System.out.println("Your username has now been registered.");
                    printMenu();
                } else {
                    System.out.println("This username is already in use, please choose another. ");
                    printMenu();
                }

            } else {
                System.out.println("Username needs to be min 5 characters (and not too long either).");
                printMenu();
            }
        } else {
            System.out.println("Name cannot be empty (and not too long either).");
            printMenu();
        }
    }

    public static void menu2(Scanner reader, ScentUpService service) throws SQLException {
        // add a new scent. combination of a name and a brand must be unique.
        // the first version continues one step at a time, and aborts if
        // inputs do not meet the criteria

        System.out.println("Name of the Scent?");
        String scentName = reader.nextLine();

        if (scentName.matches(".{1,200}")) { // is it within the size limits
            System.out.println("Brand of the Scent? If unknown, type unknown and enter.");
            String brand = reader.nextLine();

            if (brand.matches(".{1,200}")) {
                // check from the database by name and brand, if the scent already exist 
                // if exists, we don't want a double
                if (!service.doesScentExist(scentName, brand)) {
                    // lets ask the remaining details for the scent
                    System.out.println("Is it a day or a night scent?");
                    System.out.println("1. Day");
                    System.out.println("2. Night");
                    String timeOfDay = reader.nextLine();
                    if (timeOfDay.matches("1|2")) {
                        // this is good form, we shall continue
                        System.out.println("What is the preferable time of year? "
                                + "Please choose a number and hit enter.");
                        System.out.println("1. Winter");
                        System.out.println("2. Spring");
                        System.out.println("3. Summmer");
                        System.out.println("4. Autumn");
                        String season = reader.nextLine();
                        if (season.matches("1|2|3|4")) {
                            //this is good form, we shall continue
                            System.out.println("What is the stereotypical gender"
                                    + " for it? Please choose a number and hit enter.");
                            System.out.println("1. Female");
                            System.out.println("2. Male");
                            System.out.println("3. Unisex");
                            String gender = reader.nextLine();
                            if (gender.matches("1|2|3")) {
                                //this is good form, we are finally there, lets make it!

                                Scent newScent = new Scent(null, scentName, brand,
                                        Integer.parseInt(timeOfDay), Integer.parseInt(season),
                                        Integer.parseInt(gender));
                                service.createScent(newScent);

                                System.out.println("This scent has now been registered.");
                                printMenu();

                            } else {
                                System.out.println("Please choose either 1, 2 or 3.");
                                printMenu();
                            }

                        } else {
                            System.out.println("Please choose either 1, 2, 3 or 4.");
                            printMenu();
                        }

                    } else {
                        System.out.println("Please choose either 1 or 2.");
                        printMenu();
                    }

                } else {
                    System.out.println("This scent has already been registered.");
                    printMenu();
                }

            } else {
                System.out.println("Brand cannot be empty (and not too long either). ");
                printMenu();
            }

        } else {
            System.out.println("Name cannot be empty (and not too long either).");
            printMenu();
        }

    }

    public static void menu3(Scanner reader, ScentUpService service) throws SQLException {
        //login
        System.out.println("Please type your username and enter");
        String username = reader.nextLine();

        if (username.matches(".{5,200}")) {

            if (service.login(username) == false) {
                System.out.println("This username was not found.");
                printMenu();
            } else {
                //todo login and open userpage
                System.out.println("Welcome to ScentUp " + service.getLoggedIn().getName());
                service.login(username);
                afterLogin();
                while (true) {

                    String afterLoginChoice = reader.nextLine();
                    if (afterLoginChoice.matches("1|2|3")) {
                        if (afterLoginChoice.matches("1")) {
                            //list of all scents user has
                            List<Scent> userHasTheseScents = service.getScentsUserHas();

                            userHasTheseScents.stream()
                                    .map(scent -> scent.getName())
                                    .forEach(scent -> System.out.println(scent));

                        }
                        if (afterLoginChoice.matches("2")) {
                            //list of all scents user has not
                            List<Scent> userHasNotTheseScents = service.getScentsUserHasNot();

                            userHasNotTheseScents.stream()
                                    //.map(scent -> scent.getName())
                                    .forEach(scent -> System.out.println(scent.toString()));
                            System.out.println("If you want to add a scent to your collection, "
                                    + "please type the number of the scent ");

                            String lisattava = reader.nextLine();
                            if (lisattava.matches("[1-999]")) {
                                preferences();
                                String preference = reader.nextLine();
                                if (preference.matches("1|2|3")) {
                                    if (service.createUserScent(service.getLoggedIn().getUserId(),
                                            Integer.parseInt(lisattava), new Date((int) new java.util.Date().getTime()),
                                            Integer.parseInt(preference), 1)) {
                                        System.out.println("This scent has now been added to your collection.");
                                    } else {
                                        System.out.println("This scent was not added, maybe one of these"
                                                + " is enough in one collection.");
                                        afterLogin();
                                    }
                                } else {
                                    System.out.println("Please type a number mentioned in the list.");
                                    preferences();
                                }

                            } else {
                                System.out.println("Please type a number mentioned in the list.");
                                afterLogin();
                            }
                        }

                        if (afterLoginChoice.matches("3")) {
                            //logout
                            System.out.println("See you soon " + service.getLoggedIn().getName() + "!");
                            printMenu();
                            break;
                        }
                        // todo listing or browse or logout
                    } else {
                        System.out.println("Please choose either 1, 2 or 3");
                        afterLogin();
                    }

                }

            }

        } else {
            System.out.println("Username needs to be min 5 characters (and not too long either).");
            printMenu();
        }

    }

    public static void menu4() {

        //quits the application
        System.out.println("See you soon!");
    }

    public static void printMenu() {
        System.out.println(" ");
        System.out.println("What to do next?");
        System.out.println("1. Create a new User");
        System.out.println("2. Add a new Scent");
        System.out.println("3. ScentIn");
        System.out.println("4. ScentOut");
    }

    public static void afterLogin() {
        System.out.println(" ");
        System.out.println("What to do next?");
        System.out.println("1. Print what I have chosen");
        System.out.println("2. Browse and add to collection");
        System.out.println("3. Logout");

    }

    public static void preferences() {
        System.out.println("");
        System.out.println("What do you think of this scent?");
        System.out.println("1. Dislike");
        System.out.println("2. Neutral");
        System.out.println("3. Love");
    }

}
