/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.ui;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author hdheli
 */
public class Main {
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Scanner reader = new Scanner(System.in);
       
        ScentUpTextUi textUi = new ScentUpTextUi(reader);
        textUi.start();
        
    }
}
