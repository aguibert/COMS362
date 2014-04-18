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

    public Truck getTruck(int truckID);

    public boolean putInvoice(Invoice i);

    public Invoice getInvoice(int invoiceID);

    public boolean putWareHouse(Warehouse w);

    public Warehouse getWareHouse(int warehouseID);

    public boolean putPackage(SystemPackage p);

    public SystemPackage getPackage(int packageID);
}
