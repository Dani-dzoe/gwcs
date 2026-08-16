package model;

/**
 * Represents a single waste collection request in the system.
 *
 * This is a plain data object (no algorithm logic, no SQL). It is shared by
 * the scheduling engine (Part 2), the graph/routing engine (Part 3), and the
 * optimization algorithms (Part 4: GreedyTruckAssignment, KnapsackDP).
 */
public class WasteRequest {

    private final int requestId;
    private final String locationId;
    private final String wasteType;
    private final double binFillPercentage; // 0.0 - 100.0
    private final int priority;             // 1 (low) .. 5 (critical/emergency)
    private final double estimatedVolumeKg; // used as the "weight" in KnapsackDP
    private final long timeReportedEpochSeconds;
    private String status;                  // e.g. PENDING, ASSIGNED, COLLECTED

    /**
     * @param requestId unique identifier for this request
     * @param locationId id of the location this request originates from
     * @param wasteType category of waste (e.g. "GENERAL", "ORGANIC", "RECYCLABLE")
     * @param binFillPercentage how full the bin is, 0-100
     * @param priority urgency level, 1 (low) to 5 (critical)
     * @param estimatedVolumeKg estimated load this request will add to a truck
     * @param timeReportedEpochSeconds when the request was reported
     */
    public WasteRequest(int requestId, String locationId, String wasteType,
                         double binFillPercentage, int priority,
                         double estimatedVolumeKg, long timeReportedEpochSeconds) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5, got: " + priority);
        }
        if (estimatedVolumeKg <= 0) {
            throw new IllegalArgumentException("Estimated volume must be positive, got: " + estimatedVolumeKg);
        }
        this.requestId = requestId;
        this.locationId = locationId;
        this.wasteType = wasteType;
        this.binFillPercentage = binFillPercentage;
        this.priority = priority;
        this.estimatedVolumeKg = estimatedVolumeKg;
        this.timeReportedEpochSeconds = timeReportedEpochSeconds;
        this.status = "PENDING";
    }

    public int getRequestId() {
        return requestId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public double getBinFillPercentage() {
        return binFillPercentage;
    }

    public int getPriority() {
        return priority;
    }

    public double getEstimatedVolumeKg() {
        return estimatedVolumeKg;
    }

    public long getTimeReportedEpochSeconds() {
        return timeReportedEpochSeconds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = status;
    }

    /**
     * Urgency score used as the "value" in KnapsackDP and as the sort key in
     * GreedyTruckAssignment. Placeholder formula — agree the real weighting
     * with the group (e.g. should bin fill % matter as much as priority?)
     * before the final report.
     *
     * @return an integer urgency score, higher means more valuable to service now
     */
    public int getUrgencyScore() {
        return (priority * 100) + (int) Math.round(binFillPercentage);
    }

    @Override
    public String toString() {
        return "WasteRequest{id=" + requestId + ", location=" + locationId
                + ", priority=" + priority + ", fill=" + binFillPercentage
                + "%, volume=" + estimatedVolumeKg + "kg, status=" + status + "}";
    }
}
