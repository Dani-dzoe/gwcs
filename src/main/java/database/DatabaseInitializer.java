package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseInitializer - Initializes database schema
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Creates database tables and schema from SQL scripts.
 */
public class DatabaseInitializer {
    private Connection connection;
    private String schemaFilePath;

    /**
     * Constructor
     * @param schemaFilePath Path to schema.sql file
     */
    public DatabaseInitializer(String schemaFilePath) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.schemaFilePath = schemaFilePath;
    }

    /**
     * Initialize the database by executing schema.sql
     * @return true if initialization successful
     */
    public boolean initialize() {
        System.out.println("Initializing database schema...");

        try (Statement stmt = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath))) {

            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }

            String[] sqlStatements = sqlBuilder.toString().split(";");

            for (String sql : sqlStatements) {
                String trimmedSql = sql.trim();
                if (!trimmedSql.isEmpty() && !trimmedSql.startsWith("--")) {
                    stmt.execute(trimmedSql + ";");
                }
            }

            System.out.println("Database schema initialized successfully");
            return true;

        } catch (SQLException e) {
            System.err.println("SQL error during initialization: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("Error reading schema file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            return false;
        }
    }

    /**
     * Drop all tables (for testing/reset)
     * @return true if successful
     */
    public boolean dropAllTables() {
        System.out.println("Dropping all tables...");

        String[] dropStatements = {
            "DROP TABLE IF EXISTS algorithm_runs;",
            "DROP TABLE IF EXISTS waste_requests;",
            "DROP TABLE IF EXISTS roads;",
            "DROP TABLE IF EXISTS trucks;",
            "DROP TABLE IF EXISTS locations;",
            "DROP TABLE IF EXISTS auditevents;"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String sql : dropStatements) {
                stmt.execute(sql);
            }
            System.out.println("All tables dropped successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error dropping tables: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if database is initialized
     * @return true if locations table exists
     */
    public boolean isInitialized() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT COUNT(*) FROM locations");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Initialize with transaction support
     * @return true if successful
     */
    public boolean initializeWithTransaction() {
        System.out.println("Initializing database with transaction support...");

        DatabaseConnection.getInstance().beginTransaction();

        try {
            boolean success = initialize();

            if (success) {
                DatabaseConnection.getInstance().commit();
                System.out.println("Transaction committed successfully");
            } else {
                DatabaseConnection.getInstance().rollback();
                System.out.println("Transaction rolled back");
            }

            return success;
        } catch (Exception e) {
            DatabaseConnection.getInstance().rollback();
            System.err.println("Transaction failed: " + e.getMessage());
            return false;
        }
    }
}
