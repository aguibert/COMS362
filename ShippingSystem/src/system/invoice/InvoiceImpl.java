/**
 * 
 */
package system.invoice;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;

/**
 * @author Andrew
 */
public class InvoiceImpl implements Invoice, Serializable
{
    private static final long serialVersionUID = 2322652424613221016L;
    private final int ID;
    private INVOICE_STATE ivState;
    private final String companyName;
    private final String customerName;
    private final String customerAddress;
    private final String customerPhone;
    private int numPackages;
    private int deliveredPackages;
    private String description;

    private Set<Integer> packages = new HashSet<>();

    protected InvoiceImpl(int _id, String companyName, String customerName, String customerAddress, String customerPhone, int numPackages, String invoiceDescription) {
        this.ID = _id;
        this.companyName = companyName;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.numPackages = numPackages;
        this.description = invoiceDescription;

        ivState = INVOICE_STATE.OPEN;
        deliveredPackages = 0;
    }

    @Override
    public SystemPackage getPackage(int packageID) {
        if (!packages.contains(packageID))
            return null;
        else {
            return new DatabaseSupportImpl().getPackage(packageID);
        }
    }

    @Override
    public boolean setStatus(INVOICE_STATE newState) {
        ivState = newState;
        return true;
    }

    @Override
    public INVOICE_STATE getStatus() {
        return ivState;
    }

    @Override
    public boolean addPackage(int p) {
        return packages.add(p);
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public boolean deliverPackage(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null) {
            System.out.println("ERROR: Package " + packageID + " was not found in DB.");
            return false;
        }
        // Make sure package belongs to this invoice
        if (sp.getInvoice() != this.ID || !packages.contains(packageID)) {
            System.out.println("ERROR: Package is owned by different invoice=" + sp.getInvoice());
            return false;
        }
        // Set package state to delivered
        if (sp.setState(PACKAGE_STATE.DELIVERED) == false)
            return false;

        // Increment delivered counter of this invoice
        if (deliveredPackages + 1 > numPackages) {
            System.out.println("ERROR: Delivered packages has exceeded number of packages for this invoice.");
            return false;
        }
        deliveredPackages++;

        if (dbs.putPackage(sp) == false)
            return false;

        // If all packages delivered, mark invoice COMPLETE
        if (deliveredPackages == numPackages)
            return setStatus(INVOICE_STATE.COMPLETE);

        return true;
    }

    @Override
    public Set<Integer> getPackages() {
        return this.packages;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Invoice: ");
        sb.append(ID);
        sb.append("\n  State=");
        sb.append(ivState.toString());
        sb.append("\n  Customer=");
        sb.append(getCustomerName());
        sb.append("  Address=");
        sb.append(this.customerAddress);
        sb.append("  Phone=");
        sb.append(this.customerPhone);
        sb.append("\n  Total/Delivered Packages (");
        sb.append(numPackages);
        sb.append('/');
        sb.append(deliveredPackages);
        sb.append(")\n  Packages: ");
        sb.append(packages);
        sb.append("\n  Description: ");
        sb.append(this.description);
        return sb.toString();
    }
}
