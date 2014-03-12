/**
 * 
 */
package system.invoice;

/**
 * @author Andrew
 * 
 */
public interface Invoice {

    public enum INVOICE_STATE {
        OPEN,
        IN_PROGRESS,
        COMPLETE,
        CANCELLED
    }

    public Package getPackage(int packageID);

    public boolean changeStatus(INVOICE_STATE newState);

    boolean addPackageToInvoice(int packageID);
}
