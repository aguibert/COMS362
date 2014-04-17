/**
 * 
 */
package system.truck;

import java.io.Serializable;
import java.util.List;

/**
 * @author Andrew
 * 
 */
public class TruckImpl implements Truck, Serializable {

    private static final long serialVersionUID = 6133248066153670641L;
    private final int ID;

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
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#setLocation(java.lang.String)
     */
    @Override
    public boolean setLocation(String location) {
        // TODO Auto-generated method stub
        return false;
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
    public boolean addPackage(String packageID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#removePackage(java.lang.String)
     */
    @Override
    public boolean removePackage(String packageID) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getPackages()
     */
    @Override
    public List<String> getPackages() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#getState()
     */
    @Override
    public TRUCK_STATE getState() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see system.truck.Truck#setState(system.truck.Truck.TRUCK_STATE)
     */
    @Override
    public boolean setState(TRUCK_STATE newState) {
        // TODO Auto-generated method stub
        return false;
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
