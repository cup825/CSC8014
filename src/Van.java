public final class Van extends AbstractVehicle {
    private boolean needCheck;

    public Van() {
        super("Van");
        needCheck = false;
    }

    public boolean needCheck() {
        return needCheck;
    }

    public void setCheck(boolean flag) {
        needCheck = flag;
    }
}
