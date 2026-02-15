public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    private final String vehicleType;//...又改了
    private final int distanceRequirement;
    private int currentMileage; //这里不需要设置为final
    private boolean isHired;

    public AbstractVehicle(String vehicleType) {
        this.vehicleType = vehicleType;
        id = VehicleID.getInstance(vehicleType);//仅能通过工厂方法创建ID对象
        currentMileage = 0;
        isHired = false;
        distanceRequirement = vehicleType.equalsIgnoreCase(VehicleID.CAR) ? 10000 : 5000;
    }

    @Override
    public VehicleID getVehicleID() {
        return id;
    }

    @Override
    public String getVehicleType() {
        //return id.getType();
        return vehicleType;
    }

    //获取是否被租
    @Override
    public boolean isHired() {
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
        if (mileage < 0)//能否=0?
            throw new IllegalArgumentException("Mileage cannot be negative.");
        this.currentMileage = mileage;
    }

    @Override
    public void setHired(boolean flag) {
        this.isHired = flag;
    }

    @Override
    public boolean performServiceIfDue() {
        if (currentMileage >= distanceRequirement) {
            currentMileage = 0;
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        return vehicleType + " " + id.toString();
    }
}

