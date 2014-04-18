/**
 * 
 */
package system;

import system.invoice.Invoice;

/**
 * @author Jon
 * 
 */
public class SystemPackageImpl implements SystemPackage {

    private static int nextID = 0;
    private int currentID;
    private int currentInvoiceID;
    private int currentWarehouseID;
    private String currentCustomerName;
    private String currentDestinationAddress;
    private double currentWeight;
    private double currentShippingCost;
    private PACKAGE_STATE currentState;
    private String currentLocation;

    public SystemPackageImpl(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost, PACKAGE_STATE state) {
        currentID = nextID++;
        currentInvoiceID = invoiceID;
        currentWarehouseID = warehouseID;
        currentCustomerName = customerName;
        currentDestinationAddress = destinationAddress;
        currentWeight = weight;
        currentShippingCost = shippingCost;
        currentState = state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#setState(system.SystemPackage.PACKAGE_STATE)
     */
    @Override
    public boolean setState(PACKAGE_STATE newState) {
        currentState = newState;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#getState(system.SystemPackage.PACKAGE_STATE)
     */
    @Override
    public PACKAGE_STATE getState() {
        return currentState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#getPackageID()
     */
    @Override
    public int getPackageID() {
        return currentID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#getInvoice()
     */
    @Override
    public Invoice getInvoice() {
        return new DatabaseSupportImpl().getInvoice(currentInvoiceID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#setLocation(java.lang.String)
     */
    @Override
    public boolean setLocation(String location) {
        currentLocation = location;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.SystemPackage#getLocation()
     */
    @Override
    public String getLocation() {
        return currentLocation;
    }

}
