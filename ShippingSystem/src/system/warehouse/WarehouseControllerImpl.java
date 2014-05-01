/**
 * 
 */
package system.warehouse;

import java.util.Set;

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
    public boolean packageDeparture(int warehouseID, int packageID, int truckID) {
        return wm.packageDeparture(warehouseID, packageID, truckID);
    }

    @Override
    public Warehouse getWarehouse(int warehouseID) {
        return wm.getWarehouse(warehouseID);
    }

    @Override
    public Set<Warehouse> getAll() {
        return wm.getAll();
    }

    @Override
    public boolean setLocation(int warehouseID, String newLocation) {
        return wm.setLocation(warehouseID, newLocation);
    }
}
