/**
 * VehicleManager
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */

import java.util.*;

public final class VehicleManager {

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
        Vehicle vehicle;
        if (vehicleType.equalsIgnoreCase("Car")) {
            vehicle = new Car();//其中包括了分配id
            allVehicles.add(vehicle);
        } else if (vehicleType.equalsIgnoreCase("Van")) {
            vehicle = new Van();
            allVehicles.add(vehicle);
        } else
            throw new IllegalArgumentException("Invalid vehicle type!");

        return vehicle;
        //return null;
    }

    //此方法返回指定类型（汽车或货车）中未被租用的车辆数量。
    public int noOfAvailableVehicles(String vehicleType) {
        //add your code here. Do NOT change the method signature
        int total = 0, numOfHired = 0;
        for (Vehicle v : allVehicles)
            total++;
//        hiredVehicles.forEach((key,vehicleSet)->{ //lambda表达式还是不会用
//            vehicleSet.forEach((vehicle->{
//                    numOfHired++;});
//        });
        for (Map.Entry<Integer, HashSet<Vehicle>> entry : hiredVehicles.entrySet()) {
            Integer key = entry.getKey(); // 获取外层HashMap的键（你的Integer类型键，如车辆分组ID/租用天数等）
            HashSet<Vehicle> vehicleSet = entry.getValue(); // 获取内层HashSet<Vehicle>集合
            // 第二层：遍历当前键对应的所有Vehicle对象
            for (Vehicle vehicle : vehicleSet)
                numOfHired++;
        }

        return total - numOfHired;
    }


    //如果尚不存在客户记录，此方法会根据给定信息创建一个客户记录:
    //每个客户的名字、姓氏和出生日期的组合都是唯一的。如果您添加的客户具有相似的现有信息，该方法将抛出异常。
    //该方法将新创建的记录添加到现有客户的数据结构中。成功时，此方法返回CustomerRecord 对象.
    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        //add your code here. Do NOT change the method signature
        CustomerRecord customer = new CustomerRecord(firstName, lastName, dob, hasCommercialLicense);
        return customer;
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
