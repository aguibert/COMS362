/**
 * 
 */
package system.warehouse;

import java.util.List;
import java.util.Set;

import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;

/**
 * @author Andrew
 * 
 */
public interface Warehouse {

    public SystemPackage packageArrival(int invoiceID, String customerName, String destinationAddress, double weight, double shippingCost);

    public boolean packageDeparture(int packageID);

    public List<SystemPackage> getPackages(PACKAGE_STATE state);

    public int getID();

    public Set<Integer> getPackages();
}
