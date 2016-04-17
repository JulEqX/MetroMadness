/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */

package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * A single passenger that can use trains to get to specific station
 * destinations on the metro map
 *
 */
public class Passenger {
	/** The beginning station */
	private Station begining;
	/** The destination station */
	private Station destination;
	/** The total travel time */
	private float travelTime;
	/** Variable to check if passenger has reached destination */
	private boolean reachedDestination;
	/** The list of stations the passenger needs disembark */
	private ArrayList<Station> travelStations;

	public Passenger(Station start, ArrayList<Station> travelStations, Station end) {
		this.begining = start;
		this.destination = end;
		this.travelStations = travelStations;
		this.reachedDestination = false;
		this.travelTime = 0;
	}

	/**
	 * Updates the travel time of passenger
	 * @param time The time taken
	 */
	public void update(float time) {
		if (!this.reachedDestination) {
			this.travelTime += time;
		}
	}

	/**
	 * @return beginning station of passenger
	 */
	public Station getBegining() {
		return begining;
	}

	/**
	 * @return list of stations to disembark
	 */
	public ArrayList<Station> getTravelStations() {
		return travelStations;
	}

	/**
	 * @return final station to disembark
	 */
	public Station getDestination() {
		return destination;
	}

	/**
	 * @return current total travel time
	 */
	public float getTravelTime() {
		return travelTime;
	}

	/**
	 * @return whether the final station has been reached
	 */
	public boolean isReachedDestination() {
		return reachedDestination;
	}

}
