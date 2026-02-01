public final class Car implements Vehicle {
    private final VehicleID id;
    private final String vehicleType = "Car";
    private final int distanceRequirement = 10000;
    private int currentMileage; //这里不需要设置为final

    public Car() {
        //构造函数只用写这两行吗？
        id = new VehicleID("Car");//自动分配id
        currentMileage = 0;
    }

    @Override
    public VehicleID getVehicleID() {
        return id;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    //这个需要写成员变量吗？
    @Override
    public boolean isHired() {
        return false;
    }

    @Override
    public int getDistanceRequirement() {
        return distanceRequirement;
    }

    //一种获取/设置自上次保养以来累积的当前里程的方法。
    @Override
    public int getCurrentMileage() {
        //return 0;
        return currentMileage;
    }


    @Override
    public void setCurrentMileage(int mileage) {
        this.currentMileage = mileage;
    }

    //一种在服务到期时执行服务的方法。该方法会检查车辆是否已达到所需的保养里程。
    // 如果达到，该方法会重置当前里程并返回true；否则，不采取任何行动并返回false。
    @Override
    public boolean performServiceIfDue() {
        return currentMileage >= distanceRequirement;
        //return false;
    }

}
