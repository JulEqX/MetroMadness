package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.stations.ActiveStation;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class PassengerGenerator {
	
	// The station that passengers are getting on
	public Station start;
	
	// The max volume
	public float maxVolume;
	
	private static int RANDOM = 10;

	
	public PassengerGenerator(Station s, float max){
		this.start = s;
		this.maxVolume = max;
	}
	
	public Passenger[] generatePassengers(){
		int count = (int) (Math.random()*maxVolume);
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger();
		}
		return passengers;
	}
	
	public Passenger generatePassenger(){
		//Chooses random number of lines to travel on
		int numLineTravels = (int) (Math.random()*(RANDOM))+1;
		
		//Lists that hold what stations and lines the passenger travels to
		ArrayList<Station> travelStations = new ArrayList<Station>();
		
		Station temporary = start;
		for(int i = 0; i < numLineTravels; i++) {
			
			//Get a random line
			Line newLine = temporary.getLines().get((int)(Math.random()*temporary.getLines().size()));
			
			//Get a random station on that line
			Station newStation = newLine.getStations().get((int)(Math.random()*newLine.getStations().size()));
			
			//Make sure that station is an active station
			while(!(start.getClass().toString().equals(newStation.getClass().toString()))) {
				newStation = newLine.getStations().get((int)(Math.random()*newLine.getStations().size()));
			}
			
			//Add new station to travel list
			travelStations.add(newStation);
			temporary = newStation;

		}
		//Destination is a station on the last travel line
		Station destination = travelStations.get(travelStations.size()-1);
		travelStations.remove(destination);
		return new Passenger(start, travelStations, destination);
	}
	
}
