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

    public Package getPackage(String packageID);

    public boolean setStatus(INVOICE_STATE newState);

    public INVOICE_STATE getStatus();

    public boolean addPackage(Package p);

    public int getID();
}
