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
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * A passenger router used to travel on a single line
 */
public class SimpleRouter implements PassengerRouter {

	@Override
	public boolean shouldLeave(Station current, Passenger p) {
		return current.equals(p.getDestination());
	}
}
