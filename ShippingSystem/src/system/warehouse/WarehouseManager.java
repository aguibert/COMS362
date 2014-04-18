package system.warehouse;

import system.SystemPackage;

public interface WarehouseManager {

    public int createWarehouse();

    public SystemPackage packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, int packageID);

    public Warehouse getWareHouse(int warehouseID);
}
