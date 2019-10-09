package ca.ethanelliott.upnext.server.database;

import ca.ethanelliott.upnext.server.upnext.Party;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static String connectionURI = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "upnext.db";
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
            s.execute(String.valueOf(TableCreateStatements.statements.get("create")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeState(ArrayList<Party> parties) {

    }

    public ArrayList<Party> loadState() {
        return null;
    }
}