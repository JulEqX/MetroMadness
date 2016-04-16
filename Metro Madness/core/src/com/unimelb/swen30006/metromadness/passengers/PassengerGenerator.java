package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.stations.ActiveStation;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * Generates a specified number of random passengers given a starting station
 *
 */
public class PassengerGenerator {

	/* The station that passengers are getting on */
	public Station start;

	/* Maximum number of passengers to generate */
	public float maxVolume;

	/* Randomise number of line travels */
	private static final int RANDOM = 10;

	public PassengerGenerator(Station s, float max) {
		this.start = s;
		this.maxVolume = max;
	}

	/**
	 * Generates a random number of passengers that is less than or equal to the
	 * max volume
	 * 
	 * @return an arraylist of passengers
	 */
	public Passenger[] generatePassengers() {
		int count = (int) (Math.random() * maxVolume);
		Passenger[] passengers = new Passenger[count];
		for (int i = 0; i < count; i++) {
			passengers[i] = generatePassenger();
		}
		return passengers;
	}

	/**
	 * Generates a single passenger
	 * 
	 * @return passenger class
	 */
	public Passenger generatePassenger() {
		// Chooses random number of lines to travel on
		int numLineTravels = (int) (Math.random() * (RANDOM)) + 1;

		// Lists that hold what stations and lines the passenger travels to
		ArrayList<Station> travelStations = new ArrayList<Station>();

		Station temporary = start;
		for (int i = 0; i < numLineTravels; i++) {

			// Get a random line
			Line newLine = temporary.getLines().get((int) (Math.random() * temporary.getLines().size()));

			// Get a random station on that line
			Station newStation = newLine.getStations().get((int) (Math.random() * newLine.getStations().size()));

			// Make sure that station is an active station
			while (!(start.getClass().toString().equals(newStation.getClass().toString()))) {
				newStation = newLine.getStations().get((int) (Math.random() * newLine.getStations().size()));
			}

			// Add new station to travel list
			travelStations.add(newStation);
			temporary = newStation;

		}
		// Destination is the last station on the travel list
		Station destination = travelStations.get(travelStations.size() - 1);
		travelStations.remove(destination);
		return new Passenger(start, travelStations, destination);
	}

}
