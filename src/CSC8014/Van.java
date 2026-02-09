package CSC8014;

public final class Van extends AbstractVehicle {
    private boolean needCheck;

    public Van() {
        super("CSC8014.Van");
    }

    public boolean needCheck() {
//        return duration >= 10;
        return needCheck;
    }

    public void setCheck(boolean flag) {
        needCheck = flag;
    }

//一种适用于货车的方法，如果货车需要检查则返回true，否则返回false。
// 有些货车用于商业用途，因此从法律上讲，除了常规保养外，还需要定期进行安全检查，
// 以确保它们在再次出租前符合所有安全和法律要求（见任务4）。

}
