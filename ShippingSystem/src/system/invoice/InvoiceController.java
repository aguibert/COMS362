package system.invoice;

import java.util.List;

import system.Route;

public interface InvoiceController {
    boolean createInvoice(String companyName, String customerName, String customerAddress, String customerPhone,
                          int numPackages, String invoiceDescription);

    boolean updateRoute(Route r, Invoice inv);

    boolean createPackageRoute(String start, String[] destinations, Package pkg);

    boolean cancelInvoice(int invoiceID);

    List<Invoice> getCustomerInvoices(String customerName);

    boolean addPackageToInvoice(int invoiceID, int packageID);

    Invoice getInvoice(int invoiceID);
}
