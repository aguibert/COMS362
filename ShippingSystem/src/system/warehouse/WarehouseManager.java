package system.warehouse;

public interface WarehouseManager {

    public Package packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, int invoiceID, int packageID);

    public Warehouse getWareHouse(int warehouseID);
}
