package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CSVLoader - Dedicated CSV data loader
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Loads data from CSV files into database tables
 */
public class CSVLoader {
    private Connection connection;

    // =========================================================================
    // ADDED: THE MAIN METHOD ENTRY POINT FOR YOUR TERMINAL COMMAND
    // =========================================================================
    public static void main(String[] args) {
        System.out.println("Starting University of Ghana CSV Loader Tool...");
        
        // 1. Create an instance of the loader
        CSVLoader loader = new CSVLoader();
        
        // 2. Clear old data to prevent duplicate primary key errors
        loader.clearAllData();
        
        // 3. Load all files
        loader.loadAllCSVFiles();
        
        // 4. Verify everything was stored in the tables correctly
        loader.verifyData();
    }
    // =========================================================================

    public CSVLoader() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Load all CSV files
     */
    public void loadAllCSVFiles() {
        System.out.println("\n=== Loading All CSV Data Files ===\n");

        int total = 0;
        total += loadLocations("data/locations.csv");
        total += loadTrucks("data/trucks.csv");
        total += loadRoads("data/roads.csv");
        total += loadWasteRequests("data/waste_requests.csv");

        System.out.println("\n========================================");
        System.out.println("TOTAL RECORDS LOADED: " + total);
        System.out.println("========================================\n");
    }

    /**
     * Load locations from CSV
     */
    public int loadLocations(String filePath) {
        System.out.println("Loading locations from " + filePath + "...");
        return loadCSV(filePath, 
            "INSERT INTO locations (location_id, name, area, location_type, latitude, longitude, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    /**
     * Load trucks from CSV
     */
    public int loadTrucks(String filePath) {
        System.out.println("Loading trucks from " + filePath + "...");
        return loadCSV(filePath, 
            "INSERT INTO trucks (truck_id, truck_name, truck_type, capacity_kg, home_location_id, availability_status, fuel_level) VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    /**
     * Load roads from CSV
     */
    public int loadRoads(String filePath) {
        System.out.println("Loading roads from " + filePath + "...");
        return loadCSV(filePath, 
            "INSERT INTO roads (road_id, from_location_id, to_location_id, distance_km, travel_time_minutes, road_condition_weight, is_one_way) VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    /**
     * Load waste requests from CSV
     */
    public int loadWasteRequests(String filePath) {
        System.out.println("Loading waste requests from " + filePath + "...");
        return loadCSV(filePath, 
            "INSERT INTO waste_requests (request_id, source_location_id, destination_location_id, category, urgency_level, weight_estimate_kg, volume_estimate_m3, time_submitted, deadline, status, assigned_truck_id, priority_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    /**
     * Generic CSV loader
     */
    private int loadCSV(String filePath, String sql) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line = reader.readLine(); // Skip header
            if (line == null) {
                System.out.println("  ⚠ Empty file: " + filePath);
                return 0;
            }

            while ((line = reader.readLine()) != null) {
                try {
                    String[] values = line.split(",");

                    // Set parameters based on number of placeholders
                    int paramIndex = 1;
                    for (String value : values) {
                        String trimmed = value.trim();

                        // Skip first column if it's an ID (auto-increment)
                        if (paramIndex == 1 && sql.contains("AUTOINCREMENT")) {
                            // Skip ID column
                        } else {
                            pstmt.setString(paramIndex, trimmed.isEmpty() ? null : trimmed);
                            paramIndex++;
                        }
                    }

                    pstmt.executeUpdate();
                    count++;

                } catch (Exception e) {
                    System.err.println("  ⚠ Error on line: " + (count + 2) + " - " + e.getMessage());
                }
            }

            System.out.println("  ✓ Loaded " + count + " records");

        } catch (IOException e) {
            System.err.println("  ✗ Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("  ✗ SQL error: " + e.getMessage());
        }

        return count;
    }

    /**
     * Clear all data (for re-loading)
     */
    public void clearAllData() {
        System.out.println("\nClearing all data from tables...");

        String[] tables = {"algorithm_runs", "waste_requests", "roads", "trucks", "locations"};

        try (Statement stmt = connection.createStatement()) {
            for (String table : tables) {
                stmt.execute("DELETE FROM " + table);
                System.out.println("  ✓ Cleared " + table);
            }
        } catch (SQLException e) {
            System.err.println("  ✗ Error clearing data: " + e.getMessage());
        }
    }

    /**
     * Verify data loaded
     */
    public void verifyData() {
        System.out.println("\n=== Verifying Loaded Data ===\n");

        String[] queries = {
            "SELECT COUNT(*) as count FROM locations",
            "SELECT COUNT(*) as count FROM roads",
            "SELECT COUNT(*) as count FROM trucks",
            "SELECT COUNT(*) as count FROM waste_requests",
            "SELECT COUNT(*) as count FROM algorithm_runs"
        };

        String[] labels = {"Locations", "Roads", "Trucks", "Waste Requests", "Algorithm Runs"};

        try (Statement stmt = connection.createStatement()) {
            for (int i = 0; i < queries.length; i++) {
                try (var rs = stmt.executeQuery(queries[i])) {
                    if (rs.next()) {
                        System.out.printf("  %-20s: %,d records\n", labels[i], rs.getInt("count"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("  ✗ Error verifying data: " + e.getMessage());
        }

        System.out.println();
    }
}

