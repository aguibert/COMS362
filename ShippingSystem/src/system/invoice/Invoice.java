/**
 * 
 */
package system.invoice;

import system.SystemPackage;

/**
 * @author Andrew
 * 
 */
public interface Invoice {

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

}
