/**
 * 
 */
package system.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import system.DatabaseSupportImpl;
import system.warehouse.Warehouse;
import system.warehouse.WarehouseManager;
import system.warehouse.WarehouseManagerImpl;

public class Test {

    public static void main(String args[]) throws Exception {

//        getGoogleMapsPath();
        DatabaseSupportImpl db = new DatabaseSupportImpl();

//        db.dropTable();
//        db.createTable();

        WarehouseManager wm = WarehouseManagerImpl.getInstance();
        wm.createWarehouse();
        wm.createWarehouse();
        wm.createWarehouse();

        Warehouse wAfter = db.getWareHouse(1);
        for (int i : wAfter.getPackages()) {
            System.out.println("Got package: " + i);
        }
    }

    private static void getGoogleMapsPath()
    {
        String urlString = "https://www.google.com/maps/dir/4700+Mortensen+Rd,+Ames,+IA+50014/8825+Pine+Crest+Ln+NW,+Rochester,+MN+55901/am=t";
        //https://www.google.com/maps/dir/4700+Mortensen+Rd,+Ames,+IA+50014/8825+Pine+Crest+Ln+NW,+Rochester,+MN+55901/@43.3106759,-93.0721918,8z/am=t/data=!4m13!4m12!1m5!1m1!1s0x87ee79dd767fc371:0x719cea1b0dc245fe!2m2!1d-93.6835045!2d42.0119059!1m5!1m1!1s0x87f761953886a555:0x31ceae98f26f158b!2m2!1d-92.4809889!2d44.127393

        try {
            URL url = new URL(urlString);
            URLConnection cnt = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(cnt.getInputStream()));
            String content = br.readLine();
            br.close();

            System.out.println(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
