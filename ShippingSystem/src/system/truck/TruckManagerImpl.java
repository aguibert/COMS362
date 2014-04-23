/**
 * 
 */
package system.truck;

import java.util.List;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.truck.Truck.TRUCK_STATE;

/**
 * @author Lucas
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
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        return t.getPackages();
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#addPackageToTruck(java.lang.String, int)
     */
    @Override
    public boolean addPackageToTruck(int packageID, int truckID) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        if (t == null) {
            return false;
        }
        return t.addPackage(packageID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#removePackageFromTruck(java.lang.String, int)
     */
    @Override
    public boolean removePackageFromTruck(int packageID, int truckID) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        if (t == null) {
            return false;
        }
        return t.removePackage(packageID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#getTrucks(system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public List<Truck> getTrucks(TRUCK_STATE state) {
        DatabaseSupport db = new DatabaseSupportImpl();
        return db.getTrucks(state);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#setTruckState(int, system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public boolean setTruckState(int truckID, TRUCK_STATE newState) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        return t.setState(newState);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.TruckManager#getTruck(int)
     */
    @Override
    public Truck getTruck(int truckID) {
        DatabaseSupport db = new DatabaseSupportImpl();
        return db.getTruck(truckID);
    }

}
