package algorithms;

/**
 * Supplies the travel distance (or time) between two locations.
 *
 * GreedyTruckAssignment depends on this interface rather than directly on
 * Member 9's Graph class or Member 11's Dijkstra implementation, since those
 * are not built yet and this keeps GreedyTruckAssignment testable in
 * isolation. Once the Graph module is ready, plug in a real implementation
 * that calls graph.getWeight(...) or runs Dijkstra between the two ids.
 */
public interface DistanceProvider {

    /**
     * @param fromLocationId id of the origin location
     * @param toLocationId   id of the destination location
     * @return the distance (or travel time) between the two locations;
     *         must be a consistent, non-negative unit
     */
    double getDistance(String fromLocationId, String toLocationId);
}
