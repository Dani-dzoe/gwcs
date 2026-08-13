package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseLoader - Loads data from CSV files into database
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Imports CSV data files and populates database tables.
 */
public class DatabaseLoader {
    private Connection connection;

    /**
     * Constructor
     */
    public DatabaseLoader() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Load locations from CSV file
     * @param filePath Path to locations.csv
     * @return Number of records loaded
     */
    public int loadLocations(String filePath) {
        System.out.println("Loading locations from " + filePath + "...");
        int count = 0;

        String sql = "INSERT INTO locations (name, area, location_type, latitude, longitude, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 6) {
                    pstmt.setString(1, values[1].trim());
                    pstmt.setString(2, values[2].trim());
                    pstmt.setString(3, values[3].trim());
                    pstmt.setDouble(4, Double.parseDouble(values[4].trim()));
                    pstmt.setDouble(5, Double.parseDouble(values[5].trim()));
                    pstmt.setInt(6, Integer.parseInt(values[6].trim()));

                    pstmt.executeUpdate();
                    count++;
                }
            }

            System.out.println("Loaded " + count + " locations");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
        }

        return count;
    }

    /**
     * Load roads from CSV file
     * @param filePath Path to roads.csv
     * @return Number of records loaded
     */
    public int loadRoads(String filePath) {
        System.out.println("Loading roads from " + filePath + "...");
        int count = 0;

        String sql = "INSERT INTO roads (from_location_id, to_location_id, distance_km, " +
                     "travel_time_minutes, road_condition_weight, is_one_way) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 6) {
                    pstmt.setInt(1, Integer.parseInt(values[1].trim()));
                    pstmt.setInt(2, Integer.parseInt(values[2].trim()));
                    pstmt.setDouble(3, Double.parseDouble(values[3].trim()));
                    pstmt.setInt(4, Integer.parseInt(values[4].trim()));
                    pstmt.setDouble(5, Double.parseDouble(values[5].trim()));
                    pstmt.setInt(6, Integer.parseInt(values[6].trim()));

                    pstmt.executeUpdate();
                    count++;
                }
            }

            System.out.println("Loaded " + count + " roads");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
        }

        return count;
    }

    /**
     * Load waste requests from CSV file
     * @param filePath Path to waste_requests.csv
     * @return Number of records loaded
     */
    public int loadWasteRequests(String filePath) {
        System.out.println("Loading waste requests from " + filePath + "...");
        int count = 0;

        String sql = "INSERT INTO waste_requests (source_location_id, destination_location_id, " +
                     "category, urgency_level, weight_estimate_kg, volume_estimate_m3, " +
                     "time_submitted, deadline, status, priority_score) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 10) {
                    pstmt.setInt(1, Integer.parseInt(values[1].trim()));
                    pstmt.setInt(2, Integer.parseInt(values[2].trim()));
                    pstmt.setString(3, values[3].trim());
                    pstmt.setInt(4, Integer.parseInt(values[4].trim()));
                    pstmt.setDouble(5, Double.parseDouble(values[5].trim()));
                    pstmt.setDouble(6, Double.parseDouble(values[6].trim()));
                    pstmt.setString(7, values[7].trim());
                    pstmt.setString(8, values[8].trim());
                    pstmt.setString(9, values[9].trim());
                    pstmt.setDouble(10, Double.parseDouble(values[10].trim()));

                    pstmt.executeUpdate();
                    count++;
                }
            }

            System.out.println("Loaded " + count + " waste requests");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
        }

        return count;
    }

    /**
     * Load trucks from CSV file
     * @param filePath Path to trucks.csv
     * @return Number of records loaded
     */
    public int loadTrucks(String filePath) {
        System.out.println("Loading trucks from " + filePath + "...");
        int count = 0;

        String sql = "INSERT INTO trucks (truck_name, truck_type, capacity_kg, " +
                     "home_location_id, availability_status, fuel_level) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 6) {
                    pstmt.setString(1, values[1].trim());
                    pstmt.setString(2, values[2].trim());
                    pstmt.setDouble(3, Double.parseDouble(values[3].trim()));
                    pstmt.setInt(4, Integer.parseInt(values[4].trim()));
                    pstmt.setString(5, values[5].trim());
                    pstmt.setDouble(6, Double.parseDouble(values[6].trim()));

                    pstmt.executeUpdate();
                    count++;
                }
            }

            System.out.println("Loaded " + count + " trucks");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
        }

        return count;
    }

    /**
     * Get database statistics
     */
    public void printStatistics() {
        System.out.println("\n=== Database Statistics ===");

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM locations");
            if (rs.next()) {
                System.out.println("Locations: " + rs.getInt("count"));
            }

            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM roads");
            if (rs.next()) {
                System.out.println("Roads: " + rs.getInt("count"));
            }

            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM waste_requests");
            if (rs.next()) {
                System.out.println("Waste Requests: " + rs.getInt("count"));
            }

            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM trucks");
            if (rs.next()) {
                System.out.println("Trucks: " + rs.getInt("count"));
            }

            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM algorithm_runs");
            if (rs.next()) {
                System.out.println("Algorithm Runs: " + rs.getInt("count"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting statistics: " + e.getMessage());
        }
    }

    /**
     * Clear all data from tables (for testing)
     */
    public void clearAllData() {
        System.out.println("Clearing all data...");

        String[] tables = {"algorithm_runs", "waste_requests", "roads", "trucks", "locations"};

        try (Statement stmt = connection.createStatement()) {
            for (String table : tables) {
                stmt.execute("DELETE FROM " + table);
            }
            System.out.println("All data cleared");
        } catch (SQLException e) {
            System.err.println("Error clearing data: " + e.getMessage());
        }
    }
}
