package database;

import model.Road;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RoadDAO - Data Access Object for Road entities
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class RoadDAO {
    private Connection connection;

    public RoadDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Insert a new road
     */
    public int insert(Road road) {
        String sql = "INSERT INTO roads (from_location_id, to_location_id, distance_km, " +
                     "travel_time_minutes, road_condition_weight, is_one_way) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, road.getFromLocationId());
            pstmt.setInt(2, road.getToLocationId());
            pstmt.setDouble(3, road.getDistanceKm());
            pstmt.setInt(4, road.getTravelTimeMinutes());
            pstmt.setDouble(5, road.getRoadConditionWeight());
            pstmt.setBoolean(6, road.isOneWay());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting road failed, no rows affected");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    road.setRoadId(rs.getInt(1));
                }
            }

            return road.getRoadId();

        } catch (SQLException e) {
            System.err.println("Error inserting road: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Find road by ID
     */
    public Road findById(int roadId) {
        String sql = "SELECT * FROM roads WHERE road_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roadId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoad(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding road: " + e.getMessage());
        }

        return null;
    }

    /**
     * Find all roads
     */
    public List<Road> findAll() {
        List<Road> roads = new ArrayList<>();
        String sql = "SELECT * FROM roads ORDER BY road_id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                roads.add(mapResultSetToRoad(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all roads: " + e.getMessage());
        }

        return roads;
    }

    /**
     * Find roads from a specific location
     */
    public List<Road> findFromLocation(int locationId) {
        List<Road> roads = new ArrayList<>();
        String sql = "SELECT * FROM roads WHERE from_location_id = ? ORDER BY road_id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, locationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roads.add(mapResultSetToRoad(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding roads from location: " + e.getMessage());
        }

        return roads;
    }

    /**
     * Find roads to a specific location
     */
    public List<Road> findToLocation(int locationId) {
        List<Road> roads = new ArrayList<>();
        String sql = "SELECT * FROM roads WHERE to_location_id = ? ORDER BY road_id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, locationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roads.add(mapResultSetToRoad(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding roads to location: " + e.getMessage());
        }

        return roads;
    }

    /**
     * Find road between two locations
     */
    public Road findBetween(int fromLocationId, int toLocationId) {
        String sql = "SELECT * FROM roads WHERE from_location_id = ? AND to_location_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, fromLocationId);
            pstmt.setInt(2, toLocationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoad(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding road between locations: " + e.getMessage());
        }

        return null;
    }

    /**
     * Update road
     */
    public boolean update(Road road) {
        String sql = "UPDATE roads SET from_location_id=?, to_location_id=?, distance_km=?, " +
                     "travel_time_minutes=?, road_condition_weight=?, is_one_way=? " +
                     "WHERE road_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, road.getFromLocationId());
            pstmt.setInt(2, road.getToLocationId());
            pstmt.setDouble(3, road.getDistanceKm());
            pstmt.setInt(4, road.getTravelTimeMinutes());
            pstmt.setDouble(5, road.getRoadConditionWeight());
            pstmt.setBoolean(6, road.isOneWay());
            pstmt.setInt(7, road.getRoadId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating road: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete road
     */
    public boolean delete(int roadId) {
        String sql = "DELETE FROM roads WHERE road_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roadId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting road: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count all roads
     */
    public int count() {
        String sql = "SELECT COUNT(*) as count FROM roads";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting roads: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Map ResultSet to Road object
     */
    private Road mapResultSetToRoad(ResultSet rs) throws SQLException {
        Road road = new Road();
        road.setRoadId(rs.getInt("road_id"));
        road.setFromLocationId(rs.getInt("from_location_id"));
        road.setToLocationId(rs.getInt("to_location_id"));
        road.setDistanceKm(rs.getDouble("distance_km"));
        road.setTravelTimeMinutes(rs.getInt("travel_time_minutes"));
        road.setRoadConditionWeight(rs.getDouble("road_condition_weight"));
        road.setOneWay(rs.getBoolean("is_one_way"));
        road.setCreatedAt(rs.getTimestamp("created_at"));
        return road;
    }
}
