package system.truck;

import java.util.List;

import system.truck.Truck.TRUCK_STATE;

public interface TruckManager {

    public Route createRoute(int truckID);

    public boolean refreshTruckRoute(int truckID);

    public Package[] getPackagesOnTruck(int truckID);

    public boolean addPackageToTruck(int packageID, int invoiceID, int truckID);

    public boolean removePackageFromTruck(int packageID, int invoiceID, int truckID);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean setTruckState(int truckID, TRUCK_STATE newState);

    public Truck getTruck(int truckID);
}
