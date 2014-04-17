package system.warehouse;

public interface WarehouseManager {

    public int createWarehouse();

    public Package packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, String packageID);

    public Warehouse getWareHouse(int warehouseID);
}
