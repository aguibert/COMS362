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

    public String getLocation();

    public Route createTruckRoute();

    public Route refreshTruckRoute();

    public boolean addPackageToTruck(int packageID);

    public TRUCK_STATE getState();

    public boolean setTruckState(TRUCK_STATE newState);
}
