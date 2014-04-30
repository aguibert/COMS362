/**
 * 
 */
package system;

public interface SystemPackage {

    /** Iteration 1 **/

    public enum PACKAGE_STATE {
        WAREHOUSE,
        ON_TRUCK,
        DELIVERED,
        DAMAGED,
        CANCELLED
    }

    public boolean setState(PACKAGE_STATE newState);

    public PACKAGE_STATE getState();

    public int getPackageID();

    public int getInvoice();

    public boolean setLocation(String location);

    public String getLocation();

    /** Iteration 2 **/

    public int getTruck();

    public boolean setTruck(int truckID);
}
