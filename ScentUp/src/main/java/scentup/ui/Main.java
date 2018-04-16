/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import scentup.domain.Scent;
import scentup.domain.User;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 */
public class Main {
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Scanner reader = new Scanner(System.in);
        
        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        
        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);
    
        ScentUpTextUi textUi = new ScentUpTextUi(reader, database, users, scents);
        textUi.start();
        
    }
}
