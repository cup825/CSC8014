public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    //private final String vehicleType;
    private final int distanceRequirement;
    private int currentMileage; //这里不需要设置为final

    public AbstractVehicle(String vehicleType) {
        id = new VehicleID(vehicleType);//自动分配id,此时已经检验是否为Car/Van
        currentMileage = 0;
        //this.vehicleType = vehicleType;
        distanceRequirement = vehicleType.equalsIgnoreCase("Car") ? 10000 : 5000;
    }

    @Override
    public VehicleID getVehicleID() {
        return id;
    }

    @Override
    public String getVehicleType() {
        return id.getType();
    }

    //这个需要写成员变量吗？
    @Override
    public boolean isHired() {
        //VehicleManager.hiredVehicles
        return false;//待改
    }

    @Override
    public int getDistanceRequirement() {
        return distanceRequirement;
    }

    //一种获取/设置自上次保养以来累积的当前里程的方法。
    @Override
    public int getCurrentMileage() {
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
        if (currentMileage >= distanceRequirement) {
            currentMileage = 0;
            return true;
        } else
            return false;
        //return currentMileage >= distanceRequirement;
    }

}

