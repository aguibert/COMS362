/**
 * 
 */
package system.example;

import system.DatabaseSupportImpl;
import system.warehouse.Warehouse;
import system.warehouse.WarehouseManager;
import system.warehouse.WarehouseManagerImpl;

public class Test {

    public static void main(String args[]) throws Exception {

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
}
