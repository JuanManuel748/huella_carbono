package com.github.JuanManuel.model.connection;

import com.github.JuanManuel.utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Class MySQLConnection
 * Singleton class to connect to MySQL database
 * @version 1.0
 * @since 1.0
 * @see XMLManager
 * @see MySQLproperties
 */
public class MySQLConnection {
    private static Connection con = null;
    private static final String file = "connection.xml";
    private static MySQLConnection _instance;

    private MySQLConnection() {
        MySQLproperties properties = (MySQLproperties) XMLManager.readXML(new MySQLproperties(), file);

        try {
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static MySQLConnection getConnection() {
        if (_instance == null) {
            _instance = new MySQLConnection();
        }
        return _instance;
    }
}
