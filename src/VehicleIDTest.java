public class VehicleIDTest {
    public static void main(String[] args) {
        VehicleIDTest test = new VehicleIDTest();
        System.out.println("Test create VehicleID.");
        test.testCreateVehicleID();
        System.out.println("Test get VehicleID information.");
        test.testGetID();
        System.out.println("Test equals.");
        test.testEquals();
        System.out.println("Test hash code.");
        test.testHashCode();

        System.out.println("\nAll VehicleID test cases passed successfully.");
    }

    private void testCreateVehicleID() {
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

    private void testGetID() {
        VehicleID id = VehicleID.getInstance(VehicleID.CAR);
        Assertions.assertNotNull(id.toString());
        Assertions.assertTrue(id.toString().contains("-"));
    }

    private void testEquals() {
        VehicleID id1 = VehicleID.getInstance(VehicleID.CAR);
        VehicleID id2 = VehicleID.getInstance(VehicleID.CAR);

        Assertions.assertTrue(id1.equals(id1));
        Assertions.assertFalse(id1.equals(null));
        Assertions.assertFalse(id1.equals("Some String"));
        Assertions.assertFalse(id1.equals(id2)); // Ensure uniqueness
    }

    private void testHashCode() {
        VehicleID id1 = VehicleID.getInstance(VehicleID.VAN);
        Assertions.assertEquals(id1.hashCode(), id1.hashCode());
    }
}
