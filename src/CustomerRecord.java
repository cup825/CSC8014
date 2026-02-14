import java.util.Date;
import java.util.Objects;
import java.util.Calendar;

//修改继承关系
public final class CustomerRecord {
    private final Name name;
    private final Date dateOfBirth;
    private final boolean hasCommercialLicense;
    private final int customerNum;
    private static int counter = 1;//命名怪怪的
    //private final int customerNum; //Unique 工厂模式，待学

    //改成私有构造方法
    private CustomerRecord(String firstName, String lastName, Date birth, boolean hasCommercialLicense) {
        //super(firstName, lastName);
//        if (firstName == null || lastName == null) {
//            throw new IllegalArgumentException("Firstname or lastname cannot be null!");
//        }
//
//        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
//            throw new IllegalArgumentException("Firstname or lastname cannot be empty or blank!");
//        }

        if (birth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        this.name = new Name(firstName, lastName);
        this.dateOfBirth = new Date(birth.getTime());// Make defensive copies when needed (Bloch-EJ Item 50)
        this.hasCommercialLicense = hasCommercialLicense;
        this.customerNum = counter++;
    }

    public Date getDateOfBirth() {
        return (Date) dateOfBirth.clone();//防止通过被修改
    }

    //若字段名是isXxx，方法名应直接写isXxx()，无需加get前缀（Java 官方编码规范）。
    public boolean hasCommercialLicense() {
        return hasCommercialLicense;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public Name getName() {
        //return name;
        return new Name(name.getFirstName(),name.getLastName());
    }

    public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(dateOfBirth);
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        return (birth.get(Calendar.DAY_OF_YEAR) > today.get(Calendar.DAY_OF_YEAR))
                ? age - 1
                : age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof CustomerRecord record)
            return this.name.equals(record.name) && this.dateOfBirth.equals(record.dateOfBirth);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }
//    You should use the java.util.Date class to represent dates in CustomerRecord.
//    However, you must not use deprecated methods of the Date class. So, for example, in the
//    test class, you can use java.util.Calendar to construct dates of birth of customer
//    records. You can assume default time zone and locale

}

