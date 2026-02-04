public final class Van implements Vehicle {
    private final VehicleID id;
    private final String vehicleType = "Van";
    private final int distanceRequirement = 5000;
    private int currentMileage; //这里不需要设置为final

    public Van() {
        //构造函数只用写这两行吗？
        id = new VehicleID("Van");
        currentMileage = 0;
    }

    @Override
    public VehicleID getVehicleID() {
        return id;
    }

    @Override
    public String getVehicleType() {
        return "Van";
    }

    @Override
    public boolean isHired() {
        return false;
    }

    @Override
    public int getDistanceRequirement() {
        return distanceRequirement;
    }

    @Override
    public int getCurrentMileage() {
        return currentMileage;
    }

    @Override
    public void setCurrentMileage(int mileage) {
        this.currentMileage = mileage;
    }

    @Override
    public boolean performServiceIfDue() {
        if (currentMileage >= distanceRequirement) {
            currentMileage = 0;
            return true;
        } else
            return false;
    }

    //待写
    public boolean needCheck() {
        return false;
    }


    //一种适用于货车的方法，如果货车需要检查则返回true，否则返回false。
    // 有些货车用于商业用途，因此从法律上讲，除了常规保养外，还需要定期进行安全检查，
    // 以确保它们在再次出租前符合所有安全和法律要求（见任务4）。
}
