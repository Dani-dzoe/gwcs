package model;

import java.sql.Timestamp;

/**
 * WasteRequest - Model class for waste collection requests
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Represents a service request in the waste collection system.
 */
public class WasteRequest {
    private int requestId;
    private int sourceLocationId;
    private int destinationLocationId;
    private String category;
    private int urgencyLevel;
    private double weightEstimateKg;
    private double volumeEstimateM3;
    private String timeSubmitted;
    private String deadline;
    private String status;
    private Integer assignedTruckId;
    private double priorityScore;
    private String notes;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Default constructor
     */
    public WasteRequest() {
        this.urgencyLevel = 1;
        this.status = "pending";
        this.priorityScore = 0.0;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with required fields
     */
    public WasteRequest(int sourceLocationId, int destinationLocationId, 
                        String category, int urgencyLevel, String timeSubmitted) {
        this.sourceLocationId = sourceLocationId;
        this.destinationLocationId = destinationLocationId;
        this.category = category;
        this.urgencyLevel = urgencyLevel;
        this.timeSubmitted = timeSubmitted;
        this.status = "pending";
        this.priorityScore = calculatePriorityScore();
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Full constructor
     */
    public WasteRequest(int requestId, int sourceLocationId, int destinationLocationId,
                        String category, int urgencyLevel, double weightEstimateKg,
                        double volumeEstimateM3, String timeSubmitted, String deadline,
                        String status, Integer assignedTruckId, double priorityScore,
                        String notes, Timestamp createdAt, Timestamp updatedAt) {
        this.requestId = requestId;
        this.sourceLocationId = sourceLocationId;
        this.destinationLocationId = destinationLocationId;
        this.category = category;
        this.urgencyLevel = urgencyLevel;
        this.weightEstimateKg = weightEstimateKg;
        this.volumeEstimateM3 = volumeEstimateM3;
        this.timeSubmitted = timeSubmitted;
        this.deadline = deadline;
        this.status = status;
        this.assignedTruckId = assignedTruckId;
        this.priorityScore = priorityScore;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getSourceLocationId() {
        return sourceLocationId;
    }

    public void setSourceLocationId(int sourceLocationId) {
        this.sourceLocationId = sourceLocationId;
    }

    public int getDestinationLocationId() {
        return destinationLocationId;
    }

    public void setDestinationLocationId(int destinationLocationId) {
        this.destinationLocationId = destinationLocationId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
        this.priorityScore = calculatePriorityScore();
    }

    public double getWeightEstimateKg() {
        return weightEstimateKg;
    }

    public void setWeightEstimateKg(double weightEstimateKg) {
        this.weightEstimateKg = weightEstimateKg;
    }

    public double getVolumeEstimateM3() {
        return volumeEstimateM3;
    }

    public void setVolumeEstimateM3(double volumeEstimateM3) {
        this.volumeEstimateM3 = volumeEstimateM3;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(String timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAssignedTruckId() {
        return assignedTruckId;
    }

    public void setAssignedTruckId(Integer assignedTruckId) {
        this.assignedTruckId = assignedTruckId;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(double priorityScore) {
        this.priorityScore = priorityScore;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Calculate priority score based on urgency and other factors
     */
    private double calculatePriorityScore() {
        double baseScore = urgencyLevel;

        // Add weight factor
        if (weightEstimateKg > 300) {
            baseScore += 0.5;
        }

        // Add deadline urgency
        if (deadline != null && !deadline.isEmpty()) {
            try {
                // Simple deadline check - could be enhanced
                baseScore += 0.3;
            } catch (Exception e) {
                // Ignore date parsing errors
            }
        }

        return baseScore;
    }

    /**
     * Check if request is urgent
     */
    public boolean isUrgent() {
        return urgencyLevel >= 4;
    }

    /**
     * Check if request is pending
     */
    public boolean isPending() {
        return "pending".equals(status);
    }

    @Override
    public String toString() {
        return "WasteRequest{" +
               "requestId=" + requestId +
               ", from=" + sourceLocationId +
               ", to=" + destinationLocationId +
               ", category='" + category + '\'' +
               ", urgency=" + urgencyLevel +
               ", status='" + status + '\'' +
               ", priority=" + priorityScore +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WasteRequest request = (WasteRequest) obj;
        return requestId == request.requestId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(requestId);
    }
}
