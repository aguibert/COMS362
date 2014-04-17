/**
 * 
 */
package system.warehouse;

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
    public Package packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#packageDeparture(int, java.lang.String)
     */
    @Override
    public boolean packageDeparture(int warehouseID, String packageID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.warehouse.WarehouseManager#getWareHouse(int)
     */
    @Override
    public Warehouse getWareHouse(int warehouseID) {
        // TODO Auto-generated method stub
        return null;
    }

}
