import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * VehicleIDTest – Verifies the logic and business rules of VehicleID.
 *
 * @author Ziyue Ren
 * @see VehicleID
 * @see Assertions
 */
public final class VehicleID {
    private final String code;
    private final String numCode;
    private static final Set<VehicleID> vehicleIDs = new HashSet<>();
    public static final String CAR = "car";
    public static final String VAN = "van";

    /**
     * Private constructor to create a VehicleID instance.
     * This ensures that VehicleID objects can only be created through the static factory method.
     *
     * @param code    The alphanumeric code for the vehicle.
     * @param numCode The numeric code for the vehicle.
     */
    private VehicleID(String code, String numCode) {
        this.code = code;
        this.numCode = numCode;
    }

    /**
     * Static factory method to create or retrieve a unique VehicleID instance.
     * Ensures that no two VehicleID objects have the same code and numCode combination.
     *
     * @param type The type of vehicle ("car" or "van").
     * @return A unique VehicleID instance.
     */
    public static VehicleID getInstance(String type) {
        VehicleID id;
        do {
            if (type.equalsIgnoreCase(CAR)) {
                id = new VehicleID(generateCode(CAR), generateNum(CAR));
            } else if (type.equalsIgnoreCase(VAN)) {
                id = new VehicleID(generateCode(VAN), generateNum(VAN));
            } else {
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
            }
        } while (!vehicleIDs.add(id)); // Ensure uniqueness by adding to the set.
        return id;
    }

    /**
     * Generates a random alphanumeric code for the vehicle.
     *
     * @param type The type of vehicle ("car" or "van").
     * @return A string representing the alphanumeric code.
     */
    private static String generateCode(String type) {
        char typeChar = type.equalsIgnoreCase(CAR) ? 'C' : 'V';
        char randomLetter = (char) ('A' + (int) (Math.random() * 26));
        char randomNum = (char) ('0' + (int) (Math.random() * 10));
        return "" + typeChar + randomLetter + randomNum;
    }

    /**
     * Generates a random numeric code for the vehicle.
     * Ensures that the code is even for cars and odd for vans.
     *
     * @param type The type of vehicle ("car" or "van").
     * @return A string representing the numeric code.
     */
    private static String generateNum(String type) {
        int even = (int) (Math.random() * 500) * 2;
        return type.equalsIgnoreCase(CAR)
                ? String.format("%03d", even)
                : String.format("%03d", (even + 1));
    }

    /**
     * Returns the alphanumeric code of the vehicle.
     *
     * @return The alphanumeric code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the numeric code of the vehicle.
     *
     * @return The numeric code.
     */
    public String getNumCode() {
        return numCode;
    }

    /**
     * Returns a string representation of the VehicleID.
     * Combines the alphanumeric and numeric codes.
     *
     * @return A string representation of the VehicleID.
     */
    @Override
    public String toString() {
        return code + "-" + numCode;
    }

    /**
     * Checks if this VehicleID is equal to another object.
     * Two VehicleID objects are considered equal if their code and numCode are the same.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof VehicleID id)
            return this.numCode.equals(id.numCode) && this.code.equals(id.code);
        else
            return false;
    }

    /**
     * Returns the hash code for this VehicleID.
     * The hash code is based on the code and numCode.
     *
     * @return The hash code of this VehicleID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numCode, code);
    }
}