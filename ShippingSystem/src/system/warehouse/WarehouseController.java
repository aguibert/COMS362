package system.warehouse;

public interface WarehouseController {

    /** Returns generated package ID */
    public int packageArrival(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);
}
