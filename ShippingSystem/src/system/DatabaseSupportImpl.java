/**
 * 
 */
package system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import system.invoice.Invoice;
import system.truck.Truck;
import system.warehouse.Warehouse;

/**
 * @author Andrew
 * 
 */
public class DatabaseSupportImpl implements DatabaseSupport
{
    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:myDB";
    private static final String TRUCK_TABLE = "truck_TABLE";
    private static final String INVOICE_TABLE = "invoice_TABLE";
    private static final String WAREHOUSE_TABLE = "warehouse_TABLE";
    private static final String PACKAGE_TABLE = "package_TABLE";
    private static final String CREATE_TRUCK_TABLE = "CREATE TABLE " + TRUCK_TABLE +
                                                     "( id int NOT NULL, javaObject blob )";
    private static final String CREATE_INVOICE_TABLE = "CREATE TABLE " + INVOICE_TABLE +
                                                       "( id int NOT NULL, javaObject blob )";
    private static final String CREATE_WAREHOUSE_TABLE = "CREATE TABLE " + WAREHOUSE_TABLE +
                                                         "( id int NOT NULL, javaObject blob )";
    private static final String CREATE_PACKAGE_TABLE = "CREATE TABLE " + PACKAGE_TABLE +
                                                       "( id int NOT NULL,javaObject blob )";

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
            ps.executeUpdate();
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
            PreparedStatement ps = conn.prepareStatement("select * from " + table + " where id=" + id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                o = ois.readObject();

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    @Override
    public boolean putTruck(Truck t) {

        return putCommon(t, TRUCK_TABLE, t.getID());
    }

    @Override
    public Truck getTruck(int truckID) {

        return (Truck) getCommon(truckID, TRUCK_TABLE);
    }

    @Override
    public boolean putInvoice(Invoice i) {

        return putCommon(i, INVOICE_TABLE, i.getID());
    }

    @Override
    public Invoice getInvoice(int invoiceID) {

        return (Invoice) getCommon(invoiceID, INVOICE_TABLE);
    }

    @Override
    public boolean putWareHouse(Warehouse w) {

        return putCommon(w, WAREHOUSE_TABLE, w.getID());
    }

    @Override
    public Warehouse getWareHouse(int warehouseID) {

        return (Warehouse) getCommon(warehouseID, WAREHOUSE_TABLE);
    }

    @Override
    public boolean putPackage(SystemPackage p) {

        return putCommon(p, PACKAGE_TABLE, p.getPackageID());
    }

    @Override
    public SystemPackage getPackage(int packageID) {

        return (SystemPackage) getCommon(packageID, PACKAGE_TABLE);
    }

    private Connection getConnection() {
        try {
            Class.forName(DB_DRIVER).newInstance();
            return DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } // Load driver class into runtime
    }

    public void createTable() throws Exception {
        Exception ex = null;
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_TRUCK_TABLE);
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_INVOICE_TABLE);
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_WAREHOUSE_TABLE);
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(CREATE_PACKAGE_TABLE);
        } catch (Exception e) {
            ex = e;
        }
        if (ex != null)
            throw ex;
    }

    public void dropTable() throws Exception {
        Exception ex = null;
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + TRUCK_TABLE); // Only run this once
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + INVOICE_TABLE); // Only run this once
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + WAREHOUSE_TABLE); // Only run this once
        } catch (Exception e) {
            ex = e;
        }
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("drop table " + PACKAGE_TABLE); // Only run this once
        } catch (Exception e) {
            ex = e;
        }
        if (ex != null)
            throw ex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.DatabaseSupport#getInvoiceByName(java.lang.String)
     */
    @Override
    public List<Invoice> getInvoiceByName(String customerName) {

        List<Invoice> toReturn = new ArrayList<>();

        if (customerName == null || customerName.trim().length() == 0)
            return toReturn;

        try (Connection conn = getConnection()) {
            Invoice i;
            PreparedStatement ps = conn.prepareStatement("select * from " + INVOICE_TABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                i = (Invoice) ois.readObject();
                if (i.getCustomerName() != null && i.getCustomerName().contains(customerName))
                    toReturn.add(i);

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
