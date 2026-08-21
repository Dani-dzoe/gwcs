package algorithms.optimization;

import model.Truck;
import model.WasteRequest;
import java.util.*;

/**
 * GreedyTruckAssignment - Greedy truck assignment algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Greedy approach: Assign smallest capable truck to each request
 * Time Complexity: O(n * m) where n=requests, m=trucks
 * 
 * Note: This may not always give optimal solution
 */
public class GreedyTruckAssignment {

    /**
     * Assignment result
     */
    public static class Assignment {
        public WasteRequest request;
        public Truck truck;
        public boolean success;

        public Assignment(WasteRequest request, Truck truck, boolean success) {
            this.request = request;
            this.truck = truck;
            this.success = success;
        }
    }

    /**
     * Assign trucks to requests greedily
     */
    public static List<Assignment> assignTrucks(List<WasteRequest> requests, List<Truck> trucks) {
        List<Assignment> assignments = new ArrayList<>();
        Set<Integer> usedTrucks = new HashSet<>();

        // Sort requests by urgency (descending)
        List<WasteRequest> sortedRequests = new ArrayList<>(requests);
        sortedRequests.sort((r1, r2) -> Integer.compare(r2.getUrgencyLevel(), r1.getUrgencyLevel()));

        for (WasteRequest request : sortedRequests) {
            Assignment assignment = assignTruck(request, trucks, usedTrucks);
            assignments.add(assignment);

            if (assignment.success && assignment.truck != null) {
                usedTrucks.add(assignment.truck.getTruckId());
            }
        }

        return assignments;
    }

    /**
     * Assign single truck to request
     */
    private static Assignment assignTruck(WasteRequest request, List<Truck> trucks, Set<Integer> usedTrucks) {
        Truck bestTruck = null;
        double minCapacityWaste = Double.MAX_VALUE;

        for (Truck truck : trucks) {
            if (!truck.isAvailable() || usedTrucks.contains(truck.getTruckId())) {
                continue;
            }

            if (truck.canHandleWeight(request.getWeightEstimateKg())) {
                double capacityWaste = truck.getCapacityKg() - request.getWeightEstimateKg();

                if (capacityWaste < minCapacityWaste) {
                    minCapacityWaste = capacityWaste;
                    bestTruck = truck;
                }
            }
        }

        return new Assignment(request, bestTruck, bestTruck != null);
    }

    /**
     * Calculate total assignments
     */
    public static int countSuccessful(List<Assignment> assignments) {
        int count = 0;

        for (Assignment assignment : assignments) {
            if (assignment.success) {
                count++;
            }
        }

        return count;
    }

    /**
     * Calculate total capacity used
     */
    public static double totalCapacityUsed(List<Assignment> assignments) {
        double total = 0.0;

        for (Assignment assignment : assignments) {
            if (assignment.success && assignment.truck != null) {
                total += assignment.request.getWeightEstimateKg();
            }
        }

        return total;
    }
}
