package system.warehouse;

import java.util.Set;

import system.SystemPackage;

public interface WarehouseController {
    /** Iteration 1 **/
    public int createWarehouse();

    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, int packageID);

    public Warehouse getWarehouse(int warehouseID);

    /** Iteration 2 **/
    public Set<Warehouse> getAll();
}
