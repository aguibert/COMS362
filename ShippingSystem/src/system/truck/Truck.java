/**
 * 
 */
package system.truck;

import java.util.List;

public interface Truck {
    /** Iteration 1 **/
    public enum TRUCK_STATE {
        ALL_STATES,
        AVAILABLE,
        IN_ROUTE,
        BROKEN,
        LOADING
    }

    public String getLocation();

    public boolean setLocation(String location);

    public String createTruckRoute();

    public boolean refreshTruckRoute();

    public boolean addPackage(int packageID);

    public boolean removePackage(int packageID);

    public List<Integer> getPackages();

    public TRUCK_STATE getState();

    public boolean setState(TRUCK_STATE newState);

    public int getID();

    /** Iteration 2 **/
}
