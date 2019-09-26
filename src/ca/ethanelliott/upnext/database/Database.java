package ca.ethanelliott.upnext.database;

import javax.xml.crypto.Data;
import java.sql.*;

public class Database {
    private static Database instance = null;
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static String connectionURI = "jdbc:sqlite:" + System.getProperty("user.home") + "\\upnext.db";
    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(connectionURI);
            setupTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupTables() {
        try {
            Statement s = connection.createStatement();
            for (String stmt : TableCreateStatements.statements) {
                s.execute(stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
