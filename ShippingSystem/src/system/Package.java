/**
 * 
 */
package system;

import system.invoice.Invoice;

public interface Package {

    public enum PACKAGE_STATE {
        WAREHOUSE,
        ON_TRUCK,
        DELIVERED,
        DAMAGED,
        CANCELLED
    }

    public int getPackageID();

    public Invoice getInvoice();
}
