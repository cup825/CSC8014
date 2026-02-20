import java.util.*;

/**
 * Comprehensive Test Class – Verifies the logic of VehicleManager
 * and related classes in the vehicle rental system.
 */
public class Test {
    static String line = "____________________________";

    /**
     * Main method to execute all test cases.
     * Catches and reports any assertion errors during testing.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        //VehicleManager manager = VehicleManager.getInstance();

        try {
            System.out.println("Starting vehicle rental system tests...\n");

            testNoOfAvailableVehicles();

            testAddVehicle();
            testAddCustomer();

            //testNoOfAvailableVehicles();

            testHireCarAgeLimit();
            testHireVanRequirements();
            testRentalLimit();
            testMaintenanceAndInspection();
            testReturnVehicle();
            testInvalidVehicleType();
            testDuplicateVehicleID();
            testBoundaryMileage();

            System.out.println("\nAll test cases passed successfully.");
        } catch (AssertionError e) {
            System.err.println("\nTest failed: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause());
            }
        }
    }

    /**
     * Test vehicle creation and type validation.
     * Verifies that vehicles are correctly instantiated as Car or Van.
     */
    private static void testAddVehicle() {
        System.out.println(line + "\nStart adding Vehicle test.");
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");
        Vehicle van = manager.addVehicle("Van");

        Assertions.assertTrue(car instanceof Car);
        Assertions.assertTrue(van instanceof Van);

        try {
            Vehicle truck = manager.addVehicle("Truck");
        } catch (IllegalArgumentException e){
            e.getMessage();
        }
//        Assertions.assertEquals("Car", car.getVehicleType());
//        Assertions.assertEquals(0, car.getCurrentMileage());

        System.out.println("Add Vehicle test passed.\n" + line);
    }

    /**
     * Test customer creation and uniqueness.
     * Ensures that duplicate customers cannot be added.
     */
    private static void testAddCustomer() {
        VehicleManager manager = VehicleManager.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1);
        Date dob = cal.getTime();

        CustomerRecord c1 = manager.addCustomerRecord("Harry", "Potter", dob, false);
        Assertions.assertNotNull(c1);

        try {
            manager.addCustomerRecord("Harry", "Potter", dob, false);
            Assertions.assertNotReached();
        } catch (IllegalArgumentException e) {
            System.out.println("Customer uniqueness test passed.");
        }
        System.out.println("Add customer test passed.");
    }


    /**
     * Test car rental minimum age requirement.
     * Ensures that customers under 18 cannot rent a car.
     */
    private static void testHireCarAgeLimit() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Car");

        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.JANUARY, 1);
        CustomerRecord youngUser =
                manager.addCustomerRecord("Young", "User", cal.getTime(), false);

        boolean canHire = manager.hireVehicle(youngUser, "Car", 5);
        Assertions.assertFalse(canHire);

        System.out.println("Car age restriction test passed.");
    }

    /**
     * Test van rental requirements.
     * Ensures that customers meet age and license requirements to rent a van.
     */
    private static void testHireVanRequirements() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Van");

        Calendar cal = Calendar.getInstance();

        // Old enough but no commercial licence
        cal.set(1990, Calendar.JANUARY, 1);
        CustomerRecord noLicenceUser =
                manager.addCustomerRecord("No", "Licence", cal.getTime(), false);
        Assertions.assertFalse(manager.hireVehicle(noLicenceUser, "Van", 5));

        // Has licence but under 23
        cal.set(2005, Calendar.JANUARY, 1);
        CustomerRecord youngVanUser =
                manager.addCustomerRecord("Young", "Van", cal.getTime(), true);
        Assertions.assertFalse(manager.hireVehicle(youngVanUser, "Van", 5));

        // Meets all requirements
        cal.set(1995, Calendar.JANUARY, 1);
        CustomerRecord validVanUser =
                manager.addCustomerRecord("Valid", "Van", cal.getTime(), true);
        Assertions.assertTrue(manager.hireVehicle(validVanUser, "Van", 5));

        System.out.println("Van eligibility test passed.");
    }

    /**
     * Test rental limit.
     * Ensures that a customer cannot rent more than three vehicles at a time.
     */
    private static void testRentalLimit() {
        VehicleManager manager = VehicleManager.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);

        CustomerRecord frequentUser =
                manager.addCustomerRecord("Frequent", "User", cal.getTime(), true);

        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");

        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(frequentUser, "Car", 1));

        Assertions.assertFalse(manager.hireVehicle(frequentUser, "Car", 1));

        System.out.println("Rental limit test passed.");
    }

    /**
     * Test van inspection requirement for long-term rental.
     * Verifies that vans are flagged for inspection after rentals of 10 or more days.
     */
    private static void testMaintenanceAndInspection() {
        VehicleManager manager = VehicleManager.getInstance();
        manager.addVehicle("Van");

        Calendar cal = Calendar.getInstance();
        cal.set(1980, Calendar.JANUARY, 1);

        CustomerRecord vanTester =
                manager.addCustomerRecord("Van", "Tester", cal.getTime(), true);

        manager.hireVehicle(vanTester, "Van", 11);

        Vehicle van =
                manager.getVechilesByCustomer(vanTester).iterator().next();

        Assertions.assertTrue(((Van) van).needCheck());

        manager.returnVehicle(van.getVehicleID(), vanTester, 100);

        Assertions.assertFalse(((Van) van).needCheck());
        Assertions.assertFalse(van.isHired());

        System.out.println("Van inspection reset test passed.");
    }

    /**
     * Test mileage reset after exceeding maintenance threshold.
     * Ensures that vehicles are serviced and mileage is reset after return.
     */
    private static void testReturnVehicle() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);

        CustomerRecord driver =
                manager.addCustomerRecord("Race", "Driver", cal.getTime(), false);

        manager.hireVehicle(driver, "Car", 1);

        manager.returnVehicle(car.getVehicleID(), driver, 10001);

        Assertions.assertEquals(0, car.getCurrentMileage());

        System.out.println("Mileage reset test passed.");
    }

    /**
     * Test invalid vehicle type addition.
     * Ensures that adding an invalid vehicle type throws an exception.
     */
    private static void testInvalidVehicleType() {
        VehicleManager manager = VehicleManager.getInstance();
        try {
            manager.addVehicle("Bike");
            Assertions.assertNotReached();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type test passed.");
        }
    }

    /**
     * Test duplicate vehicle ID generation.
     * Ensures that no two vehicles have the same ID.
     */
    private static void testDuplicateVehicleID() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car1 = manager.addVehicle("Car");
        Vehicle car2 = manager.addVehicle("Car");

        Assertions.assertNotEquals(car1.getVehicleID(), car2.getVehicleID());

        System.out.println("Duplicate vehicle ID test passed.");
    }

    /**
     * Test boundary mileage for service.
     * Ensures that vehicles are serviced exactly at the distance requirement.
     */
    private static void testBoundaryMileage() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");

        car.setCurrentMileage(10000);
        Assertions.assertTrue(car.performServiceIfDue());
        Assertions.assertEquals(0, car.getCurrentMileage());

        System.out.println("Boundary mileage test passed.");
    }

    private static void testNoOfAvailableVehicles() {
        VehicleManager manager = VehicleManager.getInstance();

        // 1. 初始状态：无车辆时可用数为0
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("Car"));
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("Van"));

        // 2. 添加车辆后，可用数等于添加数量
        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Van");
        Assertions.assertEquals(2, manager.noOfAvailableVehicles("Car"));
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("Van"));

        // 3. 租用车辆后，可用数减少
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1); // 满足租车年龄要求
        CustomerRecord testCustomer = manager.addCustomerRecord("Test", "User", cal.getTime(), true);
        // 租用1辆Car
        boolean hireCarSuccess = manager.hireVehicle(testCustomer, "Car", 5);
        Assertions.assertTrue(hireCarSuccess);
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("Car")); // 剩余1辆Car可用
        //Assertions.assertEquals(1, manager.noOfAvailableVehicles("Van")); // Van不受影响

        // 4. 租用所有Car后，Car可用数为0
        boolean hireSecondCarSuccess = manager.hireVehicle(testCustomer, "Car", 5);
        Assertions.assertTrue(hireSecondCarSuccess);
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("Car"));

        // 5. 归还车辆后，可用数恢复
        // 获取该客户租用的Car
        Vehicle hiredCar = manager.getVechilesByCustomer(testCustomer).stream()
                .filter(v -> v.getVehicleType().equalsIgnoreCase("Car"))
                .findFirst()
                .orElseThrow();
        // 归还车辆
        manager.returnVehicle(hiredCar.getVehicleID(), testCustomer, 100);
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("Car"));

        System.out.println("noOfAvailableVehicles test passed.");
    }
}

