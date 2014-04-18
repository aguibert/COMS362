package system.invoice;

import java.util.List;

import system.SystemPackage;

public interface InvoiceManager {

    public int createInvoice(String companyName, String customerName, String customerAddress, String customerPhone,
                             int numPackages, String invoiceDescription);

    public boolean cancelInvoice(int invoiceID);

    public List<Invoice> getCustomerInvoices(String customerName);

    public boolean addPackageToInvoice(int packageID, int invoiceID);

    public Invoice getInvoice(int invoiceID);

    public SystemPackage getPackage(int packageID);
}
