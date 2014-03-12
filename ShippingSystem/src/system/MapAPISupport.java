/**
 * 
 */
package system;

import java.util.List;

import system.truck.Route;

/**
 * @author Andrew
 * 
 */
public interface MapAPISupport {

    public Route createRoute(List<String> destinations);
}
