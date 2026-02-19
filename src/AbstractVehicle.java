/**
 * AbstractVehicle - Base class for all vehicle types.
 * Implements common functionality for vehicles.
 */
public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    private final String vehicleType;
    private final int distanceRequirement;
    private int currentMileage;
    private boolean isHired;

    /**
     * Constructor for AbstractVehicle.
     * Initializes the vehicle type, ID, and other properties.
     * The ID is generated using a factory method to ensure uniqueness.
     * The distance requirement is set based on the vehicle type.
     * Cars require service every 10,000 miles, while vans require service every 5,000 miles.
     *
     * @param vehicleType The type of the vehicle (e.g., "Car", "Van").
     */
    public AbstractVehicle(String vehicleType) {
        this.vehicleType = vehicleType;
        id = VehicleID.getInstance(vehicleType); // Generate a unique ID using the factory method.
        currentMileage = 0; // Initialize mileage to 0.
        isHired = false; // Default to not hired.
        distanceRequirement = vehicleType.equalsIgnoreCase(VehicleID.CAR) ? 10000 : 5000; // Set service distance.
    }

    /**
     * Returns the unique ID of the vehicle.
     *
     * @return VehicleID object.
     */
    @Override
    public VehicleID getVehicleID() {
        return id;
    }

    /**
     * Returns the type of the vehicle.
     *
     * @return A string representing the vehicle type.
     */
    @Override
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Checks whether the vehicle is currently hired or not.
     *
     * @return True if the vehicle is hired, false otherwise.
     */
    @Override
    public boolean isHired() {
        return isHired;
    }

    /**
     * Returns the distance requirement for servicing the vehicle.
     *
     * @return The distance requirement.
     */
    @Override
    public int getDistanceRequirement() {
        return distanceRequirement;
    }

    /**
     * Returns the current mileage accumulated since the last service was carried out.
     *
     * @return The current mileage.
     */
    @Override
    public int getCurrentMileage() {
        return currentMileage;
    }

    /**
     * Sets the current mileage of the vehicle.
     * Throws an exception if the mileage is negative.
     *
     * @param mileage The new mileage value.
     */
    @Override
    public void setCurrentMileage(int mileage) {
        if (mileage < 0) // Ensure mileage is non-negative.
            throw new IllegalArgumentException("Mileage cannot be negative.");
        this.currentMileage = mileage; // Update the mileage.
    }

    /**
     * Sets the hired status of the vehicle.
     *
     * @param flag True to mark the vehicle as hired, false otherwise.
     */
    @Override
    public void setHired(boolean flag) {
        this.isHired = flag;
    }

    /**
     * Performs a service on the vehicle if the mileage has reached
     * or exceeded the distance requirement.
     * Resets the mileage to 0 if the service is performed.
     *
     * @return True if the service was performed, false otherwise.
     */
    @Override
    public boolean performServiceIfDue() {
        if (currentMileage >= distanceRequirement) { // Check if service is due.
            currentMileage = 0; // Reset mileage after service.
            return true; // Service performed.
        } else
            return false; // Service not required.
    }

    /**
     * Returns a string representation of the vehicle.
     *
     * @return A string containing the vehicle type and ID.
     */
    @Override
    public String toString() {
        return vehicleType + " " + id.toString();
    }
}
