package system.invoice;

import java.util.List;

public interface InvoiceController {

    public boolean createInvoice(String companyName, String customerName, String customerAddress, String customerPhone,
                                 int numPackages, String invoiceDescription);

    public boolean cancelInvoice(int invoiceID);

    public List<Invoice> getCustomerInvoices(String customerName);

    public boolean addPackageToInvoice(String packageID);

    public Invoice getInvoice(int invoiceID);

    public Package getPackage(String packageID);
}
