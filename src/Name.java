import java.util.Objects;

/**
 * Name - Represents a person's name.
 * Ensures immutability and validates input.
 *
 * @author Ziyue Ren
 * @see CustomerRecord
 */
public final class Name {
    private final String firstName;
    private final String lastName;

    /**
     * Constructor for Name.
     * Validates and initializes the first and last name.
     *
     * @param firstName The first name.
     * @param lastName  The last name.
     * @throws IllegalArgumentException if the first name or last name is null, empty, or blank.
     */
    public Name(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Firstname or lastname cannot be null!");
        }
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Firstname or lastname cannot be empty or blank!");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    /**
     * Returns the first name of the person.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the person.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Checks if this Name object is equal to another object.
     * Two Name objects are considered equal if their first and last names are the same.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) // Check if the references are the same.
            return true;
        if (o instanceof Name name)
            return this.firstName.equals(name.firstName) && this.lastName.equals(name.lastName);
        else
            return false;
    }

    /**
     * Returns the hash code for this Name object.
     * The hash code is based on the first and last names.
     *
     * @return The hash code of this Name object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Returns a string representation of the Name object.
     * Combines the first and last names.
     *
     * @return A string representation of the Name object.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
