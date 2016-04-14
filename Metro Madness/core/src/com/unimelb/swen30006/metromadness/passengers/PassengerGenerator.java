package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.stations.ActiveStation;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class PassengerGenerator {
	
	// The station that passengers are getting on
	public ActiveStation s;
	// The line they are travelling on
	public ArrayList<Line> lines;
	
	// The max volume
	public float maxVolume;
	
	public PassengerGenerator(ActiveStation s, ArrayList<Line> lines, float max){
		this.s = s;
		this.lines = lines;
		this.maxVolume = max;
	}
	
	
	public void generatePassenger(){
		int count = (int) (Math.random() * maxVolume);
		Line random_line;
		Station random_destination;
		
		
		for(int i = 0; i < count; i++){
			// Pick a random station from the line
			random_line = this.lines.get((int)Math.random()*(this.lines.size()-1));
			int current_station = random_line.stations.indexOf(this.s);
			boolean forward = Math.random()>0.5f;
			
			// If we are the end of the line then set our direction forward or backward
			if(current_station == 0){
				forward = true;
			}else if (current_station == random_line.stations.size()-1){
				forward = false;
			}
			
			// Find the station
			int index = (int) ( forward ? Math.random()*(current_station+1) : Math.random()*(current_station-1));
			random_destination = random_line.stations.get(index);
			
			s.addPassenger(new Passenger(s, random_destination));
		}
	}
	
}
