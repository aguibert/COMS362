package system.truck;

import java.util.List;

import system.truck.Truck.TRUCK_STATE;

public interface TruckManager {

    public Route createRoute(Package[] packages);

    public Package[] getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int truckID);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean refreshTruckRoute(int truckID);
}
