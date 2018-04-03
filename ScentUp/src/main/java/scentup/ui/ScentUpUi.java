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
                // login
                menu2(reader, users);
            
            } else if (chosen.matches("3")) {
                menu3();
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
            }
        } else {
            System.out.println("Name cannot be empty");
            printMenu();
        }
    }

    public static void menu2(Scanner reader, UserDao users) throws SQLException {
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
    
    public static void menu3() {
        
        //quits the application
        System.out.println("See you soon!");
    }
    
    public static void printMenu() {
        System.out.println("What to do next?");
        System.out.println("1. Create a new User");
        System.out.println("2. Login");
        System.out.println("3. Quit");
    }
    

}
