package model;

import java.sql.Timestamp;

/**
 * Truck - Model class for waste collection trucks
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Represents a vehicle/resource in the waste collection fleet.
 */
public class Truck {
    private int truckId;
    private String truckName;
    private String truckType;
    private double capacityKg;
    private Integer homeLocationId;
    private String availabilityStatus;
    private double fuelLevel;
    private Integer currentLocationId;
    private Integer assignedRouteId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Default constructor
     */
    public Truck() {
        this.fuelLevel = 100.0;
        this.availabilityStatus = "available";
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with required fields
     */
    public Truck(String truckName, String truckType, double capacityKg) {
        this.truckName = truckName;
        this.truckType = truckType;
        this.capacityKg = capacityKg;
        this.fuelLevel = 100.0;
        this.availabilityStatus = "available";
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Full constructor
     */
    public Truck(int truckId, String truckName, String truckType, double capacityKg,
                 Integer homeLocationId, String availabilityStatus, double fuelLevel,
                 Integer currentLocationId, Integer assignedRouteId,
                 Timestamp createdAt, Timestamp updatedAt) {
        this.truckId = truckId;
        this.truckName = truckName;
        this.truckType = truckType;
        this.capacityKg = capacityKg;
        this.homeLocationId = homeLocationId;
        this.availabilityStatus = availabilityStatus;
        this.fuelLevel = fuelLevel;
        this.currentLocationId = currentLocationId;
        this.assignedRouteId = assignedRouteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public double getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(double capacityKg) {
        this.capacityKg = capacityKg;
    }

    public Integer getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(Integer homeLocationId) {
        this.homeLocationId = homeLocationId;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Integer getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(Integer currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public Integer getAssignedRouteId() {
        return assignedRouteId;
    }

    public void setAssignedRouteId(Integer assignedRouteId) {
        this.assignedRouteId = assignedRouteId;
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
     * Check if truck is available for assignment
     */
    public boolean isAvailable() {
        return "available".equals(availabilityStatus) && fuelLevel > 20.0;
    }

    /**
     * Check if truck needs refueling
     */
    public boolean needsRefueling() {
        return fuelLevel < 30.0;
    }

    /**
     * Check if truck can handle a given weight
     */
    public boolean canHandleWeight(double weightKg) {
        return capacityKg >= weightKg;
    }

    /**
     * Get truck type priority (for assignment algorithms)
     */
    public int getTypePriority() {
        switch (truckType.toLowerCase()) {
            case "compactor": return 1;
            case "skip": return 2;
            case "flatbed": return 3;
            case "mini_truck": return 4;
            case "recycling": return 5;
            default: return 10;
        }
    }

    @Override
    public String toString() {
        return "Truck{" +
               "truckId=" + truckId +
               ", name='" + truckName + ''' +
               ", type='" + truckType + ''' +
               ", capacity=" + capacityKg + "kg" +
               ", status='" + availabilityStatus + ''' +
               ", fuel=" + fuelLevel + "%" +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Truck truck = (Truck) obj;
        return truckId == truck.truckId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(truckId);
    }
}
