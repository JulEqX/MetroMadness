package com.unimelb.swen30006.metromadness.routers;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class SimpleRouter implements PassengerRouter {

	@Override
	public boolean shouldLeave(Station current, Passenger p) {
		if(current.equals(p.getDestination())){
			return true;
		}
		
		//If this station is an intersection
		if(current.getLines().size() > 1){
			for(Line aLine : current.getLines()){
				if(c)
				for(Station aStation : aLine.getStations()){
					
				}
			}
		}
		else {
			return false;
		}		
	}

}
