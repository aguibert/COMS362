/**
 * 
 */
package system;

import system.invoice.Invoice;
import system.truck.Truck;
import system.warehouse.Warehouse;

/**
 * @author Andrew
 * 
 */
public interface DatabaseSupport {
    public boolean putTruck(Truck t);

    public boolean getTruck(int truckID);

    public boolean putInvoice(Invoice i);

    public boolean getInvoice(int invoiceID);

    public boolean putWareHouse(Warehouse w);

    public boolean getWareHouse(int warehouseID);
}
