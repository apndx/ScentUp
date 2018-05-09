package scentup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for the database connection and creation (if needed)
 *
 * @author apndx
 */
public class Database {

    private final String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    /**
     * Opens a connection to the database
     *
     * @throws SQLException if this database query does not succeed, this
     * exception is thrown
     * @return Connection returns a Connection
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    /**
     * Initialising procedures for the database. Creates the database and tables
     * for it if they do not exist in the root folder of the program.
     *
     */
    public void init() {
        List<String> createTablesSencences = sqliteTables();

        // "try with resources" closes the connection in the end
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            //  execute command
            for (String sentence : createTablesSencences) {
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
