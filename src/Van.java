/**
 * Van - Represents a van in the vehicle rental system.
 * Includes additional properties for inspection requirements.
 */
public final class Van extends AbstractVehicle {
    /**
     * Indicates whether the van requires an inspection.
     */
    private boolean needCheck;

    /**
     * Constructor for Van.
     * Initializes the van and sets the inspection requirement to false.
     */
    public Van() {
        super("Van");
        needCheck = false;
    }

    /**
     * Checks if the van requires an inspection.
     *
     * @return True if the van needs an inspection, false otherwise.
     */
    public boolean needCheck() {
        return needCheck;
    }

    /**
     * Sets the inspection requirement for the van.
     *
     * @param flag True to mark the van as needing an inspection, false otherwise.
     */
    public void setCheck(boolean flag) {
        needCheck = flag;
    }
}
