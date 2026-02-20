import java.lang.reflect.Field;
import java.util.*;

/**
 * VehicleManagerTest – Verifies the logic of VehicleManager
 */
public class VehicleManagerTest {
    static String line = "____________________________";

    /**
     * Main method to execute all VehicleManager test cases.
     * Catches and reports any assertion errors during testing.
     *
     */
    public static void main(String[] args) {
        VehicleManagerTest test = new VehicleManagerTest();

        try {
            System.out.println("Starting vehicle rental system tests...\n");
            test.testSingleton();
            test.testAddVehicle();
            test.testAddCustomer();
            test.testCarVanLimit();
            test.testRentalLimit();
            test.testAvailableVehicle();
            test.testReturnVehicle();

            System.out.println("\nAll VehicleManager test cases passed successfully.");
        } catch (AssertionError e) {
            System.err.println("\nTest failed: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause());
            }
        }
    }

    /**
     * Ensure VehicleManager unique.
     */
    private void testSingleton() {
        System.out.println("Testing Singleton.");
        VehicleManager v1 = VehicleManager.getInstance();
        VehicleManager v2 = VehicleManager.getInstance();
        Assertions.assertTrue(v1 == v2);
    }

    /**
     * Test vehicle creation and type validation.
     * Verifies that vehicles are correctly instantiated as Car or Van.
     */
    private void testAddVehicle() {
        System.out.println(line + "\nTest add vehicle.");
        VehicleManager manager = VehicleManager.getInstance();
        resetManagerState();
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("Car"));
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("Van"));

        //test normal case
        Vehicle car = manager.addVehicle("Car");
        Vehicle van = manager.addVehicle("Van");
        Assertions.assertNotNull(car);
        Assertions.assertNotNull(van);

        Assertions.assertEquals(1, manager.noOfAvailableVehicles("Car"));
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("Van"));
        Assertions.assertTrue(manager.getAllVehicles().contains(car));
        Assertions.assertTrue(manager.getAllVehicles().contains(van));

        //test exception case: Invalid type
        try {
            Vehicle truck = manager.addVehicle("Truck");
            Assertions.assertNotReached();
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

        //test exception case: null
        try {
            Vehicle truck = manager.addVehicle(null);
            Assertions.assertNotReached();
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }


        System.out.println("Add Vehicle test passed.\n" + line);
    }

    /**
     * Test customer creation and uniqueness.
     * Ensures that duplicate customers cannot be added.
     */
    private void testAddCustomer() {
        System.out.println("Test add customer.");
        VehicleManager manager = VehicleManager.getInstance();
        resetManagerState();
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1);
        Date dob = cal.getTime();

        //test normal case
        CustomerRecord c1 = manager.addCustomerRecord("Harry", "Potter", dob, false);
        Assertions.assertNotNull(c1);
        Assertions.assertTrue(manager.getCustomers().contains(c1));

        //test exception case: duplicate customerRecord)
        try {
            manager.addCustomerRecord("Harry", "Potter", dob, false);
            Assertions.assertNotReached();
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

        //test exception case: null name
        try {
            manager.addCustomerRecord("Amy", null, dob, true);
            Assertions.assertNotReached();
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

        //test exception case: null dob
        try {
            manager.addCustomerRecord("Amy", "Jones", null, true);
            Assertions.assertNotReached();
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

        System.out.println("Add customer test passed.\n" + line);
    }


    /**
     * Test restrictions related to car and van.
     */
    private void testCarVanLimit() {
        System.out.println("Test car and van restriction.");
        VehicleManager manager = VehicleManager.getInstance();
        resetManagerState();

        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.MARCH, 3);
        CustomerRecord young =
                manager.addCustomerRecord("Young", "User", cal.getTime(), false);

        cal.set(2005, Calendar.FEBRUARY, 14);
        CustomerRecord adult1 = manager.addCustomerRecord("Adult1", "User", cal.getTime(), true);

        cal.set(1998, Calendar.JANUARY, 1);
        CustomerRecord adult2 = manager.addCustomerRecord("Adult2", "User", cal.getTime(), false);
        CustomerRecord adult3 = manager.addCustomerRecord("Adult3", "User", cal.getTime(), true);

        Assertions.assertTrue(manager.getCustomers().contains(young));
        Assertions.assertTrue(manager.getCustomers().contains(adult1));
        Assertions.assertTrue(manager.getCustomers().contains(adult2));
        Assertions.assertTrue(manager.getCustomers().contains(adult3));

        manager.addVehicle("Car");
        manager.addVehicle("Van");

        Assertions.assertFalse(manager.hireVehicle(young, "Car", 5));
        Assertions.assertTrue(manager.hireVehicle(adult1, "Car", 3));
        Assertions.assertFalse(manager.hireVehicle(adult1, "Van", 3));
        Assertions.assertFalse(manager.hireVehicle(adult2, "Van", 3));
        Assertions.assertTrue(manager.hireVehicle(adult3, "Van", 3));

        System.out.println("Car and van restriction test passed.\n" + line);
    }

    /**
     * Test rental limit.
     * Ensures that a customer cannot rent more than three vehicles at a time.
     */
    private void testRentalLimit() {
        System.out.println("Test rental limit.");
        VehicleManager manager = VehicleManager.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);

        CustomerRecord customer =
                manager.addCustomerRecord("Frequent", "User", cal.getTime(), true);

        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");
        manager.addVehicle("Car");

        Assertions.assertTrue(manager.hireVehicle(customer, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(customer, "Car", 1));
        Assertions.assertTrue(manager.hireVehicle(customer, "Car", 1));
        Assertions.assertFalse(manager.hireVehicle(customer, "Car", 1));

        Collection<Vehicle> vehicles = manager.getVechilesByCustomer(customer);
        Assertions.assertEquals(3, vehicles.size());

        System.out.println("Rental limit test passed.\n" + line);
    }

    /**
     * Test van inspection requirement for long-term rental.
     * Verifies that vans are flagged for inspection after rentals of 10 or more days.
     */
    private void testAvailableVehicle() {
        System.out.println("Test available vehicle.");
        VehicleManager manager = VehicleManager.getInstance();
        resetManagerState();
        manager.addVehicle("Van");

        Calendar cal = Calendar.getInstance();
        cal.set(1980, Calendar.JANUARY, 1);

        CustomerRecord customer =
                manager.addCustomerRecord("Van", "User", cal.getTime(), true);
        Assertions.assertFalse(manager.hireVehicle(customer, "Car", 11));

        Assertions.assertTrue(manager.hireVehicle(customer, "Van", 11));
        Vehicle van = manager.getVechilesByCustomer(customer).iterator().next();

        Assertions.assertTrue(van.isHired());
        Assertions.assertTrue(van instanceof Van);
        Assertions.assertTrue(((Van) van).needCheck());

        System.out.println("Available vehicle test passed.\n" + line);
    }

    /**
     * Test return vehicle and mileage reset.
     */
    private void testReturnVehicle() {
        System.out.println("Test return vehicle.");
        VehicleManager manager = VehicleManager.getInstance();
        resetManagerState();

        Vehicle van = manager.addVehicle("Van");
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("van"));

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        CustomerRecord customer =
                manager.addCustomerRecord("Harry", "Potter", cal.getTime(), true);

        manager.hireVehicle(customer, "Van", 11);
        Assertions.assertEquals(0, manager.noOfAvailableVehicles("van"));

        //test set & get CurrentMileage.
        van.setCurrentMileage(2000);
        Assertions.assertEquals(2000, van.getCurrentMileage());

        //test hired state of the van and whether available (After return)
        manager.returnVehicle(van.getVehicleID(), customer, 5000);
        Assertions.assertFalse(van.isHired());
        Assertions.assertEquals(1, manager.noOfAvailableVehicles("van"));

        //test check and mileage reset.
        Assertions.assertFalse(((Van) van).needCheck());
        Assertions.assertEquals(0, van.getCurrentMileage());


        System.out.println("Return vehicle test passed.\n" + line);
    }

    /**
     * Reset manager of VehicleManager.
     */
    private void resetManagerState() {
        try {
            VehicleManager manager = VehicleManager.getInstance();
            Field allVehiclesField = VehicleManager.class.getDeclaredField("allVehicles");
            Field customersField = VehicleManager.class.getDeclaredField("customers");
            Field hiredVehiclesField = VehicleManager.class.getDeclaredField("hiredVehicles");
            allVehiclesField.setAccessible(true);
            customersField.setAccessible(true);
            hiredVehiclesField.setAccessible(true);

            ((List<?>) allVehiclesField.get(manager)).clear();
            ((List<?>) customersField.get(manager)).clear();
            ((Map<?, ?>) hiredVehiclesField.get(manager)).clear();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to reset VehicleManager state", e);
        }
    }
}

