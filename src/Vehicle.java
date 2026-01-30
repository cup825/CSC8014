/**
 * Vehicle - interface to a vehicle.
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */
public interface Vehicle {
	//DO NOT remove or modify the signature of any existing method.
	//You can add additional methods (e.g. setter methods) if your solution requires that

	/**
	 * Returns the unique ID of the vehicle.
	 * All Vehicles must have an ID
	 * @return the VehicleID object
	 */
	VehicleID getVehicleID();


	/**
	 * Returns the Vehicle type.
	 * a Vehicle can be either a car or a van
	 * @return a string representing the vehicle type ("car" or "van")
	 */
	String getVehicleType();


	/**
	 * Indicates whether the vehicle is currently hired or not. 
	 * @return true if the Vehicle is hired; false otherwise
	 */
	boolean isHired();


	/**
	 * Returns the distance the vehicle must travel before it needs a service
	 * @return an integer (the service distance requirement)
	 */
	public int getDistanceRequirement() ;


	/**
	 * Returns the distance a vehicle has traveled since the last service
	 * @return an integer (the current mileage since the last service)
	 */
	public int getCurrentMileage(); 

	/**
	 * set the current mileage of the vehicle
	 */
	public void setCurrentMileage(int mileage); 


	/**
	 * Checks whether the vehicle requires a service and performs the service if due.
	 * If a service is required, the current mileage is reset.
	 * @return true if the Vehicle required a service and it was performed; false otherwise
	 */
	public boolean performServiceIfDue();


}


