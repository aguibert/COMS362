/**
 * 
 */
package system.truck;

import java.util.ArrayList;
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
        DatabaseSupport db = new DatabaseSupportImpl();
        int tid = db.getNextID('t');
        Truck t = new TruckImpl(tid);
        new DatabaseSupportImpl().putTruck(t);
        return tid;
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
        if (t == null)
            return null;
        List<SystemPackage> packs = new ArrayList<>();
        for (int pkg : t.getPackages()) {
            packs.add(db.getPackage(pkg));
        }
        return packs;
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
        if (t.addPackage(packageID) == false)
            return false;
        return db.putTruck(t);
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
