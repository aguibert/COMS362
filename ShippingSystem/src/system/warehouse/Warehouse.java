/**
 * 
 */
package system.warehouse;

import java.util.Set;

import system.SystemPackage;

/**
 * @author Andrew
 * 
 */
public interface Warehouse {

    public SystemPackage packageArrival(int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int packageID);

    public int getID();

    public Set<Integer> getPackages();
}
