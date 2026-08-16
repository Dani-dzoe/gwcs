package algorithms;

import model.Truck;
import model.WasteRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreedyTruckAssignmentTest {

    private DistanceProvider threeLocationProvider() {
        String[] locations = {"BUSH_CANTEEN", "PENTAGON", "JQB"};
        double[][] dist = {
                {0, 2, 5},
                {2, 0, 4},
                {5, 4, 0}
        };
        return new MapDistanceProvider(locations, dist);
    }

    @Test
    void shouldAssignNearestAvailableTruck() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        WasteRequest request = new WasteRequest(1, "BUSH_CANTEEN", "GENERAL", 80, 3, 50, 1000);
        Truck near = new Truck(1, "Kofi", 200, "PENTAGON", 90, true);  // distance 2
        Truck far = new Truck(2, "Ama", 200, "JQB", 90, true);         // distance 5

        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{request}, new Truck[]{near, far});

        assertTrue(results[0].isAssigned());
        assertEquals(1, results[0].getTruck().getTruckId());
    }

    @Test
    void shouldProcessHigherPriorityRequestsFirst() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        WasteRequest low = new WasteRequest(1, "BUSH_CANTEEN", "GENERAL", 20, 1, 100, 1000);
        WasteRequest high = new WasteRequest(2, "PENTAGON", "GENERAL", 90, 5, 100, 1000);
        Truck onlyTruck = new Truck(1, "Kofi", 100, "JQB", 90, true);

        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{low, high}, new Truck[]{onlyTruck});

        assertEquals(2, results[0].getRequest().getRequestId(), "high priority request should be processed first");
        assertTrue(results[0].isAssigned());
        assertFalse(results[1].isAssigned(), "only enough capacity for one request, low priority should lose out");
    }

    @Test
    void shouldSkipUnavailableTrucks() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        WasteRequest request = new WasteRequest(1, "BUSH_CANTEEN", "GENERAL", 50, 3, 30, 1000);
        Truck unavailable = new Truck(1, "Kofi", 200, "PENTAGON", 90, false); // closer but unavailable
        Truck available = new Truck(2, "Ama", 200, "JQB", 90, true);

        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{request}, new Truck[]{unavailable, available});

        assertTrue(results[0].isAssigned());
        assertEquals(2, results[0].getTruck().getTruckId());
    }

    @Test
    void shouldReturnUnassignedWhenNoTruckHasEnoughCapacity() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        WasteRequest heavy = new WasteRequest(1, "BUSH_CANTEEN", "GENERAL", 90, 5, 500, 1000);
        Truck small = new Truck(1, "Kofi", 100, "PENTAGON", 90, true);

        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{heavy}, new Truck[]{small});

        assertFalse(results[0].isAssigned());
        assertNull(results[0].getTruck());
    }

    @Test
    void shouldThrowExceptionWhenRequestsArrayIsNull() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        Truck truck = new Truck(1, "Kofi", 100, "PENTAGON", 90, true);

        assertThrows(IllegalArgumentException.class,
                () -> greedy.assign(null, new Truck[]{truck}));
    }

    @Test
    void shouldHandleEmptyRequestsArray() {
        GreedyTruckAssignment greedy = new GreedyTruckAssignment(threeLocationProvider());
        Truck truck = new Truck(1, "Kofi", 100, "PENTAGON", 90, true);

        GreedyTruckAssignment.Assignment[] results = greedy.assign(new WasteRequest[]{}, new Truck[]{truck});

        assertEquals(0, results.length);
    }
}
