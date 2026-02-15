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
    private final List<Vehicle> allVehicles;
    private final List<CustomerRecord> customers;
    private final Map<Integer, Set<Vehicle>> hiredVehicles;

    //Has a static instance of itself 拥有自身静态实例
    private static final VehicleManager INSTANCE = new VehicleManager();

    //VehicleManager只有一个，是单例
    private VehicleManager() {
        allVehicles = new ArrayList<>();
        customers = new ArrayList<>();
        hiredVehicles = new HashMap<>();
    }

    public static VehicleManager getInstance() {
        return INSTANCE;
    }

    //返回值修改为List
    public List<Vehicle> getAllVehicles() {
        return Collections.unmodifiableList(allVehicles);
    }

    public List<CustomerRecord> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    //返回值修改为Map
    public Map<Integer, Set<Vehicle>> getHiredVehicles() {
//        return Collections.unmodifiableMap(hiredVehicles);
//        先让set不可修改
        Map<Integer, Set<Vehicle>> copyMap = new HashMap<>();
        for (Map.Entry<Integer, Set<Vehicle>> entry : hiredVehicles.entrySet()) {
            // 关键：把里面的 Set 也包装成不可修改
            copyMap.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
        }
        // 最后把外层的 Map 也包装
        return Collections.unmodifiableMap(copyMap);
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
        //改用静态工厂方法:两行都会检查是否重复，但用的不同的数据结构，意义也不同
        CustomerRecord customer = CustomerRecord.getInstance(firstName, lastName, dob, hasCommercialLicense);
        if (customers.contains(customer))//contains会调用其equals，所以要重写
            throw new IllegalArgumentException("Duplicate customer!");//待改
        customers.add(customer);
        return customer;
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
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerNum);//得到value
        //打印资格不符合
        //①租车数量>3的排除
        if (vehicleSet.size() >= 3) {
            System.out.println("The number of vehicle rentals exceeds the limit of 3.");
            return false;
        }

        //②年龄不符的排除
        //int age = 2026 - customerRecord.getDateOfBirth().getYear();//待改正，用Calendar类
        if (isCar(vehicleType) && customerRecord.getAge() < 18) {
            System.out.println("Customer must be at least 18 to hire a car.");
            return false;
        }
        if (isVan(vehicleType) && (customerRecord.getAge() < 23 || (!customerRecord.hasCommercialLicense()))) {
            System.out.println("Customer must be at least 23 and has commercial license to hire a van.");
            return false;
        }

        //3.车本身
        for (Vehicle v : allVehicles) {
            if (!v.getVehicleType().equalsIgnoreCase(vehicleType)) //类型不匹配
                continue;
            if (v.isHired() || v.getCurrentMileage() >= v.getDistanceRequirement())//被租或需要维修
                continue;
            if (v instanceof Van van && van.needCheck())//是否在检查
                continue;
            v.setHired(true);
            if (v instanceof Van van && duration >= 10)//是否即将被检查
                van.setCheck(true);
            vehicleSet.add(v);
            System.out.println("Hire successful:Vehicle " + v.getVehicleID() + " rented to " + customerRecord.getName());
            return true;
        }
        System.out.println("No available " + vehicleType + " found at the moment.");
        return false;//所有车辆中没有符合条件的
    }

    //此方法会在车辆行驶达到指定里程数后，终止与给定客户记录相关联的、针对该车辆的租赁合同。
    // 之后，该车辆可供其他人租赁。此方法的执行步骤如下:
    //从hiredVehicles 数据结构中的客户条目里移除已归还的车辆。如果移除后该客户没有其他在租车辆，就从数据结构中删除整个客户条目。
    //更新车辆状态，表明其不再被租用。
    //通过添加指定的里程数来更新车辆的当前里程。
    //若车辆需要保养，则对其进行保养。
    //如果车辆是货车，必要时进行检查。
    //终止不存在的合同无效。
    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        //add your code here. Do NOT change the method signature
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerRecord.getCustomerNum());
        if (vehicleSet == null) return;
        Vehicle target = null;
        //迭代器遍历集合，并安全移除
        Iterator<Vehicle> it = vehicleSet.iterator();
        while (it.hasNext()) {
            Vehicle v = it.next();
            if (v.getVehicleID().equals(vehicleID)) {
                it.remove();
                target = v;
                break;
            }

        }
        if (target == null) return;
        //如果移除后，用户没租车了，直接移除整个用户的空Set
        if (vehicleSet.isEmpty())
            //hiredVehicles.remove(vehicleSet);
            hiredVehicles.remove(customerRecord.getCustomerNum());//移除key，不是value

        //更新车辆状态，并完成检查和维修
        target.setHired(false);
        target.setCurrentMileage(mileage + target.getCurrentMileage());
        if (target.performServiceIfDue())
            System.out.println("The vehicle has been serviced.");
        if (target instanceof Van van && van.needCheck()) {
            van.setCheck(false);
            System.out.println("The van has been checked.");
        }
    }

    //拼写错误，就这吧
//此方法返回具有指定客户记录的客户当前租用的所有车辆（如果有）的不可修改集合。
    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        //add your code here. Do NOT change the method signature
        Set<Vehicle> vehicleSet = hiredVehicles.get(customerRecord.getCustomerNum());
        if (vehicleSet == null)
            return Collections.emptySet();//空集合
        return Collections.unmodifiableSet(vehicleSet);//不可修改集合
    }

}
