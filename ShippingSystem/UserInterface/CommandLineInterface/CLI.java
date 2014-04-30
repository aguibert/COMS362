/**
 * 
 */
package CommandLineInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;
import system.SystemPackageImpl;
import system.invoice.Invoice;
import system.invoice.InvoiceController;
import system.invoice.InvoiceControllerImpl;
import system.truck.Route;
import system.truck.Truck;
import system.truck.TruckController;
import system.truck.TruckControllerImpl;
import system.warehouse.Warehouse;
import system.warehouse.WarehouseController;
import system.warehouse.WarehouseControllerImpl;

public class CLI
{
    private static InvoiceController ic = new InvoiceControllerImpl();
    private static TruckController tc = new TruckControllerImpl();
    private static WarehouseController wc = new WarehouseControllerImpl();
    private static boolean exit = false;

    public static void main(String[] args)
    {
        System.out.println("Team #11 - Shipping System");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            String cmd = "";
            while (!exit && cmd != null) {
                System.out.print("> ");
                cmd = br.readLine();
                processCommand(cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean processCommand(String cmd)
    {
        String[] args = cmd.split(" ");
        if (cmd.trim().length() == 0 || args.length == 0) {
            // Empty line, do nothing
        }
        else if ("DB".equalsIgnoreCase(args[0])) {
            return doDB(args);
        }
        else if ("warehouse".equalsIgnoreCase(args[0])) {
            return doWarehouse(args);
        }
        else if ("invoice".equalsIgnoreCase(args[0])) {
            return doInvoice(args);
        }
        else if ("truck".equalsIgnoreCase(args[0])) {
            return doTruck(args);
        }
        else if ("help".equalsIgnoreCase(args[0])) {
            System.out.println("Basic operations:\n DB\n WAREHOUSE \n INVOICE\n TRUCK\n EXIT");
        }
        else if ("exit".equalsIgnoreCase(args[0]) ||
                 "quit".equalsIgnoreCase(args[0])) {
            exit = true;
            return true;
        }
        else {
            System.out.println("< Unrecognized command");
        }
        return true;
    }

    /**
     * @param args
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean doTruck(String[] args) {
        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Truck operations:\n"
                               + "CREATE                 \n"
                               + "CREATEROUTE            <truckID>\n"
                               + "REFRESHROUTE           <truckID>\n"
                               + "GETPACKAGES            <truckID>\n"
                               + "ADDPACKAGE             <packageID> <truckID>\n"
                               + "REMOVEPACKAGE          <packageID> <truckID>\n"
                               + "GETTRUCKS              <state>\n"
                               + "SETSTATE               <truckID> <newState>\n"
                               + "GET                    <truckID>\n");
            return true;
        }

        //create truck
        if ("create".equalsIgnoreCase(args[1])) {
            if (len != 2) {
                System.out.println("TRUCK CREATE <>");
                return false;
            }

            int id = tc.createTruck();
            System.out.println("Created truck with id " + id);
            return true;
        }

        //create route
        if ("createroute".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK CREATEROUTE <truckID>");
                return false;
            }

            Route r = tc.createRoute(Integer.valueOf(args[2]));
            System.out.println("Truck " + args[2] + " has route " + r);
        }

        //refresh truck route
        if ("refreshroute".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK REFRESHROUTE <truckID>");
                return false;
            }

            if (tc.refreshTruckRoute(Integer.valueOf(args[2]))) {
                System.out.println("Truck route refreshed for truck " + args[1]);
            }
            else {
                System.out.println("Truck " + args[2] + " not found in database");
            }
            return true;
        }

        //get packages on truck
        if ("getpackages".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GETPACKAGES <truckID>");
                return false;
            }

            List<SystemPackage> packs = tc.getPackagesOnTruck(Integer.valueOf(args[2]));
            System.out.println(packs);
            if (packs == null) {
                System.out.println("Truck " + args[2] + " does not exist.");
                return true;
            }
            System.out.println("Truck " + args[2] + " has " + packs.size() + " packages.");

            for (SystemPackage pack : packs) {
                System.out.println("  " + pack.getPackageID());
            }
            return true;
        }

        //add package to truck
        if ("addpackage".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK ADDPACKAGE <packageID> <truckID>");
                return false;
            }

            if (tc.addPackageToTruck(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Package " + args[2] + " added to truck " + args[3]);
            }
            return true;
        }

        //remove package from truck
        if ("removepackage".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK REMOVEPACKAGE <packageID> <truckID>");
                return false;
            }

            if (tc.addPackageToTruck(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Package " + args[2] + " removed from truck " + args[3]);
            }
            else {
                System.out.println("Package not found"); //should I clarify if package not found or truck not found?
            }
            return true;
        }

        //get trucks
        if ("gettrucks".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GETTRUCKS <state>");
                return false;
            }

            System.out.println("Trucks with state: " + args[2]);
            for (Truck tr : tc.getTrucks(args[2])) {
                System.out.println("  " + tr.getID());
            }
            return true;
        }

        //set truck state
        if ("setstate".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK SETSTATE <truckID> <newState>");
                return false;
            }

            if (tc.setTruckState(Integer.valueOf(args[2]), args[3])) {
                System.out.println("Truck " + args[2] + " set to state " + args[3]);
            }
            else {
                System.out.println("Truck " + args[2] + " not found in database");
            }
        }

        //get truck
        if ("get".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GET <truckID>");
                return false;
            }

            Truck tr = tc.getTruck(Integer.valueOf(args[2]));
            if (tr == null) {
                System.out.println("Truck " + args[2] + " not found in database");
            }
            else {
                System.out.println(tr);
            }
        }

        //invalid command
        else {
            System.out.println("Invalid Command");
        }
        return true;
    }

    private static boolean doDB(String[] args) {
        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Database operations:\n CREATE\n DROP");
        }

        DatabaseSupportImpl db = new DatabaseSupportImpl();
        if ("CREATE".equalsIgnoreCase(args[1])) {
            if (db.createTable())
                System.out.println("Tables created successfully.");

            if (len == 3 && "gen".equalsIgnoreCase(args[2])) {
                System.out.println("Created warehouse: " + wc.createWarehouse());
                System.out.println("Created truck: " + tc.createTruck());
                System.out.println("Created invoice: " + ic.createInvoice("comp", "cust", "addr", "phone", 2, "desc"));
                if (wc.packageArrival(1, 1, "cust", "dest", 1.0, 1.0) == null)
                    return false;
            }
        }
        else if ("DROP".equalsIgnoreCase(args[1])) {
            if (db.dropTable())
                System.out.println("Tables dropped successfully.");
        }
        // TODO remove this once warehouse manager creates pakcages
        else if ("createPkg".equalsIgnoreCase(args[1])) {
            SystemPackage sp = new SystemPackageImpl(0, 1, "cust", "dest", 1.0, 1.0, PACKAGE_STATE.WAREHOUSE);
            new DatabaseSupportImpl().putPackage(sp);
        }

        return true;
    }

    private static boolean doWarehouse(String[] args) {

        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Warehouse operations:\n "
                               + "CREATE        <warehouseID>\n "
                               + "GET           <warehouseID>\n "
                               + "ARRIVAL       <warehouseID> <invoiceID> <customerName> <destinationAddress> <weight> <shippingCost>\n "
                               + "DEPARTURE     <warehouseID> <packageID>");
            return true;
        }

        //createWarehouse
        if ("create".equalsIgnoreCase(args[1])) {
            if (len != 2) {
                System.out.println("WAREHOUSE CREATE");
                return false;
            }

            int id = wc.createWarehouse();
            System.out.println("Created warehouse with id " + id);
            return true;
        }

        //getWarehouse
        if ("get".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("WAREHOUSE GET <warehouseID>");
                return false;
            }

            Warehouse w = wc.getWarehouse(Integer.valueOf(args[2]));
            if (w == null)
                System.out.println("Warehouse " + args[2] + " not found in database.");
            else
                System.out.println(w.toString());
            return true;
        }

        //packageArrival
        if ("arrival".equalsIgnoreCase(args[1])) {
            if (len != 8) {
                System.out.println("WAREHOUSE ARRIVAL <warehouseID> <invoiceID> <customerName> <destinationAddress> <weight> <shippingCost>");
                return false;
            }

            SystemPackage p = wc.packageArrival(Integer.valueOf(args[2]), Integer.valueOf(args[3]), args[4], args[5], Double.valueOf(args[6]), Double.valueOf(args[7]));
            System.out.println("Created package with id " + p.getPackageID() + " and added to warehouse " + Integer.valueOf(args[2]));
            return true;
        }

        //packageDeparture
        if ("departure".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("WAREHOUSE DEPARTURE <warehouseID> <packageID>");
                return false;
            }

            if (wc.packageDeparture(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("pacakge " + Integer.valueOf(args[3]) + " removed from warehouse " + Integer.valueOf(args[2]));
            }
            else {
                System.out.println("Unable to depart package " + Integer.valueOf(args[3] + " from warehouse " + Integer.valueOf(args[2])));
            }
        }
        return true;
    }

    private static boolean doInvoice(String[] args) {

        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Invoice operations:\n "
                               + "CREATE        <companyName> <customerName> <customerAddress> <customerPhone> <numPackages> <description>\n "
                               + "CANCEL        <invoiceID>\n "
                               + "GETCUSTOMER   <customerName>\n "
                               + "ADDPACKAGE    <invoiceID> <packageID>\n "
                               + "GET           <invoiceID>\n "
                               + "GETPACKAGE    <packageID>\n "
                               + "QUERYSTATE    <OPEN|COMPLETE|IN_PROGRESS|CANCELLED>\n "
                               + "DELIVER       <packageID> <truckID>");
            return true;
        }

        // create invoice
        if ("create".equalsIgnoreCase(args[1])) {
            if (len != 8) {
                System.out.println("INVOICE CREATE <companyName> <customerName> <customerAddress> <customerPhone> <numPackages> <description>");
                return false;
            }

            int id = ic.createInvoice(args[2], args[3], args[4], args[5], Integer.valueOf(args[6]), args[7]);
            System.out.println("Created invoice with id " + id);
            return true;
        }

        // cancel invoice
        if ("cancel".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE CANCEL <invoiceID>");
                return false;
            }

            if (ic.cancelInvoice(Integer.valueOf(args[2])))
                System.out.println("Cancelled invoice " + args[2]);
            else
                System.out.println("Invoice " + args[2] + " not found in database.");
            return true;
        }

        // getCustomerInvoices
        if ("getCustomer".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE GETCUSTOMER <customerName>");
                return false;
            }

            System.out.println("Invoices for customer: " + args[2]);
            for (Invoice inv : ic.getCustomerInvoices(args[2])) {
                System.out.println("  " + inv.getID());
            }
            return true;
        }

        // addPackageToInvoice
        if ("addPackage".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("INVOICE ADDPACKAGE <invoiceID> <packageID>");
                return false;
            }

            if (ic.addPackageToInvoice(Integer.valueOf(args[3]), Integer.valueOf(args[2])))
                System.out.println("Package " + args[3] + " added to invoice " + args[2]);
            else
                System.out.println("Unable to add package " + args[3] + " to invoice " + args[2]);
            return true;
        }

        // getInvoice
        if ("get".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE GET <invoiceID>");
                return false;
            }

            Invoice i = ic.getInvoice(Integer.valueOf(args[2]));
            if (i == null)
                System.out.println("Invoice " + args[2] + " not found in database.");
            else
                System.out.println(i.toString());
            return true;
        }

        // getPackage
        if ("getPackage".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE GETPACKAGE <packageID>");
                return false;
            }

            SystemPackage sp = ic.getPackage(Integer.valueOf(args[2]));
            if (sp == null)
                System.out.println("Package " + args[2] + " not found in database.");
            else
                System.out.println(sp.toString());
            return true;
        }

        if ("queryState".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE QUERYSTATE <OPEN|COMPLETE|IN_PROGRESS|CANCELLED>");
                return false;
            }

            Set<Invoice> iSet = ic.getInvoiceByState(args[2]);
            if (iSet == null)
                System.out.println("Please provide one of the valid invoice states: <OPEN|COMPLETE|IN_PROGRESS|CANCELLED>");
            else if (iSet.size() == 0)
                System.out.println("No invoices matched state " + args[2]);
            else
                for (Invoice i : iSet)
                    System.out.println("  " + i.toString().replace("\n", "\n  "));
            return true;
        }

        if ("deliver".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("DELIVER       <packageID> <truckID>");
                return false;
            }

            if (ic.deliverPackage(Integer.valueOf(args[2]), Integer.valueOf(args[3])) == false) {
                return false;
            }
            else
                return true;
        }

        System.out.println("Error: Unrecognized command.");
        return false;
    }
}
