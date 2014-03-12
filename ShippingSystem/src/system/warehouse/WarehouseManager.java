package system.warehouse;

public interface WarehouseManager {

    public Package packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);
}
