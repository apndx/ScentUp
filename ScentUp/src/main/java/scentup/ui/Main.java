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

/**
 *
 * @author hdheli
 */
public class Main {
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Scanner reader = new Scanner(System.in);
        
        
        File file = new File("db", "ScentUp.db");
        Database database = new Database("jdbc:sqlite:" + file.getAbsolutePath());
        
        
        ScentUpTextUi textUi = new ScentUpTextUi(reader, database);
        textUi.start();
        
    }
}
