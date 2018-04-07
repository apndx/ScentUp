/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.domain.User;

/**
 *
 * @author hdheli
 */
public class ScentUpUi {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());

        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);

        System.out.println("Welcome to ScentUp! ");
        System.out.println(" ");
        printMenu();

        Scanner reader = new Scanner(System.in);

        // here should be made sure that input is a number of the menu, otherwise declined
        while (true) {
            String chosen = reader.nextLine();
            if (chosen.matches("1")) {
                
                menu1(reader, users);

            } else if (chosen.matches("2")) {
                // add a new scent
                // todo
                menu2(reader, scents);
                
                
            }   else if (chosen.matches("3")) {
                // login
                menu3(reader, users);
            
            } else if (chosen.matches("4")) {
                menu4();
                break;
            }else {
        
                System.out.println("Please type a number mentioned in the menu.");
            }

        }

    }

    public static void menu1(Scanner reader, UserDao users) throws SQLException {
        // creates a new user to the database
        
        System.out.println("What is your name?");
        String name = reader.nextLine();
        if (name.matches(".{1,200}")) {
            System.out.println("Please type an username (min 5 characters)");
            String username = reader.nextLine();

            if (username.matches(".{5,200}")) { // is it within the size limits

                if (users.isUsernameFree(username)) {
                    // if the username is free
                    User newuser = new User(null, name, username);
                    users.saveOrUpdate(newuser);
                    System.out.println("Your username has now been registered.");
                } else if (!users.isUsernameFree(username)) {
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

    public static void menu2(Scanner reader, ScentDao scents) throws SQLException  {
        // add a new scent. combination of a name and a brand must be unique.
        // the first version continues one step at a time, and aborts if
        // inputs do not meet the criteria
        
        System.out.println("Name of the Scent?");
        String scentName = reader.nextLine();
        
        if (scentName.matches(".{1,200}")){ // is it within the size limits
            System.out.println("Brand of the Scent? If unknown, type unknown and enter.");
            String brand = reader.nextLine();
            
            if (brand.matches(".{1,200}")) {
                
            } else {
                System.out.println("Brand cannot be empty (and not too long either). ");
                printMenu();
            }
                
        } else {
            System.out.println("Name cannot be empty (and not too long either).");
            printMenu();
        }
        
        
    }
    
    public static void menu3(Scanner reader, UserDao users) throws SQLException {
        //login
        System.out.println("Pleas type your username and enter");
        String username = reader.nextLine();

        if (username.matches(".{5,200}")) {
        
            User current = users.findOne(username);
            
            if (current==null) {
                System.out.println("This username was not found.");
                 printMenu();
                 
            } else {
                //todo login and open userpage
                System.out.println("Welcome to ScentUp "+ current.getName());
                printMenu();
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
    

}
