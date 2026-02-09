public final class VehicleID {
    private final String type; //改了，还是写这吧，防止重复判断类型操作
    private final String code;
    private final String numCode;

    public VehicleID(String type) {//随机生成
        this.type = type;
        if (!(type.equalsIgnoreCase("Car") || type.equalsIgnoreCase("Van"))) {
            throw new IllegalArgumentException("Invalid vehicle type!");
        }
        this.code = generateCode(type);
        this.numCode = generateNum(type);
    }

    public String getType() {
        return type;
    }

    private String generateCode(String type) {
        char typeChar = type.toUpperCase().charAt(0);//将类型的第一个字母转为大写并作为第一个字符
        //char typeChar=type.equalsIgnoreCase("Car")?'C':'V';
        char randomLetter = (char) ('A' + (int) (Math.random() * 26));
        char randomNum = (char) ('0' + (int) (Math.random() * 10));
        return "" + typeChar + randomLetter + randomNum;
    }

    private String generateNum(String type) {
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
}