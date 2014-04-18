/**
 * 
 */
package system.invoice;

import java.util.List;

import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.invoice.Invoice.INVOICE_STATE;

/**
 * @author Andrew
 */
public class InvoiceManagerImpl implements InvoiceManager
{
    private int nextInvoiceID = 0;

    private static volatile InvoiceManagerImpl singleton = null;

    public static synchronized InvoiceManagerImpl getInstance() {
        if (singleton == null)
            singleton = new InvoiceManagerImpl();
        return singleton;
    }

    private InvoiceManagerImpl() {}

    @Override
    public int createInvoice(String companyName, String customerName, String customerAddress, String customerPhone, int numPackages, String invoiceDescription) {
        Invoice i = new InvoiceImpl(nextInvoiceID++, companyName, customerName, customerAddress, customerPhone, numPackages, invoiceDescription);

        return i.getID();
    }

    @Override
    public boolean cancelInvoice(int invoiceID) {
        Invoice i = new DatabaseSupportImpl().getInvoice(invoiceID);

        if (i == null)
            return false;

        return i.setStatus(INVOICE_STATE.CANCELLED);
    }

    @Override
    public List<Invoice> getCustomerInvoices(String customerName) {
        return new DatabaseSupportImpl().getInvoiceByName(customerName);
    }

    @Override
    public boolean addPackageToInvoice(int packageID, int invoiceID) {

        Invoice i = new DatabaseSupportImpl().getInvoice(invoiceID);

        if (i == null)
            return false;

        return i.addPackage(packageID);
    }

    @Override
    public Invoice getInvoice(int invoiceID) {
        return new DatabaseSupportImpl().getInvoice(invoiceID);
    }

    @Override
    public SystemPackage getPackage(int packageID) {
        return new DatabaseSupportImpl().getPackage(packageID);
    }

}
