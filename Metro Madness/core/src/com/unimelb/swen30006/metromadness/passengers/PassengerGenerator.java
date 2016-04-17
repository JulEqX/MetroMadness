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
 * Generates a random number of passengers up to a maximum value.
 *
 */
public class PassengerGenerator {

	/** Starting station to generate passengers */
	private Station start;
	/** Maximum number of passengers that should be generated */
	private float maxVolume;
	/**
	 * Generate a random number of line travels. Used for simulating multi line
	 * travel
	 */
	private static final int RANDOM = 10;

	public PassengerGenerator(Station s, float max) {
		this.start = s;
		this.maxVolume = max;
	}

	/**
	 * Generates a list of random passengers
	 * 
	 * @return an arraylist of passengers
	 */
	public Passenger[] generatePassengers() {
		// Get a random number of passengers to generate
		int count = (int) (Math.random() * maxVolume);
		Passenger[] passengers = new Passenger[count];
		for (int i = 0; i < count; i++) {
			passengers[i] = generatePassenger();
		}
		return passengers;
	}

	/** Generates a single passenger with random stations to travel to */
	private Passenger generatePassenger() {
		// Chooses random number of lines to travel on
		int numLineTravels = (int) (Math.random() * (RANDOM)) + 1;

		// Lists that hold what stations and lines the passenger travels to
		ArrayList<Station> travelStations = new ArrayList<Station>();

		Station temporary = start;
		for (int i = 0; i < numLineTravels; i++) {

			// Get a random line from current position
			Line newLine = temporary.getLines()
					.get((int) (Math.random() * temporary.getLines().size()));

			// Get a random station on that line
			Station newStation = newLine.getStations()
					.get((int) (Math.random() * newLine.getStations().size()));

			// Make sure that station is an active station
			Class<?> cls = newStation.getClass();
			while (!(cls.getSimpleName().equalsIgnoreCase("ActiveStation"))
					&& !(travelStations.contains(newStation))) {
				newStation = newLine.getStations().get(
						(int) (Math.random() * newLine.getStations().size()));
				cls = newStation.getClass();
			}

			// Add new station to travel list
			travelStations.add(newStation);
			temporary = newStation;

		}
		// Destination is a station on the last travel line
		Station destination = travelStations.get(travelStations.size() - 1);
		travelStations.remove(destination);
		return new Passenger(start, travelStations, destination);
	}

}
