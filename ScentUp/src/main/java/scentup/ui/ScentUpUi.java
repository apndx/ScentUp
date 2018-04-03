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
        System.out.println("What to do next?");
        System.out.println("1. Create new User");
        System.out.println("2. Quit");

        Scanner reader = new Scanner(System.in);

        // here should be made sure that input is int, otherwise declined
        while (true) {
            String chosen = reader.nextLine();
            if (chosen.matches("1")) {

                menu1(reader, users);

            } else if (chosen.matches("2")) {
                menu2();
                break;
            } else {
                System.out.println("Please type a number mentioned in the menu.");
            }

        }

    }

    public static void menu1(Scanner reader, UserDao users) throws SQLException {

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
                    System.out.println("What to do next?");
                    System.out.println("1. Create new User");
                    System.out.println("2. Quit");
                }

            } else {
                System.out.println("Username needs to be min 5 characters.");
            }
        } else {
            System.out.println("Name cannot be empty");
            System.out.println("What to do next?");
            System.out.println("1. Create new User");
            System.out.println("2. Quit");
        }
    }

    public static void menu2() {
        System.out.println("See you soon!");
    }

}
