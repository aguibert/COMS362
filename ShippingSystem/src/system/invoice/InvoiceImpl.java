/**
 * 
 */
package system.invoice;

import java.io.Serializable;

/**
 * @author Andrew
 * 
 */
public class InvoiceImpl implements Invoice, Serializable
{
    private static final long serialVersionUID = 2322652424613221016L;
    private final int ID;

    protected InvoiceImpl(int _id, String companyName, String customerName, String customerAddress, String customerPhone, int numPackages, String invoiceDescription) {
        this.ID = _id;
    }

    @Override
    public Package getPackage(String packageID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean setStatus(INVOICE_STATE newState) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public INVOICE_STATE getStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addPackage(Package p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
