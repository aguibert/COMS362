/**
 * 
 */
package system.invoice;

import java.util.Set;

import system.SystemPackage;

public interface Invoice {
    /** Iteration 1 **/
    public enum INVOICE_STATE {
        OPEN,
        IN_PROGRESS,
        COMPLETE,
        CANCELLED
    }

    public SystemPackage getPackage(int packageID);

    public boolean setStatus(INVOICE_STATE newState);

    public INVOICE_STATE getStatus();

    public boolean addPackage(int p);

    public int getID();

    public String getCustomerName();

    /** Iteration 2 **/

    /**
     * Employee provides package ID. Invoice associated with this package is selected
     * and modified as necessary (such as invoice completed). If package is not on
     * the truck, returns false.
     */
    public boolean deliverPackage(int pakcageID);

    public Set<Integer> getPackages();

    public boolean markDamaged();

    /** Iteration 3 **/
}
