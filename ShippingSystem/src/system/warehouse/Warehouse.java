/**
 * 
 */
package system.warehouse;

import java.util.List;

import system.Package.PACKAGE_STATE;

/**
 * @author Andrew
 * 
 */
public interface Warehouse {

    public Package packageArrival(String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int packageID, int invoiceID);

    public List<Package> getPackages(PACKAGE_STATE state);
}
