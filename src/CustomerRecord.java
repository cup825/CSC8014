import java.util.Date;
import java.util.Objects;

//修改继承关系
public final class CustomerRecord {
    private final Name name;
    private final Date dateOfBirth;
    private final boolean isCommercial;
    private final int customerNum;
    private static int nextCustomerNum = 1;//命名怪怪的
    //private final int customerNum; //Unique 工厂模式，待学

    public CustomerRecord(String firstName, String lastName, Date birth, boolean isCommercial) {
        //super(firstName, lastName);
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Firstname or lastname cannot be null!");
        }
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Firstname or lastname cannot be empty or blank!");
        }
        if (birth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        this.name = new Name(firstName, lastName);
        this.dateOfBirth = new Date(birth.getTime());//防止被setTime
        this.isCommercial = isCommercial;
        this.customerNum = nextCustomerNum++;
    }

    public Date getDateOfBirth() {
        return (Date) dateOfBirth.clone();//防止通过被修改
    }

    //若字段名是isXxx，方法名应直接写isXxx()，无需加get前缀（Java 官方编码规范）。
    public boolean isCommercial() {
        return isCommercial;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public Name getName() {
        return name;
    }

//    public String getName() {
//        return name.getFirstName() + " " + name.getLastName();
//    }

    //待写
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerRecord))
            return false;
        CustomerRecord record = (CustomerRecord) o;
        //return this.getName().equals(record.getName()) && this.dob.equals(record.dob);
        return this.name.equals(record.name) && this.dateOfBirth.equals(record.dateOfBirth);
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

