/**
 * VehicleManager
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */

import java.util.*;

public class VehicleManager {

    /**
     * When you add the VehicleManager.java and Vehicle.java to your project,
     * you will get a compilation error
     * because the other classes are not created yet.
     * This will be resolved once you create the required classes.
     **/


    //you can add attributes and additional methods if needed.
    //you can throw an exception if needed
//    在实现这些方法之前，你需要在该类中定义几个变量（未包含在提供的模板中）以支持其功能。因此，你需要定义变量来：
//    维护一个单一的数据结构 allVehicles，用于存储所有添加到系统中的车辆。
//    维护一个单一的数据结构 customers，用于存储所有现有的客户记录。
//    维护一个单一的数据结构hiredVehicles，其中包含所有现有客户编号及其租用车辆的列表/集合。

    //以下数据结构都不确定, 先放着.
    private ArrayList<Vehicle> allVehicles;
    private ArrayList<CustomerRecord> customers;
    private HashMap<Integer, HashSet<Vehicle>> hiredVehicles;

    public VehicleManager() {
        allVehicles = new ArrayList<>();
        customers = new ArrayList<>();
        hiredVehicles = new HashMap<>();
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public ArrayList<CustomerRecord> getCustomers() {
        return customers;
    }

    public HashMap<Integer, HashSet<Vehicle>> getHiredVehicles() {
        return hiredVehicles;
    }

    //    此方法向系统中添加指定类型（vehicleType）的新车辆，并为其分配一个车辆ID。
    //    可以假设当前里程为0。如果添加的车辆是货车(van)，则可以假设该货车不需要检查。成功时，此方法需要返回车辆对象。
    public Vehicle addVehicle(String vehicleType) {
        //add your code here. Do NOT change the method signature

        VehicleID id=new VehicleID(vehicleType);
        if(vehicleType.equalsIgnoreCase("Car"))
            Car car=new Car();
        //allVehicles.add();

        return null;
    }


    public int noOfAvailableVehicles(String vehicleType) {
        //add your code here. Do NOT change the method signature
        return 0;
    }


    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        //add your code here. Do NOT change the method signature
        return null;
    }


    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
        //add your code here. Do NOT change the method signature
        //return null;
        return false;
    }


    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        //add your code here. Do NOT change the method signature
    }

    //拼写错误，问老师
    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        //add your code here. Do NOT change the method signature
        return null;
    }


}
