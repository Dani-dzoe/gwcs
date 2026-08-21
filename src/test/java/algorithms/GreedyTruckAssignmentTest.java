package algorithms.optimization;

import org.junit.jupiter.api.Test;
import model.Truck;
import model.WasteRequest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class GreedyTruckAssignmentTest {

    @Test
    public void testAssignTrucks() {
        List<WasteRequest> requests = new ArrayList<>();
        WasteRequest r1 = new WasteRequest();
        r1.setRequestId(1);
        r1.setWeightEstimateKg(100.0);
        r1.setUrgencyLevel(5);
        requests.add(r1);

        List<Truck> trucks = new ArrayList<>();
        Truck t1 = new Truck("Truck1", "compactor", 5000.0);
        t1.setTruckId(1);
        trucks.add(t1);

        List<GreedyTruckAssignment.Assignment> assignments = 
            GreedyTruckAssignment.assignTrucks(requests, trucks);

        assertEquals(1, assignments.size());
        assertTrue(assignments.get(0).success);
    }

    @Test
    public void testCountSuccessful() {
        List<GreedyTruckAssignment.Assignment> assignments = new ArrayList<>();
        assignments.add(new GreedyTruckAssignment.Assignment(null, null, true));
        assignments.add(new GreedyTruckAssignment.Assignment(null, null, false));
        assignments.add(new GreedyTruckAssignment.Assignment(null, null, true));

        assertEquals(2, GreedyTruckAssignment.countSuccessful(assignments));
    }
}
