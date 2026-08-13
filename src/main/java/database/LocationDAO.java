package database;

import model.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LocationDAO - Data Access Object for Location entities
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class LocationDAO {
    private Connection connection;

    public LocationDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Insert a new location
     */
    public int insert(Location location) {
        String sql = "INSERT INTO locations (name, area, location_type, latitude, longitude, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getArea());
            pstmt.setString(3, location.getLocationType());
            pstmt.setDouble(4, location.getLatitude());
            pstmt.setDouble(5, location.getLongitude());
            pstmt.setBoolean(6, location.isActive());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting location failed, no rows affected");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    location.setLocationId(rs.getInt(1));
                }
            }

            return location.getLocationId();

        } catch (SQLException e) {
            System.err.println("Error inserting location: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Find location by ID
     */
    public Location findById(int locationId) {
        String sql = "SELECT * FROM locations WHERE location_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, locationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLocation(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding location: " + e.getMessage());
        }

        return null;
    }

    /**
     * Find all locations
     */
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations ORDER BY name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                locations.add(mapResultSetToLocation(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all locations: " + e.getMessage());
        }

        return locations;
    }

    /**
     * Find locations by area
     */
    public List<Location> findByArea(String area) {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations WHERE area = ? ORDER BY name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, area);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    locations.add(mapResultSetToLocation(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding locations by area: " + e.getMessage());
        }

        return locations;
    }

    /**
     * Find locations by type
     */
    public List<Location> findByType(String locationType) {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations WHERE location_type = ? ORDER BY name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, locationType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    locations.add(mapResultSetToLocation(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding locations by type: " + e.getMessage());
        }

        return locations;
    }

    /**
     * Update location
     */
    public boolean update(Location location) {
        String sql = "UPDATE locations SET name=?, area=?, location_type=?, latitude=?, " +
                     "longitude=?, is_active=?, updated_at=CURRENT_TIMESTAMP " +
                     "WHERE location_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getArea());
            pstmt.setString(3, location.getLocationType());
            pstmt.setDouble(4, location.getLatitude());
            pstmt.setDouble(5, location.getLongitude());
            pstmt.setBoolean(6, location.isActive());
            pstmt.setInt(7, location.getLocationId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating location: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete location
     */
    public boolean delete(int locationId) {
        String sql = "DELETE FROM locations WHERE location_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, locationId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting location: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count all locations
     */
    public int count() {
        String sql = "SELECT COUNT(*) as count FROM locations";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting locations: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Get active locations only
     */
    public List<Location> findActive() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations WHERE is_active = 1 ORDER BY name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                locations.add(mapResultSetToLocation(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding active locations: " + e.getMessage());
        }

        return locations;
    }

    /**
     * Map ResultSet to Location object
     */
    private Location mapResultSetToLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setName(rs.getString("name"));
        location.setArea(rs.getString("area"));
        location.setLocationType(rs.getString("location_type"));
        location.setLatitude(rs.getDouble("latitude"));
        location.setLongitude(rs.getDouble("longitude"));
        location.setActive(rs.getBoolean("is_active"));
        location.setCreatedAt(rs.getTimestamp("created_at"));
        location.setUpdatedAt(rs.getTimestamp("updated_at"));
        return location;
    }
}
