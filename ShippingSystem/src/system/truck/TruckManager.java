package system.truck;

import java.util.List;

import system.SystemPackage;
import system.truck.Truck.TRUCK_STATE;

public interface TruckManager {

    public int createTruck();

    public Route createRoute(int truckID);

    public boolean refreshTruckRoute(int truckID);

    public List<SystemPackage> getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int truckID);

    public boolean removePackageFromTruck(int packageID, int truckID);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean setTruckState(int truckID, TRUCK_STATE newState);

    public Truck getTruck(int truckID);
}
