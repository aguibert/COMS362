package system.warehouse;

import java.util.Set;

import system.SystemPackage;

public interface WarehouseManager {
    /** Iteration 1 **/
    public int createWarehouse();

    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, int packageID, int truckID);

    public Warehouse getWarehouse(int warehouseID);

    /** Iteration 2 **/

    boolean addTruck(int truckID, int warehouseID);

    boolean removeTruck(int truckID, int warehouseID);

    /** Iteration 3 **/

    public Set<Warehouse> getAll();

    public boolean setLocation(int warehouseID, String newLocation);
}
