package model;

/**
 * Represents a waste collection truck.
 *
 * Plain data object, shared across the truck-scheduling and optimization
 * modules. No algorithm logic lives here.
 */
public class Truck {

    private final int truckId;
    private final String driverName;
    private final double capacityKg;
    private String currentLocationId;
    private double fuelLevel; // 0.0 - 100.0
    private boolean available;

    public Truck(int truckId, String driverName, double capacityKg,
                  String currentLocationId, double fuelLevel, boolean available) {
        if (capacityKg <= 0) {
            throw new IllegalArgumentException("Truck capacity must be positive, got: " + capacityKg);
        }
        this.truckId = truckId;
        this.driverName = driverName;
        this.capacityKg = capacityKg;
        this.currentLocationId = currentLocationId;
        this.fuelLevel = fuelLevel;
        this.available = available;
    }

    public int getTruckId() {
        return truckId;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getCapacityKg() {
        return capacityKg;
    }

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Truck{id=" + truckId + ", driver=" + driverName
                + ", capacity=" + capacityKg + "kg, location=" + currentLocationId
                + ", available=" + available + "}";
    }
}
