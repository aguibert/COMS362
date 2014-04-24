package system.truck;

import java.util.List;

import system.SystemPackage;

public interface TruckController {
    /** Iteration 1 **/
    public int createTruck();

    public Route createRoute(int truckID);

    public boolean refreshTruckRoute(int truckID);

    public List<SystemPackage> getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int truckID);

    public boolean removePackageFromTruck(int packageID, int truckID);

    public List<Truck> getTrucks(String string);

    public boolean setTruckState(int truckID, String string);

    public Truck getTruck(int truckID);

    /** Iteration 2 **/
}
