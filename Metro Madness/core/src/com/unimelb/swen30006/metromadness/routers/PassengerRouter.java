/**
 * 
 */
package com.unimelb.swen30006.metromadness.routers;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A routing system that check if a passenger has reached a destination
 */
public interface PassengerRouter {

	/**
	 * Check whether the passenger should leave at provided station
	 * 
	 * @param current
	 *            current station train is at
	 * @param p
	 *            passenger to check for
	 * @return whether a passenger should leave
	 */
	public boolean shouldLeave(Station current, Passenger p);

}
