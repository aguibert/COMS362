/**
 * 
 */
package system.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;
import system.SystemPackageImpl;

/**
 * @author Andrew
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
        p.setState(SystemPackage.PACKAGE_STATE.ON_TRUCK);
        return dbs.putPackage(p);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#getPackages(system.Package.PACKAGE_STATE)
     */
    @Override
    public List<SystemPackage> getPackages(PACKAGE_STATE state) {
        List<SystemPackage> ps = new ArrayList<SystemPackage>();
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Iterator<Integer> iter = packages.iterator();
        for (int i = 0; i < packages.size(); i++) {
            ps.add(dbs.getPackage(iter.next()));
        }
        return ps;
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
