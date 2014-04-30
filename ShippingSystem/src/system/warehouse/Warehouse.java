/**
 * 
 */
package system.warehouse;

import java.util.Set;

import system.SystemPackage;

public interface Warehouse {
    /** Iteration 1 **/
    public SystemPackage packageArrival(int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int packageID);

    public int getID();

    public Set<Integer> getPackages();

    /** Iteration 2 **/
    public boolean addPackage(int packageID);
}
