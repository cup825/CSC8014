import java.util.Date;

public final class CustomerRecord extends Name {
    private final Date dob;
    private final boolean isCommercial;
    private final int customerNum;
    private static int nextCustomerNum = 1;//命名怪怪的
    //private final int customerNum; //Unique 工厂模式，待学

    public CustomerRecord(String firstName, String lastName, Date birth, boolean isCommercial) {
        super(firstName, lastName);
        this.dob = birth;
        this.isCommercial = isCommercial;
        this.customerNum = nextCustomerNum++;
        //this.customerNum = customerNum;
    }

    public Date getDate() {
        return dob;
    }

    public boolean getIsCommercial() {
        return isCommercial;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    //待写
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerRecord))
            return false;
        CustomerRecord record = (CustomerRecord) o;
        return this.getName().equals(record.getName()) && this.dob.equals(record.dob) &&
                this.isCommercial == record.isCommercial && this.customerNum == record.customerNum;
    }
//    You should use the java.util.Date class to represent dates in CustomerRecord.
//    However, you must not use deprecated methods of the Date class. So, for example, in the
//    test class, you can use java.util.Calendar to construct dates of birth of customer
//    records. You can assume default time zone and locale

}

