/**
 * 
 */
package CommandLineInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import system.SystemPackage;
import system.invoice.Invoice;
import system.invoice.InvoiceController;
import system.invoice.InvoiceControllerImpl;
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
            System.out.println("Truck operations:\n "
                               + "ADDPACKAGE      <packageID> <truckID>\n "
                               + "CREATE          \n "
                               + "CREATEROUTE     <truckID>\n "
                               + "GET             <truckID>\n "
                               + "GETPACKAGES     <truckID>\n "
                               + "GETSTATE        <ALL_STATES|AVAILABLE|IN_ROUTE|BROKEN|LOADING>\n "
                               + "REMOVEPACKAGE   <packageID> <truckID>\n "
                               + "RELEASETRUCK    <truckID> <warehouseID>\n "
                               + "REFRESHROUTE    <truckID>\n "
                               + "SETSTATE        <truckID> <ALL_STATES|AVAILABLE|IN_ROUTE|BROKEN|LOADING>\n "
                               + "TOWAREHOUSE     <truckID> <warehouse>"
                            );
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

            String str = tc.createRoute(Integer.valueOf(args[2]));
            if (str == null) {
                return false;
            }
            System.out.println("Truck " + args[2] + " has route " + str);
            return true;
        }

        //refresh truck route
        if ("refreshroute".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK REFRESHROUTE <truckID>");
                return false;
            }

            String nextStop = tc.refreshTruckRoute(Integer.valueOf(args[2]));
            if (nextStop != null) {
                System.out.println("Truck route refreshed for truck " + args[2] + ".  Next stop is " + nextStop);
                return true;
            }
            else {
                return false;
            }
        }

        //get packages on truck
        if ("getpackages".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GETPACKAGES <truckID>");
                return false;
            }

            List<SystemPackage> packs = tc.getPackagesOnTruck(Integer.valueOf(args[2]));
            if (packs == null) {
                return false;
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

            if (tc.removePackageFromTruck(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Package " + args[2] + " removed from truck " + args[3]);
                return true;
            }
            else {
                return false;
            }
        }

        //get trucks
        if ("getState".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GETSTATE <ALL_STATES|AVAILABLE|IN_ROUTE|BROKEN|LOADING>");
                return false;
            }

            List<Truck> list = tc.getTrucks(args[2]);
            if (list == null) {
                return false;
            }
            System.out.println("Trucks with state: " + args[2]);
            for (Truck tr : list) {
                System.out.println("  id=" + tr.getID());
            }
            return true;
        }

        //set truck state
        if ("setstate".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK SETSTATE <truckID> <ALL_STATES|AVAILABLE|IN_ROUTE|BROKEN|LOADING>");
                return false;
            }

            if (tc.setTruckState(Integer.valueOf(args[2]), args[3])) {
                System.out.println("Truck " + args[2] + " set to state " + args[3]);
                return true;
            }
            else {
                return false;
            }
        }

        //return truck to warehouse
        if ("toWarehouse".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK TOWAREHOUSE <truckID> <warehouseID>");
                return false;
            }

            if (tc.returnTruckToWarehouse(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Truck " + args[2] + " returned to Warehouse " + args[3]);
                return true;
            }
            else {
                return false;
            }
        }

        //release truck from warehouse
        if ("releaseTruck".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK TOWAREHOUSE <truckID> <warehouseID>");
                return false;
            }

            if (tc.releaseTruck(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Truck " + args[2] + " released from Warehouse " + args[3]);
                return true;
            }
            else {
                return false;
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
                return false;
            }
            System.out.println(tr);
            return true;
        }

        System.out.println("Invalid Command");
        return false;
    }

    private static boolean doDB(String[] args) {
        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Database operations:\n CREATE\n DROP");
            return false;
        }

        system.DatabaseSupportImpl db = new system.DatabaseSupportImpl();
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
            return true;
        }
        else if ("DROP".equalsIgnoreCase(args[1])) {
            if (db.dropTable())
                System.out.println("Tables dropped successfully.");
            return true;
        }

        System.out.println("Invalid Command");
        return false;
    }

    private static boolean doWarehouse(String[] args) {

        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Warehouse operations:\n "
                               + "ARRIVAL       <warehouseID> <invoiceID> <customerName> <destinationAddress> <weight> <shippingCost>\n "
                               + "CREATE        <warehouseID>\n "
                               + "DEPARTURE     <warehouseID> <packageID> <truckID>\n "
                               + "GET           <warehouseID>\n "
                               + "GETALL\n "
                               + "SETLOC        <warehouseID> <location>");
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
            if (len != 5) {
                System.out.println("WAREHOUSE DEPARTURE <warehouseID> <packageID> <truckID>");
                return false;
            }

            if (wc.packageDeparture(Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]))) {
                System.out.println("pacakge " + Integer.valueOf(args[3]) + " removed from warehouse " + Integer.valueOf(args[2]));
            }
            else {
                System.out.println("Unable to depart package " + Integer.valueOf(args[3] + " from warehouse " + Integer.valueOf(args[2])));
            }
            return true;
        }

        // Get all warehouses
        if ("getAll".equalsIgnoreCase(args[1])) {
            if (len != 2) {
                System.out.println("WAREHOUSE GETALL");
                return false;
            }

            Set<Warehouse> whs = wc.getAll();
            for (Warehouse wh : whs)
                System.out.println(wh);
            System.out.println("System returned " + whs.size() + " warehouse(s).");
            return true;
        }

        // Set location
        if ("setLoc".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("SETLOC <warehouseID> <location>");
                return false;
            }
            if (!wc.setLocation(Integer.valueOf(args[2]), args[3]))
                return false;
            System.out.println("Warehouse " + args[2] + " location has been udpated.");
            return true;
        }

        System.out.println("Unrecognized command.");
        return false;
    }

    private static boolean doInvoice(String[] args) {

        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Invoice operations:\n "
                               + "ADDPACKAGE    <invoiceID> <packageID>\n "
                               + "CREATE        <companyName> <customerName> <customerAddress> <customerPhone> <numPackages> <description>\n "
                               + "CANCEL        <invoiceID>\n "
                               + "DELIVER       <packageID> <truckID>\n "
                               + "GET           <invoiceID>\n "
                               + "GETPKG        <packageID>\n "
                               + "GETALLPKG     \n "
                               + "GETPKGLOC     <packageID> <invoiceID>\n "
                               + "GETCUSTOMER   <customerName>\n "
                               + "GETSTATE      <OPEN|COMPLETE|IN_PROGRESS|CANCELLED>\n "
                               + "MARKDAMAGED   <packageID> <invoiceID>"
                            );
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
        if ("getPkg".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE GETPKG <packageID>");
                return false;
            }

            SystemPackage sp = ic.getPackage(Integer.valueOf(args[2]));
            if (sp == null)
                System.out.println("Package " + args[2] + " not found in database.");
            else
                System.out.println(sp.toString());
            return true;
        }

        // Get package by state
        if ("getState".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("INVOICE GETSTATE    <OPEN|COMPLETE|IN_PROGRESS|CANCELLED>");
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

        // Deliver package (and mark closed if necessary)
        if ("deliver".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("DELIVER       <packageID> <truckID>");
                return false;
            }

            return ic.deliverPackage(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
        }

        // Mark a package damaged (invoice re-opened if previously completed)
        if ("markDamaged".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("MARKDAMAGED   <packageID> <invoiceID>");
                return false;
            }

            if (ic.markDamaged(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Package has been marked damaged.");
                return true;
            }
            return false;
        }

        // Query package by location
        if ("getPkgLoc".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("GETPKGLOC     <packageID> <invoiceID>");
                return false;
            }
            String loc = ic.getPkgLoc(Integer.valueOf(args[2]), Integer.valueOf(args[3]));
            if (loc != null) {
                System.out.println("Package has location: " + loc);
                return true;
            }
            return false;
        }

        // Get all packages
        if ("getAllPkg".equalsIgnoreCase(args[1])) {
            if (len != 2) {
                System.out.println("GETALLPKG");
                return false;
            }
            Set<SystemPackage> packs = ic.getAllPackages();
            for (SystemPackage sp : packs)
                System.out.println(sp.toString());
            System.out.println("System returned " + packs.size() + " package(s).");
            return true;
        }

        System.out.println("Error: Unrecognized command.");
        return false;
    }
}
