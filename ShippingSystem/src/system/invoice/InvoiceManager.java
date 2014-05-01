package system.invoice;

import java.util.List;
import java.util.Set;

import system.SystemPackage;
import system.invoice.Invoice.INVOICE_STATE;

public interface InvoiceManager {
    /** Iteration 1 **/
    public int createInvoice(String companyName, String customerName, String customerAddress, String customerPhone,
                             int numPackages, String invoiceDescription);

    public boolean cancelInvoice(int invoiceID);

    public List<Invoice> getCustomerInvoices(String customerName);

    public boolean addPackageToInvoice(int packageID, int invoiceID);

    public Invoice getInvoice(int invoiceID);

    public SystemPackage getPackage(int packageID);

    /** Iteration 2 **/

    /**
     * Selects all invoices from invoice table and returns a set of
     * invoices matching the state filter.
     */
    public Set<Invoice> getInvoiceByState(INVOICE_STATE state);

    /**
     * Employee provides package ID. Invoice associated with this package is selected
     * and modified as necessary (such as invoice completed). If package is not on
     * the truck, returns false.
     */
    public boolean deliverPackage(int pakcageID, int truckID);

    public boolean markDamaged(int packageID, int invoiceID);

    public String getPkgLoc(int packageID, int invoiceID);
}
