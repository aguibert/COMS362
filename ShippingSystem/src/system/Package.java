/**
 * 
 */
package system;

/**
 * @author Andrew
 * 
 */
public interface Package {

    public enum PACKAGE_STATE {
        WAREHOUSE,
        ON_TRUCK,
        DELIVERED,
        DAMAGED,
        CANCELLED
    }

}
