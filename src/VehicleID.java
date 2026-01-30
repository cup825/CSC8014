public final class VehicleID {
    //private final String type; type应该写在Vehicle里
    private final String code;
    private final String numCode;

    public VehicleID(String type) {//随机生成
        this.type = type;
        this.code = generateCode();
        this.numCode = generateNum();
    }

    private String generateCode() {
        char char1 = type.charAt(0);
        char char2 = (char) ('A' + (int) (Math.random() * 26));
//        int randomNum = (int) (Math.random() * 10);
        char char3 = (char) ('0' + (int) (Math.random() * 10));
//        return String.valueOf(char1) + char2 + char3;
        return "" + char1 + char2 + char3;
    }

    private String generateNum() {
        //怎么生成奇or偶
        int even = (int) (Math.random() * 500) * 2;
        if (type.equalsIgnoreCase("Car")) //even for cars
            return String.format("%03d", even);
        else if (type.equalsIgnoreCase("Van"))
            return String.format("%03d", (even + 1));
        else
            throw new RuntimeException("xxx");
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