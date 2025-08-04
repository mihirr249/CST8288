/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 *
 * @author mihir
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Singleton class for managing database connections.
 */
public class DataSource {

    private final String url;
    private final String username;
    private final String password;

    private DataSource() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/java/businesslayer/database.properties"));

            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");

        } catch (IOException e) {
            throw new RuntimeException("Error reading database.properties", e);
        }
    }

    private static class Holder {
        private static final DataSource INSTANCE = new DataSource();
    }

    public static DataSource getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
