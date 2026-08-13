package database;

import model.AlgorithmRun;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AlgorithmRunDAO - Data Access Object for AlgorithmRun entities
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class AlgorithmRunDAO {
    private Connection connection;

    public AlgorithmRunDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Insert a new algorithm run
     */
    public int insert(AlgorithmRun run) {
        String sql = "INSERT INTO algorithm_runs (algorithm_name, algorithm_category, input_size, " +
                     "time_nanoseconds, time_milliseconds, memory_kb, operations_count, " +
                     "comparisons_count, success, error_message, parameters, machine_spec, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, run.getAlgorithmName());
            pstmt.setString(2, run.getAlgorithmCategory());
            pstmt.setInt(3, run.getInputSize());
            pstmt.setLong(4, run.getTimeNanoseconds());
            pstmt.setDouble(5, run.getTimeMilliseconds() != null ? run.getTimeMilliseconds() : run.getCalculatedTimeMs());
            pstmt.setObject(6, run.getMemoryKb());
            pstmt.setObject(7, run.getOperationsCount());
            pstmt.setObject(8, run.getComparisonsCount());
            pstmt.setBoolean(9, run.isSuccess());
            pstmt.setString(10, run.getErrorMessage());
            pstmt.setString(11, run.getParameters());
            pstmt.setString(12, run.getMachineSpec());
            pstmt.setString(13, run.getNotes());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting algorithm run failed, no rows affected");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    run.setRunId(rs.getInt(1));
                }
            }

            return run.getRunId();

        } catch (SQLException e) {
            System.err.println("Error inserting algorithm run: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Find run by ID
     */
    public AlgorithmRun findById(int runId) {
        String sql = "SELECT * FROM algorithm_runs WHERE run_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, runId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRun(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding algorithm run: " + e.getMessage());
        }

        return null;
    }

    /**
     * Find all runs
     */
    public List<AlgorithmRun> findAll() {
        List<AlgorithmRun> runs = new ArrayList<>();
        String sql = "SELECT * FROM algorithm_runs ORDER BY date_run DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                runs.add(mapResultSetToRun(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all algorithm runs: " + e.getMessage());
        }

        return runs;
    }

    /**
     * Find runs by algorithm name
     */
    public List<AlgorithmRun> findByAlgorithm(String algorithmName) {
        List<AlgorithmRun> runs = new ArrayList<>();
        String sql = "SELECT * FROM algorithm_runs WHERE algorithm_name = ? ORDER BY input_size";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, algorithmName);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    runs.add(mapResultSetToRun(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding runs by algorithm: " + e.getMessage());
        }

        return runs;
    }

    /**
     * Find runs by category
     */
    public List<AlgorithmRun> findByCategory(String category) {
        List<AlgorithmRun> runs = new ArrayList<>();
        String sql = "SELECT * FROM algorithm_runs WHERE algorithm_category = ? ORDER BY date_run";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    runs.add(mapResultSetToRun(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding runs by category: " + e.getMessage());
        }

        return runs;
    }

    /**
     * Find runs by input size range
     */
    public List<AlgorithmRun> findByInputSizeRange(int minSize, int maxSize) {
        List<AlgorithmRun> runs = new ArrayList<>();
        String sql = "SELECT * FROM algorithm_runs WHERE input_size BETWEEN ? AND ? ORDER BY input_size";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, minSize);
            pstmt.setInt(2, maxSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    runs.add(mapResultSetToRun(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding runs by input size: " + e.getMessage());
        }

        return runs;
    }

    /**
     * Get average time for an algorithm at a given input size
     */
    public double getAverageTime(String algorithmName, int inputSize) {
        String sql = "SELECT AVG(time_milliseconds) as avg_time FROM algorithm_runs " +
                     "WHERE algorithm_name = ? AND input_size = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, algorithmName);
            pstmt.setInt(2, inputSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_time");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting average time: " + e.getMessage());
        }

        return 0.0;
    }

    /**
     * Delete run
     */
    public boolean delete(int runId) {
        String sql = "DELETE FROM algorithm_runs WHERE run_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, runId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting algorithm run: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete all runs
     */
    public boolean deleteAll() {
        String sql = "DELETE FROM algorithm_runs";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting all runs: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count all runs
     */
    public int count() {
        String sql = "SELECT COUNT(*) as count FROM algorithm_runs";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting algorithm runs: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Map ResultSet to AlgorithmRun object
     */
    private AlgorithmRun mapResultSetToRun(ResultSet rs) throws SQLException {
        AlgorithmRun run = new AlgorithmRun();
        run.setRunId(rs.getInt("run_id"));
        run.setAlgorithmName(rs.getString("algorithm_name"));
        run.setAlgorithmCategory(rs.getString("algorithm_category"));
        run.setInputSize(rs.getInt("input_size"));
        run.setTimeNanoseconds(rs.getLong("time_nanoseconds"));
        run.setTimeMilliseconds(rs.getDouble("time_milliseconds"));
        run.setMemoryKb(rs.getObject("memory_kb") != null ? rs.getInt("memory_kb") : null);
        run.setOperationsCount(rs.getObject("operations_count") != null ? rs.getInt("operations_count") : null);
        run.setComparisonsCount(rs.getObject("comparisons_count") != null ? rs.getInt("comparisons_count") : null);
        run.setSuccess(rs.getBoolean("success"));
        run.setErrorMessage(rs.getString("error_message"));
        run.setParameters(rs.getString("parameters"));
        run.setDateRun(rs.getTimestamp("date_run"));
        run.setMachineSpec(rs.getString("machine_spec"));
        run.setNotes(rs.getString("notes"));
        return run;
    }
}
