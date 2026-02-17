import java.util.*;

/**
 * Comprehensive Test Class – Verifies the logic of VehicleManager
 * and related classes in the vehicle rental system.
 */
public class Test {

    public static void main(String[] args) {
        try {
            System.out.println("Starting comprehensive vehicle rental system tests...\n");

            testCustomerCreation();
            testVehicleAddition();
            testHireCarAgeLimit();
            testHireVanRequirements();
            testRentalLimit();
            testMaintenanceAndInspection();
            testReturnVehicle();

            System.out.println("\nAll test cases passed successfully.");
        } catch (AssertionError e) {
            System.err.println("\nTest failed: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause());
            }
        }
    }

    // 1. Test customer creation and uniqueness
    private static void testCustomerCreation() {
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
    }

    // 2. Test vehicle creation and type validation
    private static void testVehicleAddition() {
        VehicleManager manager = VehicleManager.getInstance();
        Vehicle car = manager.addVehicle("Car");
        Vehicle van = manager.addVehicle("Van");

        Assertions.assertTrue(car instanceof Car);
        Assertions.assertTrue(van instanceof Van);
        Assertions.assertEquals("Car", car.getVehicleType());
        Assertions.assertEquals(0, car.getCurrentMileage());

        System.out.println("Vehicle creation test passed.");
    }

    // 3. Test car rental minimum age requirement (18 years old)
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

    // 4. Test van rental requirements (minimum 23 years old and commercial licence)
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

    // 5. Test rental limit (maximum of three vehicles per customer)
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

    // 6. Test van inspection requirement for long-term rental (>= 10 days)
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

    // 7. Test mileage reset after exceeding maintenance threshold
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
}