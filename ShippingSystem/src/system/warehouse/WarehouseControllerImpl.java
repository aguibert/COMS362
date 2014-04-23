/**
 * 
 */
package system.warehouse;

import system.SystemPackage;

/**
 * @author Jon
 */
public class WarehouseControllerImpl implements WarehouseController
{
    WarehouseManager wm = WarehouseManagerImpl.getInstance();

    @Override
    public int createWarehouse() {
        return wm.createWarehouse();
    }

    @Override
    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost) {
        return wm.packageArrival(warehouseID, invoiceID, customerName, destinationAddress, weight, shippingCost);
    }

    @Override
    public boolean packageDeparture(int warehouseID, int packageID) {
        return wm.packageDeparture(warehouseID, packageID);
    }

    @Override
    public Warehouse getWareHouse(int warehouseID) {
        return wm.getWareHouse(warehouseID);
    }
}
