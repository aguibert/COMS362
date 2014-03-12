/**
 * 
 */
package system.truck;


/**
 * @author Andrew
 * 
 */
public interface Truck {

    public enum TRUCK_STATE {
        ALL_STATES,
        AVAILABLE,
        IN_ROUTE,
        BROKEN,
        LOADING
    }

    public boolean refreshTruckRoute();

    public boolean addPackageToTruck(int packageID);

    public TRUCK_STATE getState();

    public boolean setTruckState(TRUCK_STATE newState);
}
