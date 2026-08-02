package gh.edu.ug.wastesys.db;

import gh.edu.ug.wastesys.model.AlgorithmRun;
import gh.edu.ug.wastesys.model.AuditEvent;
import gh.edu.ug.wastesys.model.Location;
import gh.edu.ug.wastesys.model.Resource;
import gh.edu.ug.wastesys.model.Road;
import gh.edu.ug.wastesys.model.ServiceRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseManager implements AutoCloseable {
    private final Connection connection;

    public DatabaseManager(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to open database", exception);
        }
    }

    public void initializeSchema() {
        execute("""
                CREATE TABLE IF NOT EXISTS locations (
                    locationId INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    area TEXT NOT NULL,
                    type TEXT NOT NULL,
                    latitude REAL NOT NULL,
                    longitude REAL NOT NULL
                )
                """);
        execute("""
                CREATE TABLE IF NOT EXISTS roads (
                    fromLocationId INTEGER NOT NULL,
                    toLocationId INTEGER NOT NULL,
                    distance REAL NOT NULL,
                    travelTime INTEGER NOT NULL,
                    roadConditionWeight REAL NOT NULL
                )
                """);
        execute("""
                CREATE TABLE IF NOT EXISTS service_requests (
                    requestId INTEGER PRIMARY KEY,
                    source INTEGER NOT NULL,
                    destination INTEGER NOT NULL,
                    category TEXT NOT NULL,
                    urgency INTEGER NOT NULL,
                    timeSubmitted TEXT NOT NULL,
                    deadline TEXT NOT NULL,
                    status TEXT NOT NULL,
                    volumeKg REAL NOT NULL,
                    estimatedTimeMinutes REAL NOT NULL
                )
                """);
        execute("""
                CREATE TABLE IF NOT EXISTS resources (
                    resourceId INTEGER PRIMARY KEY,
                    type TEXT NOT NULL,
                    homeLocation INTEGER NOT NULL,
                    capacity INTEGER NOT NULL,
                    availabilityStatus TEXT NOT NULL
                )
                """);
        execute("""
                CREATE TABLE IF NOT EXISTS algorithm_runs (
                    runId INTEGER PRIMARY KEY,
                    algorithmName TEXT NOT NULL,
                    inputSize INTEGER NOT NULL,
                    timeNs INTEGER NOT NULL,
                    memoryKb INTEGER NOT NULL,
                    dateRun TEXT NOT NULL
                )
                """);
        execute("""
                CREATE TABLE IF NOT EXISTS audit_events (
                    eventId INTEGER PRIMARY KEY,
                    eventType TEXT NOT NULL,
                    eventTime TEXT NOT NULL,
                    details TEXT NOT NULL
                )
                """);
    }

    public void saveLocations(List<Location> locations) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO locations(locationId, name, area, type, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)")) {
            for (Location location : locations) {
                statement.setInt(1, location.locationId());
                statement.setString(2, location.name());
                statement.setString(3, location.area());
                statement.setString(4, location.type());
                statement.setDouble(5, location.latitude());
                statement.setDouble(6, location.longitude());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save locations", exception);
        }
    }

    public List<Location> loadLocations() {
        List<Location> locations = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT locationId, name, area, type, latitude, longitude FROM locations ORDER BY locationId")) {
            while (resultSet.next()) {
                locations.add(new Location(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6)));
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to load locations", exception);
        }
        return locations;
    }

    public void saveRoads(List<Road> roads) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO roads(fromLocationId, toLocationId, distance, travelTime, roadConditionWeight) VALUES (?, ?, ?, ?, ?)")) {
            for (Road road : roads) {
                statement.setInt(1, road.fromLocationId());
                statement.setInt(2, road.toLocationId());
                statement.setDouble(3, road.distance());
                statement.setInt(4, road.travelTime());
                statement.setDouble(5, road.roadConditionWeight());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save roads", exception);
        }
    }

    public void saveServiceRequests(List<ServiceRequest> requests) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO service_requests(requestId, source, destination, category, urgency, timeSubmitted, deadline, status, volumeKg, estimatedTimeMinutes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            for (ServiceRequest request : requests) {
                statement.setInt(1, request.requestId());
                statement.setInt(2, request.source());
                statement.setInt(3, request.destination());
                statement.setString(4, request.category());
                statement.setInt(5, request.urgency());
                statement.setString(6, request.timeSubmitted().toString());
                statement.setString(7, request.deadline().toString());
                statement.setString(8, request.status());
                statement.setDouble(9, request.volumeKg());
                statement.setDouble(10, request.estimatedTimeMinutes());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save service requests", exception);
        }
    }

    public void saveResources(List<Resource> resources) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO resources(resourceId, type, homeLocation, capacity, availabilityStatus) VALUES (?, ?, ?, ?, ?)")) {
            for (Resource resource : resources) {
                statement.setInt(1, resource.resourceId());
                statement.setString(2, resource.type());
                statement.setInt(3, resource.homeLocation());
                statement.setInt(4, resource.capacity());
                statement.setString(5, resource.availabilityStatus());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save resources", exception);
        }
    }

    public void saveAlgorithmRuns(List<AlgorithmRun> runs) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO algorithm_runs(runId, algorithmName, inputSize, timeNs, memoryKb, dateRun) VALUES (?, ?, ?, ?, ?, ?)")) {
            for (AlgorithmRun run : runs) {
                statement.setInt(1, run.runId());
                statement.setString(2, run.algorithmName());
                statement.setInt(3, run.inputSize());
                statement.setLong(4, run.timeNs());
                statement.setLong(5, run.memoryKb());
                statement.setString(6, run.dateRun().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save algorithm runs", exception);
        }
    }

    public void saveAuditEvents(List<AuditEvent> events) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO audit_events(eventId, eventType, eventTime, details) VALUES (?, ?, ?, ?)")) {
            for (AuditEvent event : events) {
                statement.setInt(1, event.eventId());
                statement.setString(2, event.eventType());
                statement.setString(3, event.eventTime().toString());
                statement.setString(4, event.details());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save audit events", exception);
        }
    }

    public int count(String tableName) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to count table " + tableName, exception);
        }
    }

    public void clearData() {
        execute("DELETE FROM locations");
        execute("DELETE FROM roads");
        execute("DELETE FROM service_requests");
        execute("DELETE FROM resources");
        execute("DELETE FROM algorithm_runs");
        execute("DELETE FROM audit_events");
    }

    public void saveAll(List<Location> locations, List<Road> roads, List<ServiceRequest> requests, List<Resource> resources, List<AlgorithmRun> runs, List<AuditEvent> events) {
        saveLocations(locations);
        saveRoads(roads);
        saveServiceRequests(requests);
        saveResources(resources);
        saveAlgorithmRuns(runs);
        saveAuditEvents(events);
    }

    private void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to execute schema statement", exception);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to close database", exception);
        }
    }
}
