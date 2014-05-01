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

    @Override
    public int createTruck() {
        DatabaseSupport db = new DatabaseSupportImpl();
        int tid = db.getNextID('t');
        Truck t = new TruckImpl(tid);
        db.putTruck(t);
        return tid;
    }

    @Override
    public String createRoute(int truckID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Truck t = dbs.getTruck(truckID);
        String str = "";
        if (t == null) {
            return null;
        }
        str = t.createTruckRoute();
        dbs.putTruck(t);
        return str;
    }

    @Override
    public boolean refreshTruckRoute(int truckID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Truck t = dbs.getTruck(truckID);
        if (t == null) {
            return false;
        }
        if (t.refreshTruckRoute() == false) {
            return false;
        }
        return dbs.putTruck(t);
    }

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

    @Override
    public boolean addPackageToTruck(int packageID, int truckID) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        if (t == null) {
            return false;
        }
        if (t.addPackage(packageID) == false) {
            return false;
        }
        return db.putTruck(t);
    }

    @Override
    public boolean removePackageFromTruck(int packageID, int truckID) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);
        if (t == null) {
            return false;
        }

        if (t.removePackage(packageID) == false)
            return false;

        return db.putTruck(t);
    }

    @Override
    public List<Truck> getTrucks(TRUCK_STATE state) {
        DatabaseSupport db = new DatabaseSupportImpl();
        return db.getTrucks(state);
    }

    @Override
    public boolean setTruckState(int truckID, TRUCK_STATE newState) {
        DatabaseSupport db = new DatabaseSupportImpl();
        Truck t = db.getTruck(truckID);

        if (t == null)
            return false;

        if (t.setState(newState) == false)
            return false;

        return db.putTruck(t);
    }

    @Override
    public Truck getTruck(int truckID) {
        return new DatabaseSupportImpl().getTruck(truckID);
    }
}
