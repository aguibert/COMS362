/**
 * 
 */
package system.warehouse;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;

/**
 * @author Andrew
 * 
 */
public class WarehouseManagerImpl implements WarehouseManager
{
    static int nextWarehouse = 0;

    private static volatile WarehouseManagerImpl singleton = null;

    public static synchronized WarehouseManager getInstance() {
        if (singleton == null)
            singleton = new WarehouseManagerImpl();
        return singleton;
    }

    private WarehouseManagerImpl() {}

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#createWarehouse()
     */
    @Override
    public int createWarehouse() {
        Warehouse w = new WarehouseImpl(nextWarehouse++);
        return w.getID();
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#packageArrival(int, int, java.lang.String, java.lang.String, double, double)
     */
    @Override
    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        Warehouse w = new DatabaseSupportImpl().getWareHouse(warehouseID);
        SystemPackage p = w.packageArrival(invoiceID, customerName, destinationAddress, weight, shippingCost);
        DatabaseSupport dbs = new DatabaseSupportImpl();
        dbs.putPackage(p);
        dbs.getInvoice(invoiceID).addPackage(p.getPackageID());
        return p;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#packageDeparture(int, java.lang.String)
     */
    @Override
    public boolean packageDeparture(int warehouseID, int packageID) {
        Warehouse w = new DatabaseSupportImpl().getWareHouse(warehouseID);
        return w.packageDeparture(packageID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#getWareHouse(int)
     */
    @Override
    public Warehouse getWareHouse(int warehouseID) {
        return new DatabaseSupportImpl().getWareHouse(warehouseID);
    }

}
