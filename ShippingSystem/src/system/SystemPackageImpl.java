/**
 * 
 */
package system;

import system.invoice.Invoice;

/**
 * @author Jon
 */
public class SystemPackageImpl implements SystemPackage {

    final private int currentID;
    private int currentInvoiceID;
    private int currentWarehouseID;
    private String currentCustomerName;
    private String currentDestinationAddress;
    private double currentWeight;
    private double currentShippingCost;
    private PACKAGE_STATE currentState;
    private String currentLocation;

    public SystemPackageImpl(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost, PACKAGE_STATE state) {
        currentID = new DatabaseSupportImpl().getNextID('p');
        currentInvoiceID = invoiceID;
        currentWarehouseID = warehouseID;
        currentCustomerName = customerName;
        currentDestinationAddress = destinationAddress;
        currentWeight = weight;
        currentShippingCost = shippingCost;
        currentState = state;
    }

    @Override
    public boolean setState(PACKAGE_STATE newState) {
        currentState = newState;
        return true;
    }

    @Override
    public PACKAGE_STATE getState() {
        return currentState;
    }

    @Override
    public int getPackageID() {
        return currentID;
    }

    @Override
    public Invoice getInvoice() {
        return new DatabaseSupportImpl().getInvoice(currentInvoiceID);
    }

    @Override
    public boolean setLocation(String location) {
        currentLocation = location;
        return true;
    }

    @Override
    public String getLocation() {
        return currentLocation;
    }
}
