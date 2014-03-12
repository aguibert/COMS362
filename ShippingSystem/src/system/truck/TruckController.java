package system.truck;

import java.util.List;

import system.truck.Truck.TRUCK_STATE;

public interface TruckController {

    public Route createRoute(int truckID);

    public boolean refreshTruckRoute(int truckID);

    public List<Package> getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(String packageID, int truckID);

    public boolean removePackageFromTruck(String packageID, int truckID);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean setTruckState(int truckID, TRUCK_STATE newState);

    public Truck getTruck(int truckID);
}
