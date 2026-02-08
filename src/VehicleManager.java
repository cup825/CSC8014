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
    static ArrayList<Vehicle> allVehicles;
    private ArrayList<CustomerRecord> customers;
    public static HashMap<Integer, HashSet<Vehicle>> hiredVehicles;

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

    //✔此方法向系统中添加指定类型（vehicleType）的新车辆，并为其分配一个车辆ID。
    //可以假设当前里程为0。如果添加的车辆是货车(van)，则可以假设该货车不需要检查。成功时，此方法需要返回车辆对象。
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

    //✔此方法返回指定类型（汽车或货车）中未被租用的车辆数量。
    public int noOfAvailableVehicles(String vehicleType) {
        //add your code here. Do NOT change the method signature
        int count = 0;
        for (Vehicle v : allVehicles) {
            if (vehicleType.equalsIgnoreCase(v.getVehicleType()) && !v.isHired())
                count++;
        }
        return count;
    }


    //✔如果尚不存在客户记录，此方法会根据给定信息创建一个客户记录:
    //每个客户的名字、姓氏和出生日期的组合都是唯一的。如果您添加的客户具有相似的现有信息，该方法将抛出异常。
    //该方法将新创建的记录添加到现有客户的数据结构中。成功时，此方法返回CustomerRecord 对象.
    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        //add your code here. Do NOT change the method signature
        //judge whether customer exist
        CustomerRecord customer = new CustomerRecord(firstName, lastName, dob, hasCommercialLicense);
        if (customers.contains(customer))//contains会调用其equals，所以要重写
            customers.add(customer);
        else
            throw new RuntimeException("Duplicate customer!");//待改

        return customer;
    }


    //此方法在给定客户记录信息和所需车辆类型(轿车或货车)的情况下:
    //确定客户是否有资格租用指定类型的车辆(见下文规则)
    //如果客户可以租用车辆，且有可供租用的车辆，系统会从可用车辆中为他们提供指定类型的车辆。
    //如果车辆是面包车，且该面包车将被租用 10 天或更长时间(即持续时间>=10≥10，其中持续时间指车辆将被租用的天数)，
    // 则该面包车的状态将变为需要检查。

    // 如果客户无法租用车辆(或者客户理论上可以租用车辆但没有可用车辆)，则该方法返回false，
    // 并打印出相应的失败提示(打印客户无法租用该车辆的原因)。

    // 然后，该方法将客户编号与车辆相关联(以便公司记录已出租的车辆以及租车人信息)。
    // 它还会返回“true” 并打印出一条信息，说明客户正在租用的车辆。判断车辆是否可以租用的规则如下:
    //一位客户最多可租用三辆任何类型的车辆(例如，一位客户可以租用2辆汽车和1辆货车)。
    //·车辆当前里程不得超过保养所需里程。
    //·要租用汽车，客户必须年满18岁。
    //要租用货车，客户必须拥有商业驾照且年龄至少为23岁。此外，该货车当前不得需要进行检查。
    //如果车辆被租用，不应将其条目从allVehicles数据结构中移除。只需将车辆状态从“可用”改为“己租用”即可。

    // 1. ✔检查客户已租车辆数 <= 3
// 2. 找出 allVehicles 中指定类型且可用的车辆（里程 <= distanceRequirement）
// 3. 检查客户资格：Car >=18岁；Van >=23岁且有商业驾照且不需检查
// 4. 如果资格不符或无可用车辆 → 打印失败原因，返回 false
// 5. 从可用车辆中选择一辆
// 6. 标记车辆已租用；如果 Van 且 duration >=10 → 设置为需检查
// 7. 更新 hiredVehicles，将客户编号与车辆关联
// 8. 打印租车信息，返回 true
    public void printReason1() {
        System.out.println("The customer do not have qualification to hire.");
    }

    public void printReason2() {
        System.out.println("No available vehicle to hire.");
    }

    public boolean isCar(String type) {
        return type.equalsIgnoreCase("Car");
    }

    public boolean isVan(String type) {
        return type.equalsIgnoreCase("Van");
    }

    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
        //add your code here. Do NOT change the method signature
        int customerNum = customerRecord.getCustomerNum();
        hiredVehicles.putIfAbsent(customerNum, new HashSet<>());//如果没有租车记录
        HashSet<Vehicle> vehicleSet = hiredVehicles.get(customerNum);//得到value
        //打印资格不符合
        //①租车数量>3的排除
        if (vehicleSet.size() >= 3) {
            printReason1();
            return false;
        }

        //②年龄不符的排除
        //int age = 2026 - customerRecord.getDateOfBirth().getYear();//待改正，用Calendar类
        if (isCar(vehicleType) && customerRecord.getAge() < 18) {
            printReason1();
            return false;
        }
        if (isVan(vehicleType) && (customerRecord.getAge() < 23 || (!customerRecord.hasCommercialLicense()))) {
            printReason1();
            return false;
        }

        //3.车本身
        for (Vehicle v : allVehicles) {
            if (!v.getVehicleType().equalsIgnoreCase(vehicleType))//类型不匹配
                continue;
            if (v.isHired() || v.getCurrentMileage() >= v.getDistanceRequirement())//被租或需要维修
                continue;
            if (v instanceof Van van && van.needCheck())//是否在检查
                continue;
            if (v instanceof Van van && duration >= 10)//是否即将被检查
                van.setCheck(true);
            v.setHired(true);
            vehicleSet.add(v);
            return true;
        }
        printReason2();
        return false;//所有车辆中没有符合条件的
    }


    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        //add your code here. Do NOT change the method signature
    }

    //拼写错误，就这吧
    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        //add your code here. Do NOT change the method signature
        return null;
    }


}
