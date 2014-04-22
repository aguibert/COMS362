/**
 * 
 */
package system.truck;

import java.util.List;

import system.SystemPackage;
import system.truck.Truck.TRUCK_STATE;

/**
 * @author Lucas
 */
public class TruckControllerImpl implements TruckController
{
    TruckManager tm = TruckManagerImpl.getInstance();

    @Override
    public int createTruck() {
        return tm.createTruck();
    }

    @Override
    public Route createRoute(int truckID) {
        return tm.createRoute(truckID);
    }

    @Override
    public boolean refreshTruckRoute(int truckID) {
        return tm.refreshTruckRoute(truckID);
    }

    @Override
    public List<SystemPackage> getPackagesOnTruck(int truckID) {
        return tm.getPackagesOnTruck(truckID);
    }

    @Override
    public boolean addPackageToTruck(int packageID, int truckID) {
        return tm.addPackageToTruck(packageID, truckID);
    }

    @Override
    public boolean removePackageFromTruck(int packageID, int truckID) {
        return tm.removePackageFromTruck(packageID, truckID);
    }

    @Override
    public List<Truck> getTrucks(TRUCK_STATE state) {
        return tm.getTrucks(state);
    }

    @Override
    public boolean setTruckState(int truckID, TRUCK_STATE newState) {
        return tm.setTruckState(truckID, newState);
    }

    @Override
    public Truck getTruck(int truckID) {
        return tm.getTruck(truckID);
    }
}