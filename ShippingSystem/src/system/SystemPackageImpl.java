/**
 * 
 */
package system;

import java.io.Serializable;

/**
 * @author Jon
 */
public class SystemPackageImpl implements SystemPackage, Serializable {

    private static final long serialVersionUID = -6805193908720493965L;
    final private int packageID;
    private int invoiceID;
    private int warehouseID;
    private int truckID;
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
        this.truckID = -1;
    }

    @Override
    public boolean setState(PACKAGE_STATE newState) {
        if (newState == PACKAGE_STATE.DELIVERED && state == PACKAGE_STATE.DELIVERED) {
            System.out.println("ERROR: Package " + packageID + " is already marked as DELIVERED.");
            return false;
        }

        if (newState == PACKAGE_STATE.DELIVERED && state != PACKAGE_STATE.ON_TRUCK) {
            System.out.println("ERROR: Package " + packageID + " must be put on truck before it is marked delivered.");
            return false;
        }
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

    @Override
    public int getTruck() {
        return truckID;
    }

    @Override
    public boolean setTruck(int truckID) {
        this.truckID = truckID;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Package: ");
        sb.append(packageID);
        sb.append("\n  Invoice=");
        sb.append(invoiceID);
        if (state == PACKAGE_STATE.WAREHOUSE) {
            sb.append("  Warehouse=");
            sb.append(warehouseID);
        }
        sb.append("\n  State=");
        sb.append(state);
        sb.append("\n  Customer=");
        sb.append(customerName);
        sb.append("  Dest=");
        sb.append(destAddr);
        sb.append("\n  Weight=");
        sb.append(weight);
        sb.append("  Cost=");
        sb.append(shippingCost);
        sb.append("  Location=");
        sb.append(currentLocation);

        return sb.toString();
    }

}
