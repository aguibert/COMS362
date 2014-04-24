/**
 * 
 */
package system;

/**
 * @author Jon
 */
public class SystemPackageImpl implements SystemPackage {

    final private int packageID;
    private int invoiceID;
    private int warehouseID;
    private String customerName;
    private String destAddr;
    private double weight;
    private double shippingCost;
    private PACKAGE_STATE state;
    private String currentLocation;

    public SystemPackageImpl(int warehouseID, int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost, PACKAGE_STATE state) {
        packageID = new DatabaseSupportImpl().getNextID('p');
        this.invoiceID = invoiceID;
        this.warehouseID = warehouseID;
        this.customerName = customerName;
        this.destAddr = destinationAddress;
        this.weight = weight;
        this.shippingCost = shippingCost;
        this.state = state;
    }

    @Override
    public boolean setState(PACKAGE_STATE newState) {
        state = newState;
        return true;
    }

    @Override
    public PACKAGE_STATE getState() {
        return state;
    }

    @Override
    public int getPackageID() {
        return packageID;
    }

    @Override
    public int getInvoice() {
        return invoiceID;
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
