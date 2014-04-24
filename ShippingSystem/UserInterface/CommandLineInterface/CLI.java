/**
 * 
 */
package CommandLineInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.invoice.Invoice;
import system.invoice.InvoiceController;
import system.invoice.InvoiceControllerImpl;
import system.truck.Route;
import system.truck.Truck;
import system.truck.Truck.TRUCK_STATE;
import system.truck.TruckController;
import system.truck.TruckControllerImpl;

public class CLI
{
    private static InvoiceController ic = new InvoiceControllerImpl();
    private static TruckController tc = new TruckControllerImpl();
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

    private static boolean processCommand(String cmd) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        String[] args = cmd.split(" ");
        if (cmd.trim().length() == 0 || args.length == 0) {
            // Empty line, do nothing
        }
        else if ("DB".equalsIgnoreCase(args[0])) {
            return doDB(args);
        }
        else if ("warehouse".equalsIgnoreCase(args[0])) {

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
    private static boolean doTruck(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        int len = args.length;
        if (len < 2 || "help".equalsIgnoreCase(args[1])) {
            System.out.println("Truck operations:\n"
                               + "CREATE                 \n"
                               + "CREATEROUTE            <truckID>\n"
                               + "REFRESHTRUCKROUTE      <truckID>\n"
                               + "GETPACKAGESON          <truckID>\n"
                               + "ADDPACKAGETO           <packageID> <truckID>\n"
                               + "REMOVEPACKAGEFROM      <packageID> <truckID>\n"
                               + "GETTRUCKS              <state>\n"
                               + "SETTRUCKSTATE          <truckID> <newState>\n"
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
        if ("refreshtruckroute".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK REFRESHTRUCKROUTE <truckID>");
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
        if ("getpackageson".equalsIgnoreCase(args[1])) {
            if (len != 3) {
                System.out.println("TRUCK GETPACKAGESON <truckID>");
                return false;
            }

            System.out.println("Packages for Truck " + args[2]);
            if (tc.getPackagesOnTruck(Integer.valueOf(args[2])) == null) {
                System.out.println("No packages on truck");
                return true;
            }
            for (SystemPackage pack : tc.getPackagesOnTruck(Integer.valueOf(args[2]))) {
                System.out.println("  " + pack.getPackageID());
            }
            return true;
        }

        //add package to truck
        if ("addpackageto".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK ADDPACKAGEOTO <packageID> <truckID>");
                return false;
            }

            if (tc.addPackageToTruck(Integer.valueOf(args[2]), Integer.valueOf(args[3]))) {
                System.out.println("Package " + args[2] + " added to truck " + args[3]);
            }
            else {
                System.out.println("Package not found"); //should I clarify if package not found or truck not found?
            }
            return true;
        }

        //remove package from truck
        if ("removepackagefrom".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK REMOVEPACKAGEFROM <packageID> <truckID>");
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

            TRUCK_STATE state = (TRUCK_STATE) Class.forName(args[2]).newInstance();
            System.out.println("Trucks with state: " + args[2]);
            for (Truck tr : tc.getTrucks(state)) {
                System.out.println("  " + tr.getID());
            }
            return true;
        }

        //set truck state
        if ("settruckstate".equalsIgnoreCase(args[1])) {
            if (len != 4) {
                System.out.println("TRUCK SETTRUCKSTATE <truckID> <newState>");
                return false;
            }

            TRUCK_STATE newState = (TRUCK_STATE) Class.forName(args[2]).newInstance();
            if (tc.setTruckState(Integer.valueOf(args[2]), newState)) {
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
        }
        else if ("DROP".equalsIgnoreCase(args[1])) {
            if (db.dropTable())
                System.out.println("Tables dropped successfully.");
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
                               + "DELIVER       <packageID>");
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

        // addPakcageToInvoice
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
                    System.out.println("  " + i);
            return true;
        }

        if ("deliver".equalsIgnoreCase(args[1])) {
            // TODO need Jon to push his UI code for package arrival in order to test this
            if (len != 3) {
                System.out.println("DELIVER       <packageID>");
                return false;
            }

            if (ic.deliverPackage(Integer.valueOf(args[2])) == false) {
                System.out.println("Package " + Integer.valueOf(args[2]) + " was not found in database.");
                return false;
            }
            else
                return true;
        }

        return true;
    }
}
