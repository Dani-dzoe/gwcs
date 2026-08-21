package model;

import java.time.LocalDateTime; 

public class ServiceRequest {

    private String requestId;
    private String location;
    private int priorityLevel; // e.g., 1 = Low, 5 = Critical
    private LocalDateTime requestTime;

    public ServiceRequest(String requestId, String location, int priorityLevel) {
        this.requestId = requestId;
        this.location = location;
        this.priorityLevel = priorityLevel;
        this.requestTime = LocalDateTime.now();
    }

    public int getPriorityLevel() { return priorityLevel; }
    public String getRequestId() { return requestId; }
    public LocalDateTime getRequestTime() { return requestTime; }

    @Override
    public String toString() {
        return "Request{" + "id='" + requestId + "', priority=" + priorityLevel + '}';
    }
    
}
