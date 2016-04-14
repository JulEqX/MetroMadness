package com.unimelb.swen30006.metromadness;

import java.util.ArrayList;
import java.lang.Class.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * 
 *
 */
public class Simulation {
	
	/**
	 * 
	 */
	private ArrayList<Station> stations;
	private ArrayList<Line> lines; 
	private ArrayList<Train> trains; 
	
	/**
	 * 
	 * @param fileName
	 */
	public Simulation(String fileName){
		// Create a map reader and read in the file
		MapReader map = new MapReader(fileName);
		map.process();
		
		// Create a list of lines
		this.lines = new ArrayList<Line>();
		this.lines.addAll(map.getLines());
				
		// Create a list of stations
		this.stations = new ArrayList<Station>();
		this.stations.addAll(map.getStations());
		
		// Create a list of trains
		this.trains = new ArrayList<Train>();
		this.trains.addAll(map.getTrains());
		
		Class cls;
		lg
		for(Station aStation : stations){
			cls = aStation.getClass();
			if(cls.getSimpleName().equalsIgnoreCase("ActiveStation")){
				aStation.addLineGenerator(lg);
			}
			
		}
	}
	
	
	/**
	 *  Update all the trains in the simulation
	 */
	public void update(){
		// Update all the trains
		for(Train train: this.trains){
			train.update(Gdx.graphics.getDeltaTime());
		}
	}
	
	/**
	 * 
	 * @param renderer
	 */
	public void render(ShapeRenderer renderer){
		for(Line line: this.lines){
			line.render(renderer);
		}

		for(Train train: this.trains){
			train.render(renderer);
		}
		for(Station station: this.stations){
			station.render(renderer);
		}
	}
}
