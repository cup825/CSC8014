public final class Van extends AbstractVehicle {
    // int duration;
    public Van() {
        super("Van");
    }

    //待写
    //如果车辆是面包车，且该面包车将被租用 10 天或更长时间(即持续时间>=10≥10，其中持续时间指车辆将被租用的天数)，
    // 则该面包车的状态将变为需要检查。
    public static boolean needCheck(int duration) {
        return duration >= 10;
    }

//一种适用于货车的方法，如果货车需要检查则返回true，否则返回false。
// 有些货车用于商业用途，因此从法律上讲，除了常规保养外，还需要定期进行安全检查，
// 以确保它们在再次出租前符合所有安全和法律要求（见任务4）。

}
