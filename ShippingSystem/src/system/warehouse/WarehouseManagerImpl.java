/**
 * 
 */
package system.warehouse;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;

/**
 * @author Jon
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

    @Override
    public int createWarehouse() {
        Warehouse w = new WarehouseImpl(nextWarehouse++);
        return w.getID();
    }

    @Override
    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        Warehouse w = new DatabaseSupportImpl().getWareHouse(warehouseID);
        SystemPackage p = w.packageArrival(invoiceID, customerName, destinationAddress, weight, shippingCost);
        DatabaseSupport dbs = new DatabaseSupportImpl();
        dbs.putPackage(p);
        dbs.getInvoice(invoiceID).addPackage(p.getPackageID());
        return p;
    }

    @Override
    public boolean packageDeparture(int warehouseID, int packageID) {
        Warehouse w = new DatabaseSupportImpl().getWareHouse(warehouseID);
        return w.packageDeparture(packageID);
    }

    @Override
    public Warehouse getWarehouse(int warehouseID) {
        return new DatabaseSupportImpl().getWareHouse(warehouseID);
    }

}
