/**
 * 
 */
package system.invoice;

import java.util.List;
import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;
import system.invoice.Invoice.INVOICE_STATE;
import system.truck.TruckManagerImpl;

/**
 * @author Andrew
 */
public class InvoiceManagerImpl implements InvoiceManager
{
    private static volatile InvoiceManagerImpl singleton = null;

    public static synchronized InvoiceManagerImpl getInstance() {
        if (singleton == null)
            singleton = new InvoiceManagerImpl();
        return singleton;
    }

    private InvoiceManagerImpl() {}

    @Override
    public int createInvoice(String companyName, String customerName, String customerAddress, String customerPhone, int numPackages, String invoiceDescription) {
        DatabaseSupport db = new DatabaseSupportImpl();

        Invoice i = new InvoiceImpl(db.getNextID('i'), companyName, customerName, customerAddress, customerPhone, numPackages, invoiceDescription);

        db.putInvoice(i);

        return i.getID();
    }

    @Override
    public boolean cancelInvoice(int invoiceID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Invoice i = dbs.getInvoice(invoiceID);

        if (i == null)
            return false;

        if (i.setStatus(INVOICE_STATE.CANCELLED) == false)
            return false;

        return dbs.putInvoice(i);
    }

    @Override
    public List<Invoice> getCustomerInvoices(String customerName) {
        return new DatabaseSupportImpl().getInvoiceByName(customerName);
    }

    @Override
    public boolean addPackageToInvoice(int packageID, int invoiceID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Invoice i = dbs.getInvoice(invoiceID);

        if (i == null)
            return false;

        if (dbs.getPackage(packageID) == null) {
            return false;
        }

        if (i.addPackage(packageID) == false)
            return false;

        return dbs.putInvoice(i);
    }

    @Override
    public Invoice getInvoice(int invoiceID) {
        return new DatabaseSupportImpl().getInvoice(invoiceID);
    }

    @Override
    public SystemPackage getPackage(int packageID) {
        return new DatabaseSupportImpl().getPackage(packageID);
    }

    @Override
    public Set<Invoice> getInvoiceByState(INVOICE_STATE state) {
        return new DatabaseSupportImpl().getInvoiceByState(state);
    }

    @Override
    public boolean deliverPackage(int packageID, int truckID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;

        Invoice i = dbs.getInvoice(sp.getInvoice());
        if (i == null)
            return false;

        if (i.deliverPackage(packageID) == false)
            return false;

        if (TruckManagerImpl.getInstance().removePackageFromTruck(packageID, truckID) == false)
            return false;

        if (dbs.putInvoice(i) == false)
            return false;

        notifyCustomer(i, "Package " + packageID + " has been sucessfully delivered to your location!");
        return true;
    }

    private void notifyCustomer(Invoice i, String msg) {
        System.out.println(" <<< System is notifying customer " + i.getCustomerName() + " with message:\n" + msg);
    }

    @Override
    public boolean markDamaged(int packageID, int invoiceID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        Invoice i = dbs.getInvoice(invoiceID);
        if (i == null)
            return false;
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;

        if (sp.setState(PACKAGE_STATE.DAMAGED) == false)
            return false;

        if (i.getStatus() == INVOICE_STATE.COMPLETE) {
            if (i.setStatus(INVOICE_STATE.IN_PROGRESS) == false)
                return false;
            System.out.println("Packaged marked as damaged.  Re-opening closed invoice.");
        }

        if (dbs.putInvoice(i) == false)
            return false;
        if (dbs.putPackage(sp) == false)
            return false;

        return true;
    }

    @Override
    public String getPkgLoc(int packageID, int invoiceID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();

        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return null;

        if (sp.getInvoice() != invoiceID) {
            System.out.println("ERROR: Package " + packageID + " does not belong to invoice " + invoiceID + ".");
            return null;
        }

        return sp.getLocation();
    }

    @Override
    public Set<SystemPackage> getAllPackages() {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        return dbs.getAllPackages();
    }
}
