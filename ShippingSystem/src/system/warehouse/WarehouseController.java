package system.warehouse;

public interface WarehouseController {

    public Package packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int warehouseID, String packageID);

    public Warehouse getWareHouse(int warehouseID);
}
