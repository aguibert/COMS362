/**
 * 
 */
package system.truck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
    private Set<Integer> packages;
    private TRUCK_STATE state;
    private String route;

    protected TruckImpl(int _id) {
        this.ID = _id;
        setState(TRUCK_STATE.AVAILABLE);
        packages = new HashSet<>();
        setLocation("Warehouse");
        route = "";
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
    public String createTruckRoute() {
        Random rand = new Random();
        int randomStops, randomNumStops, i;
        randomNumStops = rand.nextInt(21);
        i = 0;
        route = "";
        while (i < randomNumStops) {
            randomStops = rand.nextInt(1000);
            route += (randomStops + " ");
            i++;
        }
        return route;
    }

    @Override
    public String refreshTruckRoute() {
        String toReturn;
        if (route == null || route.length() == 0) {
            System.out.println("ERROR: No more destinations in this route.");
            return null;
        }
        if (route.indexOf(' ') == -1) {
            toReturn = route;
            route = "";
            return toReturn;
        } else {
            toReturn = route.substring(0, route.indexOf(' '));
            route = route.substring(route.indexOf(' '), route.length());
            return toReturn;
        }
    }

    @Override
    public boolean addPackage(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;
        packages.add(packageID);
        if (sp.setState(PACKAGE_STATE.ON_TRUCK) == false)
            return false;
        if (sp.setTruck(this.ID) == false)
            return false;
        if (dbs.putPackage(sp) == false)
            return false;
        return true;
    }

    @Override
    public boolean removePackage(int packageID) {
        DatabaseSupport dbs = new DatabaseSupportImpl();
        SystemPackage sp = dbs.getPackage(packageID);
        if (sp == null)
            return false;

        if (packages.remove(packageID) == false) {
            //returns false if package not found
            System.out.println("Package " + packageID + " is not on truck.");
            return false;
        }

        // Set truck ID to -1 to indicate package is not on a truck
        if (sp.setTruck(-1) == false)
            return false;

        if (dbs.putPackage(sp) == false)
            return false;

        return true;
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
        String string = "Truck " + ID + ":\n" + " Location: " + location + "\n" + " Packages: " + packages.toString();
        string = string.concat("\n" + " State: " + state);
        return string;
    }
}
