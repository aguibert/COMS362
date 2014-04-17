/**
 * 
 */
package system.truck;

import java.util.List;

import system.SystemPackage;
import system.truck.Truck.TRUCK_STATE;

/**
 * @author Andrew
 * 
 */
public class TruckManagerImpl implements TruckManager
{
    static int nextTruckID = 0;

    private static volatile TruckManagerImpl singleton = null;

    public static synchronized TruckManager getInstance() {
        if (singleton == null)
            singleton = new TruckManagerImpl();
        return singleton;
    }

    private TruckManagerImpl() {}

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#createTruck()
     */
    @Override
    public int createTruck() {
        Truck t = new TruckImpl(nextTruckID++);
        return t.getID();
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#createRoute(int)
     */
    @Override
    public Route createRoute(int truckID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#refreshTruckRoute(int)
     */
    @Override
    public boolean refreshTruckRoute(int truckID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#getPackagesOnTruck(int)
     */
    @Override
    public List<SystemPackage> getPackagesOnTruck(int truckID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#addPackageToTruck(java.lang.String, int)
     */
    @Override
    public boolean addPackageToTruck(String packageID, int truckID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#removePackageFromTruck(java.lang.String, int)
     */
    @Override
    public boolean removePackageFromTruck(String packageID, int truckID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#getTrucks(system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public List<Truck> getTrucks(TRUCK_STATE state) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#setTruckState(int, system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public boolean setTruckState(int truckID, TRUCK_STATE newState) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#getTruck(int)
     */
    @Override
    public Truck getTruck(int truckID) {
        // TODO Auto-generated method stub
        return null;
    }

}
