/**
 * VehicleManager  
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */
import java.util.Collection;
import java.util.Date;
public class VehicleManager {

	/** When you add the VehicletManager.java and Vehicle.java to your project, 
	 * you will get a compilation error
	 * because the other classes are not created yet. 
	 * This will be resolved once you create the required classes.
	 **/


	//you can add attributes and additional methods if needed.
	//you can throw an exception if needed


	public Vehicle addVehicle(String vehicleType){  
		//add your code here. Do NOT change the method signature 
		return null; 
	}

	public int noOfAvailableVehicles(String vehicleType) {
		//add your code here. Do NOT change the method signature
		return 0; 
	}


	public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) 
	{
		//add your code here. Do NOT change the method signature 
		return null; 
	}



	public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
		//add your code here. Do NOT change the method signature 
		//return null;
        return false;
	}



	public void returnVehicle(VehicleID vehicleID ,CustomerRecord customerRecord, int mileage) {		
		//add your code here. Do NOT change the method signature
	}	


	public Collection<Vehicle> getVechilesByCustomer (CustomerRecord customerRecord){
		//add your code here. Do NOT change the method signature
		return null;
	}




}
