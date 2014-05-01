package system.truck;

import java.util.List;

import system.SystemPackage;

public interface TruckController {
    /** Iteration 1 **/
    public int createTruck();

    public String createRoute(int truckID);

    public String refreshTruckRoute(int truckID);

    public List<SystemPackage> getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int truckID);

    public boolean removePackageFromTruck(int packageID, int truckID);

    public List<Truck> getTrucks(String string);

    public boolean setTruckState(int truckID, String string);

    public Truck getTruck(int truckID);

    /** Iteration 2 **/

    public boolean returnTruckToWarehouse(int truckID, int warehouseID);

    public boolean releaseTruck(int truckID, int warehouseID);

    /** Iteration 3 **/
}
