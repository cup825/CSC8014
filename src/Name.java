import java.util.Objects;
public final class Name {
    private final String firstName;
    private final String lastName;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) //物理地址相等
            return true;
        if (o instanceof Name name)
            return this.firstName.equals(name.firstName) && this.lastName.equals(name.lastName);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
