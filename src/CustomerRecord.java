import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Calendar;

//修改继承关系
public final class CustomerRecord {
    private final Name name;
    private final Date dateOfBirth;
    private final boolean hasCommercialLicense;
    private final int customerNum;
    private static int counter = 1;//命名怪怪的

    private final static HashMap<String, CustomerRecord> records = new HashMap<String, CustomerRecord>();

    //只能通过静态工厂方法创建新对象
    private CustomerRecord(String firstName, String lastName, Date birth, boolean hasCommercialLicense) {
        if (birth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        //姓名不用重新防御性检查，在Name的构造函数中检查过了
        this.name = new Name(firstName, lastName);
        this.dateOfBirth = new Date(birth.getTime());// Make defensive copies when needed (Bloch-EJ Item 50)
        this.hasCommercialLicense = hasCommercialLicense;
        this.customerNum = counter++;
    }

    //静态工厂方法2: 复用已有对象, 省去重复new
    public static CustomerRecord getInstance(String firstName, String lastName, Date birth, boolean hasCommercialLicense) {
        if (birth == null)//构造函数检查，这里同样要检查，因为即将调用birth.getTime()
            throw new IllegalArgumentException("Date of birth cannot be null!");
        String s = firstName + lastName + birth.getTime();
        if (!records.containsKey(s)) {
            CustomerRecord cr = new CustomerRecord(firstName, lastName, birth, hasCommercialLicense);
            records.put(s, cr);
        }
        return records.get(s);
    }

    public Date getDateOfBirth() {
        return (Date) dateOfBirth.clone();//防止通过被修改
    }

    //若字段名是isXxx，方法名应直接写isXxx()，无需加get前缀（Java 官方编码规范）。本质上是get方法
    public boolean hasCommercialLicense() {
        return hasCommercialLicense;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public Name getName() {
        return name;
        //return new Name(name.getFirstName(), name.getLastName());
    }

    public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(dateOfBirth);
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        // 如果“今年”的月份还没到生日月，或者月份到了但日子还没到，年龄就要减1
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DATE) < birth.get(Calendar.DATE))) {
            age--;
        }
        return age;
//        return (birth.get(Calendar.DAY_OF_YEAR) > today.get(Calendar.DAY_OF_YEAR))
//                ? age - 1
//                : age;
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

    //这个应该是否应该打印全部信息
    @Override
    public String toString() {
        return "Customer: " + customerNum + " " + name.toString();
    }

}

