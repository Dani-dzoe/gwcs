package algorithms;

import model.Truck;
import model.WasteRequest;

/**
 * Greedy Truck Assignment.
 *
 * Strategy: process waste requests in priority order (most urgent first).
 * For each request, assign the nearest available truck that still has
 * enough remaining capacity for that request's load.
 *
 * This is a greedy heuristic: at every step it makes the locally best choice
 * (closest truck with room) without reconsidering earlier assignments. It
 * does NOT guarantee the assignment that serves the greatest total value —
 * see KnapsackDP and the counterexample analysis for a case where this
 * greedy choice is provably worse than the optimal selection.
 *
 * Time Complexity:
 *   Sorting n requests by priority:        O(n log n)
 *   For each request, scanning m trucks:   O(n * m)
 *   Overall:                               O(n log n + n * m)
 *
 * Space Complexity: O(n + m) for the sorted copy, tracking arrays, and results
 */
public class GreedyTruckAssignment {

    /**
     * Result of assigning a single request to a truck (or leaving it unassigned).
     */
    public static class Assignment {
        private final WasteRequest request;
        private final Truck truck;       // null if no suitable truck was found
        private final double distance;   // -1 if unassigned

        public Assignment(WasteRequest request, Truck truck, double distance) {
            this.request = request;
            this.truck = truck;
            this.distance = distance;
        }

        public WasteRequest getRequest() {
            return request;
        }

        public Truck getTruck() {
            return truck;
        }

        public double getDistance() {
            return distance;
        }

        public boolean isAssigned() {
            return truck != null;
        }

        @Override
        public String toString() {
            if (!isAssigned()) {
                return "Assignment{request=" + request.getRequestId() + ", UNASSIGNED (no suitable truck)}";
            }
            return "Assignment{request=" + request.getRequestId()
                    + ", truck=" + truck.getTruckId()
                    + ", distance=" + distance + "}";
        }
    }

    private final DistanceProvider distanceProvider;

    public GreedyTruckAssignment(DistanceProvider distanceProvider) {
        if (distanceProvider == null) {
            throw new IllegalArgumentException("distanceProvider cannot be null");
        }
        this.distanceProvider = distanceProvider;
    }

    /**
     * Assigns each waste request to the nearest available truck with enough
     * remaining capacity, processing requests in priority order.
     *
     * @param requests waste requests to assign, must not be null
     * @param trucks   available truck fleet, must not be null
     * @return one Assignment per request, in the order the requests were processed
     *         (highest priority first); a request with no suitable truck is
     *         returned as an unassigned Assignment rather than being dropped
     */
    public Assignment[] assign(WasteRequest[] requests, Truck[] trucks) {
        if (requests == null) {
            throw new IllegalArgumentException("requests cannot be null");
        }
        if (trucks == null) {
            throw new IllegalArgumentException("trucks cannot be null");
        }

        WasteRequest[] sortedRequests = sortByPriorityDescending(requests);

        double[] remainingCapacity = new double[trucks.length];
        for (int i = 0; i < trucks.length; i++) {
            remainingCapacity[i] = trucks[i].getCapacityKg();
        }

        Assignment[] results = new Assignment[sortedRequests.length];

        for (int i = 0; i < sortedRequests.length; i++) {
            WasteRequest request = sortedRequests[i];
            int bestTruckIndex = -1;
            double bestDistance = Double.MAX_VALUE;

            for (int j = 0; j < trucks.length; j++) {
                Truck candidate = trucks[j];
                boolean hasCapacity = remainingCapacity[j] >= request.getEstimatedVolumeKg();
                if (!candidate.isAvailable() || !hasCapacity) {
                    continue;
                }
                double distance = distanceProvider.getDistance(
                        request.getLocationId(), candidate.getCurrentLocationId());
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestTruckIndex = j;
                }
            }

            if (bestTruckIndex == -1) {
                results[i] = new Assignment(request, null, -1);
            } else {
                remainingCapacity[bestTruckIndex] -= request.getEstimatedVolumeKg();
                results[i] = new Assignment(request, trucks[bestTruckIndex], bestDistance);
            }
        }

        return results;
    }

    /**
     * Returns a new array containing the requests sorted by priority,
     * highest priority first. Does not modify the input array.
     *
     * Implemented locally with insertion sort for now (n is small in typical
     * per-run batches). This can be swapped for the team's shared MergeSort
     * or QuickSort from Part 2 once that module is merged, without changing
     * the rest of this class.
     */
    private WasteRequest[] sortByPriorityDescending(WasteRequest[] requests) {
        WasteRequest[] sorted = new WasteRequest[requests.length];
        System.arraycopy(requests, 0, sorted, 0, requests.length);

        for (int i = 1; i < sorted.length; i++) {
            WasteRequest key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].getPriority() < key.getPriority()) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }
}
