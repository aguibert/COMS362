/**
 * 
 */
package system.invoice;

import java.util.List;

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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Invoice> getCustomerInvoices(String customerName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addPackageToInvoice(String packageID) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Invoice getInvoice(int invoiceID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Package getPackage(String packageID) {
        // TODO Auto-generated method stub
        return null;
    }

}
