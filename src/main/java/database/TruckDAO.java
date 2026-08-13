package database;

import model.Truck;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TruckDAO - Data Access Object for Truck entities
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class TruckDAO {
    private Connection connection;

    public TruckDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Insert a new truck
     */
    public int insert(Truck truck) {
        String sql = "INSERT INTO trucks (truck_name, truck_type, capacity_kg, " +
                     "home_location_id, availability_status, fuel_level) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, truck.getTruckName());
            pstmt.setString(2, truck.getTruckType());
            pstmt.setDouble(3, truck.getCapacityKg());
            pstmt.setObject(4, truck.getHomeLocationId());
            pstmt.setString(5, truck.getAvailabilityStatus());
            pstmt.setDouble(6, truck.getFuelLevel());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting truck failed, no rows affected");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    truck.setTruckId(rs.getInt(1));
                }
            }

            return truck.getTruckId();

        } catch (SQLException e) {
            System.err.println("Error inserting truck: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Find truck by ID
     */
    public Truck findById(int truckId) {
        String sql = "SELECT * FROM trucks WHERE truck_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, truckId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTruck(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding truck: " + e.getMessage());
        }

        return null;
    }

    /**
     * Find all trucks
     */
    public List<Truck> findAll() {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks ORDER BY truck_name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trucks.add(mapResultSetToTruck(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all trucks: " + e.getMessage());
        }

        return trucks;
    }

    /**
     * Find available trucks
     */
    public List<Truck> findAvailable() {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks WHERE availability_status = 'available' AND fuel_level > 20 " +
                     "ORDER BY capacity_kg DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trucks.add(mapResultSetToTruck(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding available trucks: " + e.getMessage());
        }

        return trucks;
    }

    /**
     * Find trucks by type
     */
    public List<Truck> findByType(String truckType) {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks WHERE truck_type = ? ORDER BY capacity_kg DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, truckType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trucks.add(mapResultSetToTruck(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding trucks by type: " + e.getMessage());
        }

        return trucks;
    }

    /**
     * Find trucks by status
     */
    public List<Truck> findByStatus(String status) {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks WHERE availability_status = ? ORDER BY truck_name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trucks.add(mapResultSetToTruck(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding trucks by status: " + e.getMessage());
        }

        return trucks;
    }

    /**
     * Update truck
     */
    public boolean update(Truck truck) {
        String sql = "UPDATE trucks SET truck_name=?, truck_type=?, capacity_kg=?, " +
                     "home_location_id=?, availability_status=?, fuel_level=?, " +
                     "current_location_id=?, assigned_route_id=?, updated_at=CURRENT_TIMESTAMP " +
                     "WHERE truck_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, truck.getTruckName());
            pstmt.setString(2, truck.getTruckType());
            pstmt.setDouble(3, truck.getCapacityKg());
            pstmt.setObject(4, truck.getHomeLocationId());
            pstmt.setString(5, truck.getAvailabilityStatus());
            pstmt.setDouble(6, truck.getFuelLevel());
            pstmt.setObject(7, truck.getCurrentLocationId());
            pstmt.setObject(8, truck.getAssignedRouteId());
            pstmt.setInt(9, truck.getTruckId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating truck: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete truck
     */
    public boolean delete(int truckId) {
        String sql = "DELETE FROM trucks WHERE truck_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, truckId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting truck: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count all trucks
     */
    public int count() {
        String sql = "SELECT COUNT(*) as count FROM trucks";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting trucks: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Count available trucks
     */
    public int countAvailable() {
        String sql = "SELECT COUNT(*) as count FROM trucks WHERE availability_status = 'available'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting available trucks: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Map ResultSet to Truck object
     */
    private Truck mapResultSetToTruck(ResultSet rs) throws SQLException {
        Truck truck = new Truck();
        truck.setTruckId(rs.getInt("truck_id"));
        truck.setTruckName(rs.getString("truck_name"));
        truck.setTruckType(rs.getString("truck_type"));
        truck.setCapacityKg(rs.getDouble("capacity_kg"));
        truck.setHomeLocationId(rs.getObject("home_location_id") != null ? rs.getInt("home_location_id") : null);
        truck.setAvailabilityStatus(rs.getString("availability_status"));
        truck.setFuelLevel(rs.getDouble("fuel_level"));
        truck.setCurrentLocationId(rs.getObject("current_location_id") != null ? rs.getInt("current_location_id") : null);
        truck.setAssignedRouteId(rs.getObject("assigned_route_id") != null ? rs.getInt("assigned_route_id") : null);
        truck.setCreatedAt(rs.getTimestamp("created_at"));
        truck.setUpdatedAt(rs.getTimestamp("updated_at"));
        return truck;
    }
}
