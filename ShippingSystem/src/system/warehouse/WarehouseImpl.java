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
    private final int ID;

    public WarehouseImpl(int _id) {
        ID = _id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#packageArrival(java.lang.String, java.lang.String, double, double)
     */
    @Override
    public SystemPackage packageArrival(int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        SystemPackage p = new SystemPackageImpl(ID, invoiceID, customerName, destinationAddress, weight, shippingCost, SystemPackage.PACKAGE_STATE.WAREHOUSE);
        packages.add(p.getPackageID());
        return p;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#packageDeparture(java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#getID()
     */
    @Override
    public int getID() {
        return this.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#getPackages()
     */
    @Override
    public Set<Integer> getPackages() {
        return packages;
    }

}
