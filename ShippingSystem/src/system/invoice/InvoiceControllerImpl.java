/**
 * 
 */
package system.invoice;

import java.util.List;

import system.SystemPackage;

/**
 * @author aguibert
 * 
 */
public class InvoiceControllerImpl implements InvoiceController
{
    InvoiceManager im = InvoiceManagerImpl.getInstance();

    @Override
    public int createInvoice(String companyName, String customerName, String customerAddress, String customerPhone, int numPackages, String invoiceDescription) {
        return im.createInvoice(companyName, customerName, customerAddress, customerPhone, numPackages, invoiceDescription);
    }

    @Override
    public boolean cancelInvoice(int invoiceID) {
        return im.cancelInvoice(invoiceID);
    }

    @Override
    public List<Invoice> getCustomerInvoices(String customerName) {
        return im.getCustomerInvoices(customerName);
    }

    @Override
    public boolean addPackageToInvoice(int packageID, int invoiceID) {
        return im.addPackageToInvoice(packageID, invoiceID);
    }

    @Override
    public Invoice getInvoice(int invoiceID) {
        return im.getInvoice(invoiceID);
    }

    @Override
    public SystemPackage getPackage(int packageID) {
        return im.getPackage(packageID);
    }
}
