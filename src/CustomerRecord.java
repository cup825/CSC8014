import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Calendar;

/**
 * CustomerRecord - Represents a customer in the vehicle rental system.
 * Stores customer details and ensures uniqueness.
 *
 * @author Ziyue Ren
 * @see Name
 * @see VehicleManager
 */
public final class CustomerRecord {
    private final Name name;
    private final Date dateOfBirth;
    private final boolean hasCommercialLicense;
    private final int customerNum;
    private static int counter = 1;//A counter to generate unique customer numbers.
    //A map to store unique customer records based on their name and date of birth.
    private final static HashMap<String, CustomerRecord> records = new HashMap<>();//

    /**
     * Private constructor to create a new CustomerRecord.
     * This ensures that CustomerRecord objects can only be created through the static factory method.
     *
     * @param firstName            The first name of the customer.
     * @param lastName             The last name of the customer.
     * @param birth                The date of birth of the customer.
     * @param hasCommercialLicense Whether the customer has a commercial driving license.
     */
    private CustomerRecord(String firstName, String lastName, Date birth, boolean hasCommercialLicense) {
        if (birth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        this.name = new Name(firstName, lastName);
        this.dateOfBirth = new Date(birth.getTime()); // Defensive copy to ensure immutability.
        this.hasCommercialLicense = hasCommercialLicense;
        this.customerNum = counter++;
    }

    /**
     * Static factory method to create or retrieve a CustomerRecord.
     * Ensures uniqueness based on name and date of birth.
     *
     * @param firstName            The first name of the customer.
     * @param lastName             The last name of the customer.
     * @param birth                The date of birth of the customer.
     * @param hasCommercialLicense Whether the customer has a commercial driving license.
     * @return A unique CustomerRecord instance.
     */
    public static CustomerRecord getInstance(String firstName, String lastName, Date birth, boolean hasCommercialLicense) {
        if (birth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        String key = firstName + lastName + birth.getTime();
        if (!records.containsKey(key)) {
            CustomerRecord cr = new CustomerRecord(firstName, lastName, birth, hasCommercialLicense);
            records.put(key, cr);
        }
        return records.get(key);
    }

    /**
     * Returns the date of birth of the customer.
     * A defensive copy is returned to maintain immutability.
     *
     * @return The date of birth of the customer.
     */
    public Date getDateOfBirth() {
        return (Date) dateOfBirth.clone();
    }

    /**
     * Checks if the customer has a commercial driving license.
     *
     * @return True if the customer has a commercial license, false otherwise.
     */
    public boolean hasCommercialLicense() {
        return hasCommercialLicense;
    }

    /**
     * Returns the unique customer number.
     *
     * @return The customer number.
     */
    public int getCustomerNum() {
        return customerNum;
    }

    /**
     * Returns the name of the customer.
     *
     * @return The Name object representing the customer's name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Calculates and returns the age of the customer based on their date of birth.
     *
     * @return The age of the customer.
     */
    public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(dateOfBirth);
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DATE) < birth.get(Calendar.DATE))) {
            age--;
        }
        return age;
    }

    /**
     * Checks if this CustomerRecord is equal to another object.
     * Two CustomerRecords are considered equal if their name and date of birth are the same.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof CustomerRecord record)
            return this.name.equals(record.name) && this.dateOfBirth.equals(record.dateOfBirth);
        else
            return false;
    }

    /**
     * Returns the hash code for this CustomerRecord.
     * The hash code is based on the name and date of birth.
     *
     * @return The hash code of this CustomerRecord.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    /**
     * Returns a string representation of the CustomerRecord.
     * The string includes the customer number and name.
     *
     * @return A string representation of the CustomerRecord.
     */
    @Override
    public String toString() {
        return "Customer: " + customerNum + " " + name.toString();
    }

}
