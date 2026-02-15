import java.util.*;

/**
 * 全面测试类 - 验证 VehicleManager 及相关类的逻辑
 */
public class Test {

    public static void main(String[] args) {
        try {
            System.out.println("开始租车系统全面测试...\n");

            testCustomerCreation();
            testVehicleAddition();
            testHireCarAgeLimit();
            testHireVanRequirements();
            testRentalLimit();
            testMaintenanceAndInspection();
            testReturnVehicleLogic();

            System.out.println("\n恭喜！所有测试用例均已通过。");
        } catch (AssertionError e) {
            System.err.println("\n测试失败: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("引起原因: " + e.getCause());
            }
        }
    }

    // 1. 测试客户创建与唯一性
    private static void testCustomerCreation() {
        VehicleManager manager = VehicleManager.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1);
        Date dob = cal.getTime();

        CustomerRecord c1 = manager.addCustomerRecord("John", "Doe", dob, false);
        Assertions.assertNotNull(c1);

        // 测试重复添加同一客户应抛出异常
        try {
            manager.addCustomerRecord("John", "Doe", dob, false);
            Assertions.assertNotReached();
        } catch (IllegalArgumentException e) {
            System.out.println("√ 客户重复检查测试通过");
        }
    }

    // 2. 测试车辆添加
    private static void testVehicleAddition() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");
        Vehicle van = manager.addVehicle("Van");

        Assertions.assertTrue(car instanceof Car);
        Assertions.assertTrue(van instanceof Van);
        Assertions.assertEquals("Car", car.getVehicleType());
        Assertions.assertEquals(0, car.getCurrentMileage());
        System.out.println("√ 车辆添加与类型检查测试通过");
    }

    // 3. 测试汽车租用年龄限制 (18岁)
    private static void testHireCarAgeLimit() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Car"); // 确保有车

        // 创建一个未满18岁的客户 (假设当前是2026年)
        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.JANUARY, 1);
        CustomerRecord youngUser = manager.addCustomerRecord("Young", "User", cal.getTime(), false);

        boolean canHire = manager.hireVehicle(youngUser, "Car", 5);
        Assertions.assertFalse(canHire); // 应该失败
        System.out.println("√ 汽车租用年龄限制测试通过");
    }

    // 4. 测试货车租用要求 (23岁 + 商业驾照)
    private static void testHireVanRequirements() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Van");

        // 客户1: 够岁数但没商业驾照
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        CustomerRecord noLicenseUser = manager.addCustomerRecord("No", "License", cal.getTime(), false);
        Assertions.assertFalse(manager.hireVehicle(noLicenseUser, "Van", 5));

        // 客户2: 有驾照但未满23岁
        cal.set(2005, Calendar.JANUARY, 1);
        CustomerRecord youngVanUser = manager.addCustomerRecord("Young", "Van", cal.getTime(), true);
        Assertions.assertFalse(manager.hireVehicle(youngVanUser, "Van", 5));

        // 客户3: 符合所有条件
        cal.set(1995, Calendar.JANUARY, 1);
        CustomerRecord validVanUser = manager.addCustomerRecord("Valid", "Van", cal.getTime(), true);
        Assertions.assertTrue(manager.hireVehicle(validVanUser, "Van", 5));
        System.out.println("√ 货车租用资格限制测试通过");
    }

    // 5. 测试租车上限 (3辆)
    private static void testRentalLimit() {
        VehicleManager manager = VehicleManager.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        CustomerRecord frequentUser = manager.addCustomerRecord("Frequent", "User", cal.getTime(), true);

        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");

        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));

        // 第4辆应该失败
        Assertions.assertFalse(manager.hireVehicle(frequentUser, "Car", 1));
        System.out.println("√ 个人租车上限测试通过");
    }

    // 6. 测试货车检查逻辑 (租期>=10天)
    private static void testMaintenanceAndInspection() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Van");

        Calendar cal = Calendar.getInstance();
        cal.set(1980, Calendar.JANUARY, 1);
        CustomerRecord vanTester = manager.addCustomerRecord("Van", "Tester", cal.getTime(), true);

        // 租用11天，触发 needCheck
        manager.hireVehicle(vanTester, "Van", 11);

        // 获取刚租的那辆车
        Vehicle van = manager.getVechilesByCustomer(vanTester).iterator().next();
        Assertions.assertTrue(((Van)van).needCheck());

        // 归还车辆
        manager.returnVehicle(van.getVehicleID(), vanTester, 100);

        // 归还后应重置 check 状态
        Assertions.assertFalse(((Van)van).needCheck());
        Assertions.assertFalse(van.isHired());
        System.out.println("√ 货车长租检查及状态重置测试通过");
    }

    // 7. 测试保养里程重置
    private static void testReturnVehicleLogic() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        CustomerRecord driver = manager.addCustomerRecord("Race", "Driver", cal.getTime(), false);

        manager.hireVehicle(driver, "Car", 1);

        // 行驶 10001 英里 (超过 Car 的 10000 限制)
        manager.returnVehicle(car.getVehicleID(), driver, 10001);

        Assertions.assertEquals(0, car.getCurrentMileage()); // 应被重置为 0
        Assertions.assertTrue(manager.getHiredVehicles().isEmpty() || !manager.getHiredVehicles().containsKey(driver.getCustomerNum()));
        System.out.println("√ 保养里程重置逻辑测试通过");
    }
}