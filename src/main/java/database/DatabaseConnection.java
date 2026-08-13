package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * DatabaseConnection - Singleton database connection manager
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Manages SQLite database connections for the Ghana Smart Waste Collection system.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String dbUrl;
    private String dbDriver;

    /**
     * Private constructor for singleton pattern
     */
    private DatabaseConnection() {
        loadConfiguration();
        initializeConnection();
    }

    /**
     * Load database configuration from application.properties
     */
    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                // Default configuration if properties file not found
                this.dbUrl = "jdbc:sqlite:database/ghana_waste.db";
                this.dbDriver = "org.sqlite.JDBC";
                return;
            }

            Properties properties = new Properties();
            properties.load(input);

            this.dbUrl = properties.getProperty("database.url", 
                    "jdbc:sqlite:database/ghana_waste.db");
            this.dbDriver = properties.getProperty("database.driver", 
                    "org.sqlite.JDBC");

        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            this.dbUrl = "jdbc:sqlite:database/ghana_waste.db";
            this.dbDriver = "org.sqlite.JDBC";
        }
    }

    /**
     * Initialize database connection
     */
    private void initializeConnection() {
        try {
            Class.forName(dbDriver);
            this.connection = DriverManager.getConnection(dbUrl);
            this.connection.setAutoCommit(true);
            System.out.println("Database connection established: " + dbUrl);
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Get the singleton instance of DatabaseConnection
     * @return DatabaseConnection instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get the database connection
     * @return Connection object
     */
    public Connection getConnection() {
        if (connection == null) {
            initializeConnection();
        }
        return connection;
    }

    /**
     * Check if connection is valid
     * @return true if connection is open and valid
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Close the database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Begin a transaction (disable auto-commit)
     */
    public void beginTransaction() {
        try {
            if (connection != null) {
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Error beginning transaction: " + e.getMessage());
        }
    }

    /**
     * Commit the current transaction
     */
    public void commit() {
        try {
            if (connection != null) {
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error committing transaction: " + e.getMessage());
        }
    }

    /**
     * Rollback the current transaction
     */
    public void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
        }
    }

    /**
     * Get the database URL
     * @return Database URL string
     */
    public String getDbUrl() {
        return dbUrl;
    }
}
