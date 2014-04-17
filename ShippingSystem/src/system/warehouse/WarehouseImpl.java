/**
 * 
 */
package system.warehouse;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;

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
    public SystemPackage packageArrival(String customerName, String destinationAddress, double weight, double shippingCost) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#packageDeparture(java.lang.String)
     */
    @Override
    public boolean packageDeparture(String packageID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.Warehouse#getPackages(system.Package.PACKAGE_STATE)
     */
    @Override
    public List<SystemPackage> getPackages(PACKAGE_STATE state) {
        // TODO Auto-generated method stub
        return null;
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
