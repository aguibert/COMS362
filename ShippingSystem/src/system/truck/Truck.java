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

    public boolean setLocation(String location);

    public Route createTruckRoute();

    public Route refreshTruckRoute();

    public boolean addPackage(int packageID);

    public boolean removePackage(int packageID);

    public TRUCK_STATE getState();

    public boolean setState(TRUCK_STATE newState);
}
