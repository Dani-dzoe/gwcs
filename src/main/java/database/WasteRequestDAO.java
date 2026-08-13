package database;

import model.WasteRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WasteRequestDAO - Data Access Object for WasteRequest entities
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class WasteRequestDAO {
    private Connection connection;

    public WasteRequestDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Insert a new waste request
     */
    public int insert(WasteRequest request) {
        String sql = "INSERT INTO waste_requests (source_location_id, destination_location_id, " +
                     "category, urgency_level, weight_estimate_kg, volume_estimate_m3, " +
                     "time_submitted, deadline, status, priority_score) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getSourceLocationId());
            pstmt.setInt(2, request.getDestinationLocationId());
            pstmt.setString(3, request.getCategory());
            pstmt.setInt(4, request.getUrgencyLevel());
            pstmt.setDouble(5, request.getWeightEstimateKg());
            pstmt.setDouble(6, request.getVolumeEstimateM3());
            pstmt.setString(7, request.getTimeSubmitted());
            pstmt.setString(8, request.getDeadline());
            pstmt.setString(9, request.getStatus());
            pstmt.setDouble(10, request.getPriorityScore());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting waste request failed, no rows affected");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    request.setRequestId(rs.getInt(1));
                }
            }

            return request.getRequestId();

        } catch (SQLException e) {
            System.err.println("Error inserting waste request: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Find request by ID
     */
    public WasteRequest findById(int requestId) {
        String sql = "SELECT * FROM waste_requests WHERE request_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRequest(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding waste request: " + e.getMessage());
        }

        return null;
    }

    /**
     * Find all requests
     */
    public List<WasteRequest> findAll() {
        List<WasteRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM waste_requests ORDER BY time_submitted DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all waste requests: " + e.getMessage());
        }

        return requests;
    }

    /**
     * Find requests by status
     */
    public List<WasteRequest> findByStatus(String status) {
        List<WasteRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM waste_requests WHERE status = ? ORDER BY urgency_level DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRequest(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding requests by status: " + e.getMessage());
        }

        return requests;
    }

    /**
     * Find pending requests
     */
    public List<WasteRequest> findPending() {
        return findByStatus("pending");
    }

    /**
     * Find requests by urgency level
     */
    public List<WasteRequest> findByUrgency(int urgencyLevel) {
        List<WasteRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM waste_requests WHERE urgency_level = ? ORDER BY time_submitted";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, urgencyLevel);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRequest(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding requests by urgency: " + e.getMessage());
        }

        return requests;
    }

    /**
     * Find urgent requests (urgency >= 4)
     */
    public List<WasteRequest> findUrgent() {
        List<WasteRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM waste_requests WHERE urgency_level >= 4 AND status = 'pending' " +
                     "ORDER BY urgency_level DESC, time_submitted";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding urgent requests: " + e.getMessage());
        }

        return requests;
    }

    /**
     * Update request
     */
    public boolean update(WasteRequest request) {
        String sql = "UPDATE waste_requests SET source_location_id=?, destination_location_id=?, " +
                     "category=?, urgency_level=?, weight_estimate_kg=?, volume_estimate_m3=?, " +
                     "time_submitted=?, deadline=?, status=?, assigned_truck_id=?, priority_score=?, " +
                     "notes=?, updated_at=CURRENT_TIMESTAMP " +
                     "WHERE request_id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, request.getSourceLocationId());
            pstmt.setInt(2, request.getDestinationLocationId());
            pstmt.setString(3, request.getCategory());
            pstmt.setInt(4, request.getUrgencyLevel());
            pstmt.setDouble(5, request.getWeightEstimateKg());
            pstmt.setDouble(6, request.getVolumeEstimateM3());
            pstmt.setString(7, request.getTimeSubmitted());
            pstmt.setString(8, request.getDeadline());
            pstmt.setString(9, request.getStatus());
            pstmt.setObject(10, request.getAssignedTruckId());
            pstmt.setDouble(11, request.getPriorityScore());
            pstmt.setString(12, request.getNotes());
            pstmt.setInt(13, request.getRequestId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating waste request: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete request
     */
    public boolean delete(int requestId) {
        String sql = "DELETE FROM waste_requests WHERE request_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting waste request: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count all requests
     */
    public int count() {
        String sql = "SELECT COUNT(*) as count FROM waste_requests";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting waste requests: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Count requests by status
     */
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) as count FROM waste_requests WHERE status = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error counting requests by status: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Map ResultSet to WasteRequest object
     */
    private WasteRequest mapResultSetToRequest(ResultSet rs) throws SQLException {
        WasteRequest request = new WasteRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setSourceLocationId(rs.getInt("source_location_id"));
        request.setDestinationLocationId(rs.getInt("destination_location_id"));
        request.setCategory(rs.getString("category"));
        request.setUrgencyLevel(rs.getInt("urgency_level"));
        request.setWeightEstimateKg(rs.getDouble("weight_estimate_kg"));
        request.setVolumeEstimateM3(rs.getDouble("volume_estimate_m3"));
        request.setTimeSubmitted(rs.getString("time_submitted"));
        request.setDeadline(rs.getString("deadline"));
        request.setStatus(rs.getString("status"));
        request.setAssignedTruckId(rs.getObject("assigned_truck_id") != null ? rs.getInt("assigned_truck_id") : null);
        request.setPriorityScore(rs.getDouble("priority_score"));
        request.setNotes(rs.getString("notes"));
        request.setCreatedAt(rs.getTimestamp("created_at"));
        request.setUpdatedAt(rs.getTimestamp("updated_at"));
        return request;
    }
}
