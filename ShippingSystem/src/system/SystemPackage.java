/**
 * 
 */
package system;

import system.invoice.Invoice;

public interface SystemPackage {

    public enum PACKAGE_STATE {
        WAREHOUSE,
        ON_TRUCK,
        DELIVERED,
        DAMAGED,
        CANCELLED
    }

    public boolean setState(PACKAGE_STATE newState);

    public boolean getState(PACKAGE_STATE newState);

    public int getPackageID();

    public boolean setInvoice(Invoice i);

    public Invoice getInvoice();

    public boolean setLocation(String location);

    public String getLocation();
}
