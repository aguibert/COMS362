/**
 * 
 */
package system.truck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import system.DatabaseSupport;
import system.DatabaseSupportImpl;
import system.SystemPackage;
import system.SystemPackage.PACKAGE_STATE;

/**
 * @author Lucas
 */
public class TruckImpl implements Truck, Serializable {

    private static final long serialVersionUID = 6133248066153670641L;
    private final int ID;
    private String location;
    private Set<Integer> packages = new HashSet<>();
    private TRUCK_STATE state;

    protected TruckImpl(int _id) {
        this.ID = _id;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public boolean setLocation(String location) {
        this.location = location;
        return true;
    }

    @Override
    public Route createTruckRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Route refreshTruckRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addPackage(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;

        if (sp.setState(PACKAGE_STATE.ON_TRUCK) == false)
            return false;
        if (sp.setTruck(this.ID) == false)
            return false;

        if (dbs.putPackage(sp) == false)
            return false;

        return packages.add(packageID);
    }

    @Override
    public boolean removePackage(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;

        // Set truck ID to -1 to indicate package is not on a truck
        if (sp.setTruck(-1) == false)
            return false;

        if (dbs.putPackage(sp) == false)
            return false;

        return packages.remove(packageID); //returns false if package not found
    }

    @Override
    public List<Integer> getPackages() {
        return new ArrayList<Integer>(this.packages);
    }

    @Override
    public TRUCK_STATE getState() {
        return state;
    }

    @Override
    public boolean setState(TRUCK_STATE newState) {
        this.state = newState;
        return true;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String string = "hey";
        return string;
    }
}
