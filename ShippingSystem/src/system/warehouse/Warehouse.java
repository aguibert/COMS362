/**
 * 
 */
package system.warehouse;

/**
 * @author Andrew
 * 
 */
public interface Warehouse {

    public Package packageArrival(String customerName, String destinationAddress, double weight, double shippingCost);
}
