package model;

import java.sql.Timestamp;

/**
 * Location - Model class for waste collection locations
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Represents a location node in the waste collection network.
 */
public class Location {
    private int locationId;
    private String name;
    private String area;
    private String locationType;
    private double latitude;
    private double longitude;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Default constructor
     */
    public Location() {
        this.isActive = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with required fields
     */
    public Location(String name, String area, String locationType, 
                    double latitude, double longitude) {
        this.name = name;
        this.area = area;
        this.locationType = locationType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActive = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Full constructor
     */
    public Location(int locationId, String name, String area, String locationType,
                    double latitude, double longitude, boolean isActive,
                    Timestamp createdAt, Timestamp updatedAt) {
        this.locationId = locationId;
        this.name = name;
        this.area = area;
        this.locationType = locationType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
     * Get coordinates as string
     */
    public String getCoordinates() {
        return latitude + "," + longitude;
    }

    /**
     * Calculate distance to another location (Haversine formula approximation)
     */
    public double distanceTo(Location other) {
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double deltaLat = Math.toRadians(other.latitude - this.latitude);
        double deltaLon = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return 6371 * c; // Distance in km
    }

    @Override
    public String toString() {
        return "Location{" +
               "locationId=" + locationId +
               ", name='" + name + ''' +
               ", area='" + area + ''' +
               ", type='" + locationType + ''' +
               ", coordinates=(" + latitude + ", " + longitude + ")" +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return locationId == location.locationId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(locationId);
    }
}
