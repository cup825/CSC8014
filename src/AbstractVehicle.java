public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    //private final String vehicleType;
    private final int distanceRequirement;
    private int currentMileage; //这里不需要设置为final
    private boolean isHired;

    public AbstractVehicle(String vehicleType) {
        id = new VehicleID(vehicleType);//自动分配id,此时已经检验是否为Car/Van
        currentMileage = 0;
        isHired = false;
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

    //是否被租要检查，还是直接获取成员变量？
    @Override
    public boolean isHired() {
//        Collection<HashSet<Vehicle>> values = VehicleManager.hiredVehicles.values();
//        for (HashSet<Vehicle> hs : values) {
//            if (hs.contains(this))
//                return true;
//        }
//        return false;
        return isHired;
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
        if (mileage < 0)//能否=0
            throw new IllegalArgumentException("Mileage cannot be negative.");
        this.currentMileage = mileage;
    }

    @Override
    public void setHired(boolean flag) {
        this.isHired = flag;
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

