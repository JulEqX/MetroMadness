/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */
package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;

import java.util.Iterator;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * A variation of station that allows passengers to leave and also enter
 * a station
 */
public class ActiveStation extends Station {

	/** Generates passengers for station */
	private PassengerGenerator generator;
	/** List of passengers that are currently waiting for a train */
	private ArrayList<Passenger> waiting;

	public ActiveStation(float x, float y, PassengerRouter router, String name,
			float maxPax) {
		super(x, y, router, name);
		this.waiting = new ArrayList<Passenger>();
		this.generator = new PassengerGenerator(this, maxPax);
	}

	@Override
	public void enter(Train t) throws Exception {
		//Check if there is space for train
		if (trains.size() >= PLATFORMS) {
			throw new Exception();
		} else {
			// Add the train
			this.trains.add(t);

			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while (pIter.hasNext()) {
				Passenger p = pIter.next();
				try {
					// If current train line contains the next station the
					// passenger needs to go to
					if (t.getTrainLine().getStations()
							.contains(p.getTravelStations().get(0))) {
						t.embark(p);
						p.getTravelStations().remove(0);
						pIter.remove();
					}
				} catch (Exception e) {
					// Do nothing, already waiting
					break;
				}
			}

			// Add the new passengers
			Passenger[] ps = this.generator.generatePassengers();
			for (Passenger p : ps) {
				try {
					t.embark(p);
				} catch (Exception e) {
					//Add to waiting list if no space on train*/
					this.waiting.add(p);
				}
			}
		}
	}
	
	/**
	 * Adds a passenger to the waiting list
	 * @param p a Single passenger
	 */
	public void addPassenger(Passenger p) {
		this.waiting.add(p);
	}
	
	/**
	 * Check if this station is an active one
	 * @return true if active false otherwise
	 */
	@Override
	public boolean isActive() {
		return true;
	}
	
	/**
	 * Get waiting passenger list
	 * @return list of passengers waiting for a train
	 */
	public ArrayList<Passenger> getWaiting() {
		return waiting;
	}

}