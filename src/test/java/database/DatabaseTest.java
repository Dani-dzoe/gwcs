package database;

import org.junit.jupiter.api.Test;
import model.Location;
import model.Truck;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * DatabaseTest - Unit tests for Database layer
 */
public class DatabaseTest {

    @Test
    public void testDatabaseConnection() {
        DatabaseConnection conn = DatabaseConnection.getInstance();

        assertNotNull(conn);
        assertNotNull(conn.getConnection());
        assertTrue(conn.isConnected());
    }

    @Test
    public void testLocationDAO() {
        LocationDAO dao = new LocationDAO();

        // Test find all (should not throw exception)
        List<Location> locations = dao.findAll();

        assertNotNull(locations);
        // Count should match CSV data
        assertTrue(locations.size() >= 50);  // At least 50 locations
    }

    @Test
    public void testTruckDAO() {
        TruckDAO dao = new TruckDAO();

        // Test find available
        List<Truck> trucks = dao.findAvailable();

        assertNotNull(trucks);
        assertTrue(trucks.size() > 0);
    }

    @Test
    public void testWasteRequestDAO() {
        WasteRequestDAO dao = new WasteRequestDAO();

        // Test count
        int count = dao.count();

        assertTrue(count >= 300);  // At least 300 requests
    }

    @Test
    public void testDatabaseStatistics() {
        DatabaseLoader loader = new DatabaseLoader();

        // Should not throw exception
        assertDoesNotThrow(() -> {
            loader.printStatistics();
        });
    }
}
