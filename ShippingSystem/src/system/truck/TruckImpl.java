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

/**
 * @author Andrew
 * 
 */
public class TruckImpl implements Truck, Serializable {

    private static final long serialVersionUID = 6133248066153670641L;
    private final int ID;
    private String location;
    private Set<Integer> packages = new HashSet();
    private TRUCK_STATE state;

    protected TruckImpl(int _id) {
        this.ID = _id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getLocation()
     */
    @Override
    public String getLocation() {
        return location;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#setLocation(java.lang.String)
     */
    @Override
    public boolean setLocation(String location) {
        this.location = location;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#createTruckRoute()
     */
    @Override
    public Route createTruckRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#refreshTruckRoute()
     */
    @Override
    public Route refreshTruckRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#addPackage(java.lang.String)
     */
    @Override
    public boolean addPackage(int packageID) {
        for (Integer pkg : packages) {
            if (pkg == packageID) { //check if package ID already exists
                return false; //if exists return false
            }
        }
        packages.add(packageID);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#removePackage(java.lang.String)
     */
    @Override
    public boolean removePackage(int packageID) {
        for (Integer pkg : packages) {
            if (pkg == packageID) {
                packages.remove(pkg);
                return true;
            }
        }
        return false; //returns false if package not found
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getPackages()
     */
    @Override
    public List<SystemPackage> getPackages() {
        DatabaseSupport db = new DatabaseSupportImpl();
        ArrayList<SystemPackage> pack = new ArrayList<>();
        for (int pkg : packages) {
            pack.add(db.getPackage(pkg));
        }
        return pack;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getState()
     */
    @Override
    public TRUCK_STATE getState() {
        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#setState(system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public boolean setState(TRUCK_STATE newState) {
        this.state = newState;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getID()
     */
    @Override
    public int getID() {
        return ID;
    }

}
