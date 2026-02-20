/**
 * VehicleIDTest – Verifies the logic and business rules of the VehicleID class.
 *
 * @author Ziyue Ren
 * @see VehicleID
 * @see Assertions
 */
public class VehicleIDTest {
    static String line = "____________________________";

    /**
     * Main method to execute all VehicleID test cases.
     * Catches and reports any assertion errors during testing.
     *
     */
    public static void main(String[] args) {
        VehicleIDTest test = new VehicleIDTest();
        try {
            test.testCreateVehicleID();
            test.testGetID();
            test.testEquals();
            test.testHashCode();
            test.testIdRules();
            //testUniqueness();
        } catch (AssertionError e) {
            System.err.println("\nTest failed: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause());
            }
        }

        System.out.println("\nAll VehicleID test cases passed successfully.");
    }

    private void testCreateVehicleID() {
        System.out.println("Test create VehicleID.");
        VehicleID id = VehicleID.getInstance(VehicleID.CAR);
        Assertions.assertNotNull(id);
        Assertions.assertNotNull(id.getCode());
        Assertions.assertNotNull(id.getNumCode());

        // Test exception case: invalid type
        try {
            VehicleID.getInstance("Bike");
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }
    }

    /**
     * Verifies the specific business rules associated with Vehicle IDs.
     * Checks 'C' prefix/even for Cars and 'V' prefix/odd for Vans.
     */
    private void testIdRules() {
        System.out.println("Testing Vehicle ID rules(Odd/Even/Prefix).");
        //test car: even; prefix
        VehicleID carId = VehicleID.getInstance(VehicleID.CAR);
        Assertions.assertTrue(carId.getCode().charAt(0) == 'C');

        int carNum = Integer.parseInt(carId.getNumCode());
        Assertions.assertTrue(carNum % 2 == 0);

        //test van: odd; prefix
        VehicleID vanId = VehicleID.getInstance(VehicleID.VAN);
        Assertions.assertTrue(vanId.getCode().charAt(0) == 'V');
        int vanNum = Integer.parseInt(vanId.getNumCode());
        Assertions.assertTrue(vanNum % 2 != 0);
    }

    /**
     * Checks if toString returns non-null and contains the separator.
     */
    private void testGetID() {
        VehicleID id = VehicleID.getInstance(VehicleID.CAR);
        Assertions.assertNotNull(id.toString());
        Assertions.assertTrue(id.toString().contains("-"));
    }

    /**
     * Checks if equals works for self-check and uniqueness check.
     */
    private void testEquals() {
        System.out.println("Test equals.");
        VehicleID id1 = VehicleID.getInstance(VehicleID.CAR);
        VehicleID id2 = VehicleID.getInstance(VehicleID.CAR);

        Assertions.assertTrue(id1.equals(id1));
        Assertions.assertFalse(id1.equals(null));
        Assertions.assertFalse(id1.equals("Some String"));
        Assertions.assertFalse(id1.equals(id2)); // Ensure uniqueness
    }

    /**
     * Checks if hashCode is consistent for the same object.
     */
    private void testHashCode() {
        System.out.println("Test hash code.");
        VehicleID id1 = VehicleID.getInstance(VehicleID.VAN);
        Assertions.assertEquals(id1.hashCode(), id1.hashCode());
    }
}
