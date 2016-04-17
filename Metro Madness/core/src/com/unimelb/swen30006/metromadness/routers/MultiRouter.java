/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */
package com.unimelb.swen30006.metromadness.routers;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A passenger router that is used to travel on multiple lines
 */
public class MultiRouter implements PassengerRouter {

	@Override
	public boolean shouldLeave(Station current, Passenger p) {
		//Check if destination is reached
		if(current.equals(p.getDestination())) {
			return true;
		//Check if reached a station on travel list
		} else if(p.getTravelStations().size()>0 && current.equals(p.getTravelStations().get(0))) {
			p.getTravelStations().remove(0);
			return true;
		}
		//Have not reached a desired station
		return false;
	}
}
		
