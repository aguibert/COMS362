/**
 * 
 */
package system.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {

    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String TABLE_NAME = "people";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                                               "(" +
                                               "id int NOT NULL AUTO_GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                                               "lastName varchar(40) NOT NULL," +
                                               "firstName varchar(40) NOT NULL," +
                                               "PRIMARY KEY (id)" +
                                               ")";

    public static void main(String args[]) throws Exception {

        Class.forName(driver).newInstance(); // Load driver class into runtime

        Connection conn = DriverManager.getConnection("jdbc:derby:myDB");

//        conn.createStatement().execute("drop table people"); // Only run this once
//        conn.createStatement().execute(CREATE_TABLE);

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " (lastName, firstName) VALUES ('guibert', 'andrew')", new int[] { 1 });
            ps.executeUpdate();
            ResultSet rs1 = ps.getGeneratedKeys();
            rs1.next();
            System.out.println("Added a new column with id: " + rs1.getInt(1));
            rs1.close();
            ps.close();

            Statement s = conn.createStatement();
            s.execute("SELECT * FROM " + TABLE_NAME);
            ResultSet rs = s.getResultSet();
            System.out.println("ID  Last       First");
            System.out.println("----------------------");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "   " + rs.getString(2) + "   " + rs.getString(3));
            }
            rs.close();
            s.close();
        } finally {
            conn.close();
        }
    }
}
