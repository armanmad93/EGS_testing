package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public final class DatabaseConnectionFactory {

    private final Properties properties;

    public DatabaseConnectionFactory() {
        properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("database.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        String jdbcUrl = properties.getProperty("JDBC_URL");
        String username = properties.getProperty("USERNAME");
        String password = properties.getProperty("PASSWORD");

        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
