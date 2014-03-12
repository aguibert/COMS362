package system.truck;

import java.util.List;

import system.truck.TruckManager.TRUCK_STATE;

public interface TruckController {
    public boolean createRoute(Package[] packages);

    public Package[] getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int truckID);

    List<Truck> getTrucks(TRUCK_STATE state);

    public boolean refreshTruckRoute(int truckID);
}
