/**
 * VehicleManager
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */

import java.util.*;

/**
 * VehicleManager - Manages vehicles and customer records in the rental system.
 * Implements a singleton pattern to ensure a single instance.
 *
 * @author Ziyue Ren
 * @see Vehicle
 * @see CustomerRecord
 * @see VehicleID
 */
public final class VehicleManager {
    private final List<Vehicle> allVehicles; // List of all vehicles in the system.
    private final List<CustomerRecord> customers;    //List of all customers in the system.
    // Map of hired vehicles, keyed by customer number.
    // Each customer can have a set of vehicles they have rented.
    private final Map<Integer, Set<Vehicle>> hiredVehicles;
    private static final VehicleManager INSTANCE = new VehicleManager(); // Singleton instance of VehicleManager.

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the lists and map for managing vehicles and customers.
     */
    private VehicleManager() {
        allVehicles = new ArrayList<>();
        customers = new ArrayList<>();
        hiredVehicles = new HashMap<>();
    }

    /**
     * Returns the singleton instance of VehicleManager.
     *
     * @return The singleton instance.
     */
    public static VehicleManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns an unmodifiable list of all vehicles in the system.
     *
     * @return A list of all vehicles.
     */
    public List<Vehicle> getAllVehicles() {
        return Collections.unmodifiableList(allVehicles);
    }

    /**
     * Returns an unmodifiable list of all customers in the system.
     *
     * @return A list of all customers.
     */
    public List<CustomerRecord> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    /**
     * Returns a defensive unmodifiable copy of the hiredVehicles map.
     * The returned map and its associated sets cannot be modified externally,
     * preserving encapsulation of the internal state.
     *
     * @return A map of hired vehicles.
     */
    public Map<Integer, Set<Vehicle>> getHiredVehicles() {
        Map<Integer, Set<Vehicle>> copyMap = new HashMap<>();
        for (Map.Entry<Integer, Set<Vehicle>> entry : hiredVehicles.entrySet()) {
            copyMap.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
        }
        return Collections.unmodifiableMap(copyMap);
    }

    /**
     * Adds a new vehicle to the system.
     *
     * @param vehicleType The type of vehicle to add ("Car" or "Van").
     * @return The newly created Vehicle object.
     * @throws IllegalArgumentException if the vehicle type is invalid.
     */
    public Vehicle addVehicle(String vehicleType) {
        Vehicle vehicle;
        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty!");
        }
        if (vehicleType.equalsIgnoreCase("Car")) {
            vehicle = new Car();
            allVehicles.add(vehicle);
        } else if (vehicleType.equalsIgnoreCase("Van")) {
            vehicle = new Van();
            allVehicles.add(vehicle);
        } else
            throw new IllegalArgumentException("Invalid vehicle type!");

        return vehicle;
    }

    /**
     * Returns the number of vehicles of the specified type (a car or a van) that are Not hired
     *
     * @param vehicleType The type of vehicle ("Car" or "Van").
     * @return The number of available vehicles of the specified type.
     */
    public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;
        for (Vehicle v : allVehicles) {
            if (vehicleType.equalsIgnoreCase(v.getVehicleType()) && !v.isHired())
                count++;
        }
        return count;
    }

    /**
     * Adds a new customer record to the system.
     * Ensures that the combination of first name, last name, and date of birth is unique.
     *
     * @param firstName            The first name of the customer.
     * @param lastName             The last name of the customer.
     * @param dob                  The date of birth of the customer.
     * @param hasCommercialLicense Whether the customer has a commercial driving license.
     * @return The newly created CustomerRecord object.
     * @throws IllegalArgumentException if a duplicate customer is detected.
     */
    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        CustomerRecord customer = CustomerRecord.getInstance(firstName, lastName, dob, hasCommercialLicense);
        if (customers.contains(customer))
            throw new IllegalArgumentException("Duplicate customer!");
        customers.add(customer);
        return customer;
    }

    /**
     * Checks if the given vehicle type is a car.
     *
     * @param type The vehicle type to check.
     * @return True if the type is "Car", false otherwise.
     */
    public boolean isCar(String type) {
        return type.equalsIgnoreCase("Car");
    }

    /**
     * Checks if the given vehicle type is a van.
     *
     * @param type The vehicle type to check.
     * @return True if the type is "Van", false otherwise.
     */
    public boolean isVan(String type) {
        return type.equalsIgnoreCase("Van");
    }

    /**
     * Attempts to hire a vehicle for a customer.
     * Checks customer eligibility and vehicle availability.
     *
     * @param customerRecord The customer requesting the hire.
     * @param vehicleType    The type of vehicle to hire ("Car" or "Van").
     * @param duration       The duration of the hire in days.
     * @return True if the hire was successful, false otherwise.
     */
    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
        int customerNum = customerRecord.getCustomerNum();
        hiredVehicles.putIfAbsent(customerNum, new HashSet<>()); // Initialize rental record if absent.
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerNum);

        // Check if the customer has reached the rental limit.
        if (vehicleSet.size() >= 3) {
            System.out.println("The number of vehicle rentals exceeds the limit of 3.");
            return false;
        }

        // Check age and license requirements for the vehicle type.
        if (isCar(vehicleType) && customerRecord.getAge() < 18) {
            System.out.println("Hire failed: Customer must be at least 18 to hire a car.");
            return false;
        }
        if (isVan(vehicleType) && (customerRecord.getAge() < 23 || (!customerRecord.hasCommercialLicense()))) {
            System.out.println("Hire failed: Customer must be at least 23 and have a commercial license to hire a van.");
            return false;
        }

        // Find an available vehicle that meets the criteria.
        for (Vehicle v : allVehicles) {
            if (!v.getVehicleType().equalsIgnoreCase(vehicleType)) // Skip mismatched types.
                continue;
            if (v.isHired() || v.getCurrentMileage() >= v.getDistanceRequirement()) // Skip unavailable vehicles.
                continue;
            if (v instanceof Van van && van.needCheck()) // Skip vans requiring check.
                continue;

            // Assign the vehicle to the customer.
            v.setHired(true);
            if (v instanceof Van van && duration >= 10) // Mark vans for inspection if hired for 10+ days.
                van.setCheck(true);
            vehicleSet.add(v);
            System.out.println("Hire successful: Vehicle " + v.getVehicleID() + " rented to " + customerRecord.getName());
            return true;
        }

        System.out.println("Hire failed: No available " + vehicleType + " found at the moment.");
        return false; // No suitable vehicle found.
    }

    /**
     * Returns a vehicle and updates its status.
     * Removes the vehicle from the customer's hired list, updates mileage, and performs maintenance if needed.
     *
     * @param vehicleID      The ID of the vehicle being returned.
     * @param customerRecord The customer returning the vehicle.
     * @param mileage        The mileage driven during the hire.
     */
    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerRecord.getCustomerNum());
        if (vehicleSet == null) return; // No vehicles hired by the customer.

        Vehicle target = null;
        Iterator<Vehicle> it = vehicleSet.iterator();
        while (it.hasNext()) {
            Vehicle v = it.next();
            if (v.getVehicleID().equals(vehicleID)) { // Find the target vehicle.
                it.remove(); // Remove the vehicle from the customer's hired list.
                target = v;
                break;
            }
        }

        if (target == null) return; // Vehicle not found.

        // Remove the customer from the map if no vehicles are left.
        if (vehicleSet.isEmpty())
            hiredVehicles.remove(customerRecord.getCustomerNum());

        // Update the vehicle's status and perform maintenance if required.
        target.setHired(false);
        target.setCurrentMileage(mileage + target.getCurrentMileage());
        if (target.performServiceIfDue())
            System.out.println("The vehicle has been serviced.");
        if (target instanceof Van van && van.needCheck()) {
            van.setCheck(false);
            System.out.println("The van has been checked.");
        }
    }

    /**
     * Returns a collection of vehicles currently hired by a specific customer.
     *
     * @param customerRecord The customer whose hired vehicles are requested.
     * @return An unmodifiable collection of vehicles hired by the customer.
     * or an empty collection if none exist
     */
    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerRecord.getCustomerNum());
        if (vehicleSet == null)
            return Collections.emptySet();
        return Collections.unmodifiableSet(vehicleSet);
    }
}
