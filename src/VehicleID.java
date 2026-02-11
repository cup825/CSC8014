import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class VehicleID {
    //private final String type; //改了，还是写这吧，防止重复判断类型操作
    private final String code;
    private final String numCode;
    private static final Set<VehicleID> vehicleIDs = new HashSet<>();//在这里初始化并且static，因为公用一个

    public static final String CAR = "car";
    public static final String VAN = "van";

    private VehicleID(String code, String numCode) {//随机生成
        this.code = code;
        this.numCode = numCode;
    }

    //禁止直接new，只能通过静态方法调用
    //为什么要这样呢？应该是id不能脱离Vehicle类单独存在吧，否则没有意义 ——理解的不对
    public static VehicleID getInstance(String type) {
        VehicleID id;
        //do-while 与while的区别：do-while是不管咋样先执行一次，然后如果while判断成立，继续执行循环
        do {
            if (type.equalsIgnoreCase(CAR))
                id = new VehicleID(generateCode(CAR), generateNum(CAR));
            else if (type.equalsIgnoreCase(VAN))
                id = new VehicleID(generateCode(VAN), generateNum(VAN));
            else
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
        while (!vehicleIDs.add(id));
        return id;
    }

    //这两个方法只能改成静态方法吗？
    private static String generateCode(String type) {
        char typeChar = type.toUpperCase().charAt(0);//将类型的第一个字母转为大写并作为第一个字符
        //char typeChar=type.equalsIgnoreCase("Car")?'C':'V';
        char randomLetter = (char) ('A' + (int) (Math.random() * 26));
        char randomNum = (char) ('0' + (int) (Math.random() * 10));
        return "" + typeChar + randomLetter + randomNum;
    }

    private static String generateNum(String type) {
        //怎么生成奇or偶
        int even = (int) (Math.random() * 500) * 2;
        return type.equalsIgnoreCase("Car")
                ? String.format("%03d", even)
                : String.format("%03d", (even + 1));
    }

    public String getCode() {
        return code;
    }

    public String getNumCode() {
        return numCode;
    }

    @Override
    public String toString() {
        return code + "-" + numCode;
    }

    //Set检验唯一性，所以要重写
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof VehicleID id)
            return this.numCode.equals(id.numCode) && this.code.equals(id.code);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numCode, code);
    }
}