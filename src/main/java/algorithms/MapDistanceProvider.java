package algorithms;

/**
 * Temporary DistanceProvider backed by a plain 2D distance matrix.
 *
 * This exists so GreedyTruckAssignment can run and be tested before the
 * Graph module (Member 9) and Dijkstra (Member 11) are ready. Replace with a
 * Graph/Dijkstra-backed implementation once those are merged; the algorithm
 * code itself will not need to change since it only depends on the
 * DistanceProvider interface.
 *
 * Not an assessed data structure — this is a small integration convenience
 * class, so a plain 2D array is acceptable here.
 */
public class MapDistanceProvider implements DistanceProvider {

    private final String[] locationIds;
    private final double[][] distances;

    /**
     * @param locationIds ordered array of location ids matching the rows/columns of distances
     * @param distances   square matrix where distances[i][j] is the distance from
     *                    locationIds[i] to locationIds[j]
     */
    public MapDistanceProvider(String[] locationIds, double[][] distances) {
        if (locationIds == null || distances == null) {
            throw new IllegalArgumentException("locationIds and distances cannot be null");
        }
        if (distances.length != locationIds.length) {
            throw new IllegalArgumentException("distances matrix size must match locationIds length");
        }
        this.locationIds = locationIds;
        this.distances = distances;
    }

    @Override
    public double getDistance(String fromLocationId, String toLocationId) {
        int fromIndex = indexOf(fromLocationId);
        int toIndex = indexOf(toLocationId);
        if (fromIndex == -1) {
            throw new IllegalArgumentException("Unknown location id: " + fromLocationId);
        }
        if (toIndex == -1) {
            throw new IllegalArgumentException("Unknown location id: " + toLocationId);
        }
        return distances[fromIndex][toIndex];
    }

    private int indexOf(String locationId) {
        for (int i = 0; i < locationIds.length; i++) {
            if (locationIds[i].equals(locationId)) {
                return i;
            }
        }
        return -1;
    }
}
