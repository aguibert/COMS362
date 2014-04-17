/**
 * 
 */
package system.truck;

import java.util.List;

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

    public boolean addPackage(String packageID);

    public boolean removePackage(String packageID);

    public List<String> getPackages();

    public TRUCK_STATE getState();

    public boolean setState(TRUCK_STATE newState);

    public int getID();
}
