package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class Passenger {

	public Station begining;
	public Station destination;
	public float travelTime;
	public boolean reachedDestination;

	public ArrayList<Station> travelStations;
	
	public Passenger(Station start, ArrayList<Station> travelStations, Station end){
		this.begining = start;
		this.destination = end;
		this.travelStations = travelStations;
		this.reachedDestination = false;
		this.travelTime = 0;
	}

	public ArrayList<Station> getTravelStations() {
		return travelStations;
	}

	public void update(float time){
		if(!this.reachedDestination){
			this.travelTime += time;
		}
	}

	public Station getBegining() {
		return begining;
	}

	public Station getDestination() {
		return destination;
	}

	public float getTravelTime() {
		return travelTime;
	}

	public boolean isReachedDestination() {
		return reachedDestination;
	}
	

	
	
}
