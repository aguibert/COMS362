/**
 * 
 */
package system.warehouse;

import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.invoice.InvoiceManager;
import system.invoice.InvoiceManagerImpl;

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
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Warehouse w = new WarehouseImpl(dbs.getNextID('w'));
        dbs.putWareHouse(w);
        return w.getID();
    }

    @Override
    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Warehouse w = dbs.getWareHouse(warehouseID);
        if (w == null) {
            return null;
        }
        SystemPackage p = w.packageArrival(invoiceID, customerName, destinationAddress, weight, shippingCost);
        if (dbs.putPackage(p) == false)
            return null;
        InvoiceManager im = InvoiceManagerImpl.getInstance();
        im.addPackageToInvoice(p.getPackageID(), invoiceID);
        if (dbs.putWareHouse(w) == false)
            return null;

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

    @Override
    public boolean addTruck(int truckID, int warehouseID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Warehouse w = dbs.getWareHouse(warehouseID);
        if (w == null)
            return false;

        w.addTruck(truckID);

        return dbs.putWareHouse(w);
    }

    @Override
    public boolean removeTruck(int truckID, int warehouseID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Warehouse w = dbs.getWareHouse(warehouseID);

        if (w == null)
            return false;

        if (!w.removeTruck(truckID)) {
            return false;
        }

        return dbs.putWareHouse(w);
    }

    @Override
    public Set<Warehouse> getAll() {
        return new DatabaseSupportImpl().getAllWarehouse();
    }

    @Override
    public boolean setLocation(int warehouseID, String newLocation) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Warehouse w = dbs.getWareHouse(warehouseID);
        if (w == null)
            return false;
        if (w.setLocation(newLocation) == false)
            return false;
        return dbs.putWareHouse(w);
    }
}
