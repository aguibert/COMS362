/**
 * 
 */
package system;

import java.util.List;

import system.invoice.Invoice;
import system.truck.Truck;
import system.truck.Truck.TRUCK_STATE;
import system.warehouse.Warehouse;

/**
 * @author Andrew
 * 
 */
public interface DatabaseSupport {
    public boolean putTruck(Truck t);

    public Truck getTruck(int truckID);

    public List<Truck> getTrucks(TRUCK_STATE state);

    public boolean putInvoice(Invoice i);

    public Invoice getInvoice(int invoiceID);

    public List<Invoice> getInvoiceByName(String customerName);

    public boolean putWareHouse(Warehouse w);

    public Warehouse getWareHouse(int warehouseID);

    public boolean putPackage(SystemPackage p);

    public SystemPackage getPackage(int packageID);

    /**
     * Gets the next ID that should be assigned to the corresponding id type.
     * 
     * @param idType
     *            <br> 'i' for invoice
     *            <br> 't' for truck
     *            <br> 'w' for warehouse
     *            <br> 'p' for package
     * @return
     *         the id that the next object should be created with
     */
    public int getNextID(char idType);
}
