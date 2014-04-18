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

    @Override
    public boolean putTruck(Truck t) {

        try (Connection conn = getConnection()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(t);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            PreparedStatement ps = conn.prepareStatement("insert into " + TRUCK_TABLE + " (ID, javaObject) values(?, ?)");
            ps.setInt(1, t.getID());
            ps.setObject(2, data);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Truck getTruck(int truckID) {

        Truck t = null;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + TRUCK_TABLE + " where id=" + truckID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                t = (Truck) ois.readObject();

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    @Override
    public boolean putInvoice(Invoice i) {

        try (Connection conn = getConnection()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(i);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            PreparedStatement ps = conn.prepareStatement("insert into " + INVOICE_TABLE + " (ID, javaObject) values(?, ?)");
            ps.setInt(1, i.getID());
            ps.setObject(2, data);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Invoice getInvoice(int invoiceID) {

        Invoice i = null;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + INVOICE_TABLE + " where id=" + invoiceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                i = (Invoice) ois.readObject();

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return i;
    }

    @Override
    public boolean putWareHouse(Warehouse w) {

        try (Connection conn = getConnection()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(w);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            PreparedStatement ps = conn.prepareStatement("insert into " + WAREHOUSE_TABLE + " (ID, javaObject) values(?, ?)");
            ps.setInt(1, w.getID());
            ps.setObject(2, data);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Warehouse getWareHouse(int warehouseID) {

        Warehouse w = null;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + WAREHOUSE_TABLE + " where id=" + warehouseID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                w = (Warehouse) ois.readObject();

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return w;
    }

    @Override
    public boolean putPackage(SystemPackage p) {
        try (Connection conn = getConnection()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(p);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            PreparedStatement ps = conn.prepareStatement("insert into " + PACKAGE_TABLE + " (ID, javaObject) values(?, ?)");
            ps.setInt(1, p.getPackageID());
            ps.setObject(2, data);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public SystemPackage getPackage(int packageID) {
        SystemPackage p = null;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from " + PACKAGE_TABLE + " where id=" + packageID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                p = (SystemPackage) ois.readObject();

                ois.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return p;
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

}
