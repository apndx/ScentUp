/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.io.File;
import java.util.Scanner;
import scentup.dao.Database;
import scentup.dao.UserDao;

/**
 *
 * @author hdheli
 */
public class ScentUpUi {

    public static void main(String[] args) throws ClassNotFoundException {
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
                // todo
            } else if (chosen.matches("2")) {
                break;
            } else {
                System.out.println("You should type a number mentioned in the menu.");
            }

        }

    }

}
