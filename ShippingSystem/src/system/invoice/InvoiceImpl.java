/**
 * 
 */
package system.invoice;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import system.DatabaseSupportImpl;
import system.SystemPackage;

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

        ivState = INVOICE_STATE.IN_PROGRESS;
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
        if (newState != ivState)
            ivState = newState;
        return true;
    }

    @Override
    public INVOICE_STATE getStatus() {
        return ivState;
    }

    @Override
    public boolean addPackage(int p) {
        packages.add(p);
        return true;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }
}
