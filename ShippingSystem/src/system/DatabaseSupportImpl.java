/**
 * 
 */
package system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource40;

import system.invoice.Invoice;
import system.invoice.Invoice.INVOICE_STATE;
import system.truck.Truck;
import system.truck.Truck.TRUCK_STATE;
import system.warehouse.Warehouse;

/**
 * @author Andrew
 */
public class DatabaseSupportImpl implements DatabaseSupport
{
    private static boolean isDriverLoaded = false;
    private static EmbeddedConnectionPoolDataSource ds = new EmbeddedConnectionPoolDataSource40();
    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource40";
    private static final String DB_NAME = "myDB";
    private static final String DB_URL = "jdbc:derby:" + DB_NAME;
    private static final String TRUCK_TABLE = "truck_TABLE";
    private static final String INVOICE_TABLE = "invoice_TABLE";
    private static final String WAREHOUSE_TABLE = "warehouse_TABLE";
    private static final String PACKAGE_TABLE = "package_TABLE";
    private static final String CREATE_TRUCK_TABLE = "CREATE TABLE " + TRUCK_TABLE +
                                                     "( id int NOT NULL UNIQUE, javaObject blob )";
    private static final String CREATE_INVOICE_TABLE = "CREATE TABLE " + INVOICE_TABLE +
                                                       "( id int NOT NULL UNIQUE, javaObject blob )";
    private static final String CREATE_WAREHOUSE_TABLE = "CREATE TABLE " + WAREHOUSE_TABLE +
                                                         "( id int NOT NULL UNIQUE, javaObject blob )";
    private static final String CREATE_PACKAGE_TABLE = "CREATE TABLE " + PACKAGE_TABLE +
                                                       "( id int NOT NULL UNIQUE, invoice int, truck int, warehouse int, javaObject blob )";

    public DatabaseSupportImpl() {}

    private boolean putCommon(Object o, String table, int id) {
        try (Connection conn = getConnection()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(o);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            PreparedStatement ps = conn.prepareStatement("insert into " + table + " (ID, javaObject) values(?, ?)");
            ps.setInt(1, id);
            ps.setObject(2, data);
            try {
                ps.executeUpdate();
            } catch (SQLException sqle) {
                PreparedStatement ps1 = conn.prepareStatement("UPDATE " + table + " SET javaObject=? WHERE id=" + id);
                ps1.setObject(1, data);
                if (ps1.executeUpdate() != 1)
                    System.out.println("Update of id " + id + " failed in the table " + table);
                ps1.close();
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Object getCommon(int id, String table) {
        Object o = null;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select javaObject from " + table + " where id=" + id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                o = ois.readObject();

                ois.close();
                bis.close();
            }
            else
                return null;
            if (rs.next()) {
                // should not get here, this means there were duplicate primary keys
                System.out.println("Found duplicate keys for " + id + " in table " + table);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    @Override
    public boolean putTruck(Truck t) {

        // Store package values in the PACKAGE_TABLE table for later reconstruction
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            for (Integer sp : t.getPackages()) {
                String update = "UPDATE " + PACKAGE_TABLE + " SET truck=" + t.getID() + ", warehouse=-1 WHERE id=" + sp;
                if (s.executeUpdate(update) != 1) {
                    System.out.println("Error: Package " + sp + " not found in database");
                    return false;
                }
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return putCommon(t, TRUCK_TABLE, t.getID());
    }

    @Override
    public Truck getTruck(int truckID) {

        Truck t = (Truck) getCommon(truckID, TRUCK_TABLE);
        if (t == null) {
            System.out.println("ERROR: Truck " + truckID + " was not found in database.");
            return null;
        }

        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM " + PACKAGE_TABLE + " WHERE truck=" + truckID);
            while (rs.next()) {
                t.addPackage(rs.getInt("id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    @Override
    public boolean putInvoice(Invoice i) {

        // Store package values in the PACKAGE_TABLE table for later reconstruction
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            for (Integer sp : i.getPackages()) {
                String update = "UPDATE " + PACKAGE_TABLE + " SET invoice=" + i.getID() + " WHERE id=" + sp;
                if (s.executeUpdate(update) != 1) {
                    System.out.println("Error: Package " + sp + " not found in database");
                    return false;
                }
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return putCommon(i, INVOICE_TABLE, i.getID());
    }

    @Override
    public Invoice getInvoice(int invoiceID) {

        Invoice i = (Invoice) getCommon(invoiceID, INVOICE_TABLE);

        if (i == null) {
            System.out.println("ERROR: Invoice " + invoiceID + " was not found in database");
            return null;
        }

        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM " + PACKAGE_TABLE + " WHERE invoice=" + invoiceID);
            while (rs.next()) {
                i.addPackage(rs.getInt("id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return i;
    }

    @Override
    public boolean putWareHouse(Warehouse w) {

        // Store package values in the PACKAGE_TABLE table for later reconstruction
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            for (Integer sp : w.getPackages()) {
                String update = "UPDATE " + PACKAGE_TABLE + " SET warehouse=" + w.getID() + ", truck=-1 WHERE id=" + sp;
                if (s.executeUpdate(update) != 1) {
                    System.out.println("Error: Unable to put package " + sp + " into database.");
                    return false;
                }
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return putCommon(w, WAREHOUSE_TABLE, w.getID());
    }

    @Override
    public Warehouse getWareHouse(int warehouseID) {

        Warehouse w = (Warehouse) getCommon(warehouseID, WAREHOUSE_TABLE);

        if (w == null) {
            System.out.println("ERROR: Warehouse " + warehouseID + " was not found in database.");
            return null;
        }

        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id FROM " + PACKAGE_TABLE + " WHERE warehouse=" + warehouseID);
            while (rs.next()) {
                w.addPackage(rs.getInt("id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return w;
    }

    @Override
    public boolean putPackage(SystemPackage p) {

        if (putCommon(p, PACKAGE_TABLE, p.getPackageID()) == false) {
            return false;
        }

        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            String update = "UPDATE " + PACKAGE_TABLE + " SET truck=-1, warehouse=-1, invoice=" + p.getInvoice() + " WHERE id=" + p.getPackageID();
            if (s.executeUpdate(update) != 1)
                System.out.println("Error: Unable to put package " + p.getPackageID() + " into database.");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public SystemPackage getPackage(int packageID) {

        SystemPackage sp = (SystemPackage) getCommon(packageID, PACKAGE_TABLE);
        if (sp == null) {
            System.out.println("ERROR: Package " + packageID + " was not found in database.");
        }
        return sp;
    }

    private Connection getConnection() {
        try {
            if (!isDriverLoaded) {
                Class.forName(DB_DRIVER).newInstance();
                ds.setDatabaseName(DB_NAME);
                isDriverLoaded = true;
            }
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } // Load driver class into runtime
    }

    public boolean createTable() {

        boolean caughtEx = false;

        try {
            Class.forName(DB_DRIVER).newInstance();
            DriverManager.getConnection(DB_URL + ";create=true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } // Load driver class into runtime

        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_TRUCK_TABLE);
        } catch (Exception e) {
            caughtEx = true;
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_INVOICE_TABLE);
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_WAREHOUSE_TABLE);
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_PACKAGE_TABLE);
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }

        // Create and initialize config file
        try (OutputStream os = new FileOutputStream("config.properties")) {
            Properties props = new Properties();
            if (props.getProperty("nextInvoice") == null) {
                props.setProperty("nextInvoice", "1");
                props.setProperty("nextTruck", "1");
                props.setProperty("nextWarehouse", "1");
                props.setProperty("nextPackage", "1");
            }
            props.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return !caughtEx;
    }

    public boolean dropTable() {

        boolean caughtEx = false;

        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + TRUCK_TABLE); // Only run this once
        } catch (Exception e) {
            caughtEx = true;
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + INVOICE_TABLE); // Only run this once
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + WAREHOUSE_TABLE); // Only run this once
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + PACKAGE_TABLE); // Only run this once
        } catch (Exception e) {
            caughtEx = true;
            if (caughtEx)
                System.out.println("\n----------------------------\n");
            e.printStackTrace();
        }

        // Also reset the config file
        File config = new File("config.properties");
        config.delete();

        return !caughtEx;
    }

    @Override
    public List<Invoice> getInvoiceByName(String customerName) {

        List<Invoice> toReturn = new ArrayList<>();

        if (customerName == null || customerName.trim().length() == 0)
            return toReturn;

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + INVOICE_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                Invoice i = (Invoice) ois.readObject();
                if (i == null)
                    continue;
                if (i.getCustomerName() != null && i.getCustomerName().contains(customerName)) {
                    toReturn.add(this.getInvoice(i.getID()));
                }

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }

    @Override
    public List<Truck> getTrucks(TRUCK_STATE state)
    {
        List<Truck> toReturn = new ArrayList<>();

        if (state == null)
            return toReturn;

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + TRUCK_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                Truck t = (Truck) ois.readObject();
                if (t == null)
                    continue;
                if (state == TRUCK_STATE.ALL_STATES || state == t.getState())
                    toReturn.add(this.getTruck(t.getID()));

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }

    @Override
    public int getNextID(char idType) {

        Properties props = new Properties();

        try {
            // Load file contents into props
            try (InputStream is = new FileInputStream("config.properties")) {
                props.load(is);
            }

            // Read desired id value, and increment nextID counter
            int curValue = -1;
            switch (idType) {
                case 'i':
                    curValue = Integer.valueOf(props.getProperty("nextInvoice"));
                    props.setProperty("nextInvoice", String.valueOf(curValue + 1));
                    break;
                case 't':
                    curValue = Integer.valueOf(props.getProperty("nextTruck"));
                    props.setProperty("nextTruck", String.valueOf(curValue + 1));
                    break;
                case 'w':
                    curValue = Integer.valueOf(props.getProperty("nextWarehouse"));
                    props.setProperty("nextWarehouse", String.valueOf(curValue + 1));
                    break;
                case 'p':
                    curValue = Integer.valueOf(props.getProperty("nextPackage"));
                    props.setProperty("nextPackage", String.valueOf(curValue + 1));
                    break;
                default:
                    throw new RuntimeException("Invalid id type: " + idType);
            }

            // Store properties to config file
            try (OutputStream os = new FileOutputStream("config.properties")) {
                props.store(os, null);
            }

            if (curValue > 0)
                return curValue;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Should never get here
        return -1;
    }

    @Override
    public Set<Invoice> getInvoiceByState(INVOICE_STATE state) {
        Set<Invoice> toReturn = new HashSet<>();

        if (state == null)
            return toReturn;

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + INVOICE_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                Invoice i = (Invoice) ois.readObject();
                if (state == i.getStatus())
                    toReturn.add(this.getInvoice(i.getID()));

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }

    @Override
    public Set<SystemPackage> getAllPackages()
    {
        Set<SystemPackage> packs = new HashSet<>();

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select javaObject from " + PACKAGE_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                SystemPackage sp = (SystemPackage) ois.readObject();
                packs.add(sp);

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return packs;
    }

    @Override
    public Set<Warehouse> getAllWarehouse() {
        Set<Warehouse> toReturn = new HashSet<>();

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select javaObject from " + WAREHOUSE_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                Warehouse w = (Warehouse) ois.readObject();
                if (w == null)
                    continue;
                toReturn.add(this.getWareHouse(w.getID()));

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return toReturn;
    }
}
