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
import static javafx.application.Application.launch;
import scentup.dao.Database;
import scentup.dao.ScentDao;
import scentup.dao.UserDao;
import scentup.dao.UserScentDao;
import scentup.domain.Scent;
import scentup.domain.ScentUpService;
import scentup.domain.User;
import scentup.domain.UserScent;

/**
 *
 * @author hdheli
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ////when using GUI, these textUI rows should be commented away
        Scanner reader = new Scanner(System.in);  
        File file = new File("ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        database.init();
        UserDao users = new UserDao(database);
        ScentDao scents = new ScentDao(database);
        UserScentDao userScents = new UserScentDao(database);
        ScentUpService scentUpService = new ScentUpService(users, scents, userScents);
        ScentUpTextUi textUi = new ScentUpTextUi(reader, scentUpService);
        textUi.start(); 
        
        
        
         ////GUI needs to create file, ddatabase and  daos in the class itself unlike txtUi
         //// when using textUI these two rows below should be commented away
//        ScentUpGui scentUpGui = new ScentUpGui();
//        launch(ScentUpGui.class);

    }
}
