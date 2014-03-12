package system.truck;

import java.util.List;


public interface TruckManager {

    public enum TRUCK_STATE {
        ALL_STATES,
        AVAILABLE,
        IN_ROUTE,
        BROKEN,
        LOADING
    }

    public Route createRoute(Package[] packages);

    public Package[] getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(Package p, Truck t);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean refreshTruckRoute(int truckID);
}
