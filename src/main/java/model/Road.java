package model;

import java.sql.Timestamp;

/**
 * Road - Model class for roads between locations
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Represents a weighted edge in the road network graph.
 */
public class Road {
    private int roadId;
    private int fromLocationId;
    private int toLocationId;
    private double distanceKm;
    private int travelTimeMinutes;
    private double roadConditionWeight;
    private boolean isOneWay;
    private Timestamp createdAt;

    /**
     * Default constructor
     */
    public Road() {
        this.roadConditionWeight = 1.0;
        this.isOneWay = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with required fields
     */
    public Road(int fromLocationId, int toLocationId, double distanceKm, 
                int travelTimeMinutes) {
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distanceKm = distanceKm;
        this.travelTimeMinutes = travelTimeMinutes;
        this.roadConditionWeight = 1.0;
        this.isOneWay = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Full constructor
     */
    public Road(int roadId, int fromLocationId, int toLocationId, double distanceKm,
                int travelTimeMinutes, double roadConditionWeight, boolean isOneWay,
                Timestamp createdAt) {
        this.roadId = roadId;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distanceKm = distanceKm;
        this.travelTimeMinutes = travelTimeMinutes;
        this.roadConditionWeight = roadConditionWeight;
        this.isOneWay = isOneWay;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getFromLocationId() {
        return fromLocationId;
    }

    public void setFromLocationId(int fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    public int getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(int toLocationId) {
        this.toLocationId = toLocationId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public void setTravelTimeMinutes(int travelTimeMinutes) {
        this.travelTimeMinutes = travelTimeMinutes;
    }

    public double getRoadConditionWeight() {
        return roadConditionWeight;
    }

    public void setRoadConditionWeight(double roadConditionWeight) {
        this.roadConditionWeight = roadConditionWeight;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get weighted distance (considering road condition)
     */
    public double getWeightedDistance() {
        return distanceKm * roadConditionWeight;
    }

    /**
     * Get weighted travel time (considering road condition)
     */
    public double getWeightedTravelTime() {
        return travelTimeMinutes * roadConditionWeight;
    }

    @Override
    public String toString() {
        return "Road{" +
               "roadId=" + roadId +
               ", from=" + fromLocationId +
               ", to=" + toLocationId +
               ", distance=" + distanceKm + "km" +
               ", time=" + travelTimeMinutes + "min" +
               ", weight=" + roadConditionWeight +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road road = (Road) obj;
        return roadId == road.roadId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(roadId);
    }
}
