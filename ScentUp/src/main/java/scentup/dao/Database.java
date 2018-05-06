/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scentup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hdheli
 */
public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> createTablesSencences = sqliteTables();

        // "try with resources" closes the connection in the end
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            //  execute command
            for (String sentence : createTablesSencences) {
                //System.out.println("Running command >> " + sentence);
                st.executeUpdate(sentence);
            }

        } catch (Throwable t) {
            // if table exists, command not executed
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteTables() {
        ArrayList<String> tablesList = new ArrayList<>();

        // creating the datatables
        tablesList.add("CREATE TABLE IF NOT EXISTS User (\n"
                + "    user_id integer PRIMARY KEY,\n"
                + "    name varchar(200),\n"
                + "    username varchar(200));");
        tablesList.add("CREATE TABLE IF NOT EXISTS Scent (\n"
                + "    scent_id integer PRIMARY KEY,\n"
                + "    name varchar(200),\n"
                + "    brand varchar(200),\n"
                + "    timeofday integer,\n"
                + "    season integer,\n"
                + "    gender integer\n"
                + ");");
        tablesList.add("CREATE TABLE IF NOT EXISTS  UserScent (\n"
                + "    user_id integer,\n"
                + "    scent_id integer,\n"
                + "    choicedate integer,\n"
                + "    preference integer,\n"
                + "    active integer,\n"
                + "    FOREIGN KEY (user_id) REFERENCES User(user_id),\n"
                + "    FOREIGN KEY (scent_id) REFERENCES Scent(scent_id)\n"
                + ");");
        tablesList.add("CREATE TABLE IF NOT EXISTS Category (\n"
                + "    category_id integer PRIMARY KEY,     \n"
                + "    name varchar(200),\n"
                + "    weight integer	\n"
                + ");");
        tablesList.add("CREATE TABLE IF NOT EXISTS ScentCategory (\n"
                + "    scent_id integer,\n"
                + "    category_id integer,\n"
                + "    FOREIGN KEY (scent_id) REFERENCES Scent(scent_id),\n"
                + "    FOREIGN KEY (category_id) REFERENCES Category(category_id)\n"
                + ");");

        return tablesList;
    }

}
