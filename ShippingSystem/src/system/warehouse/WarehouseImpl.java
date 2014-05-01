/**
 * 
 */
package system.warehouse;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackageImpl;

/**
 * @author Jon
 */
public class WarehouseImpl implements Warehouse, Serializable
{
    private static final long serialVersionUID = 8153959552398319696L;
    public Set<Integer> packages = new HashSet<Integer>();
    public Set<Integer> trucks = new HashSet<Integer>();
    public String location = "";
    private final int ID;

    public WarehouseImpl(int _id) {
        ID = _id;
    }

    @Override
    public SystemPackage packageArrival(int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        SystemPackage p = new SystemPackageImpl(ID, invoiceID, customerName, destinationAddress, weight, shippingCost, SystemPackage.PACKAGE_STATE.WAREHOUSE);
        this.addPackage(p.getPackageID());
        return p;
    }

    @Override
    public boolean packageDeparture(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage p = dbs.getPackage(packageID);
        if (p == null) {
            return false;
        }
        packages.remove(packageID);
        p.setState(SystemPackage.PACKAGE_STATE.ON_TRUCK);
        return dbs.putPackage(p);
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public Set<Integer> getPackages() {
        return packages;
    }

    @Override
    public String toString() {
        String string = null;
        string = "Warehouse:  " + ID + "\n  Location:  " + location + "\n  Packages:  " + packages + "\n  Trucks:  " + trucks;
        return string;
    }

    @Override
    public boolean addPackage(int packageID) {
        return packages.add(packageID);
    }

    @Override
    public void addTruck(int truckID) {
        trucks.add(truckID);
    }

    @Override
    public boolean removeTruck(int truckID) {
        if (!trucks.contains(truckID)) {
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean setLocation(String newLocation) {
        this.location = newLocation;
        return true;
    }
}
