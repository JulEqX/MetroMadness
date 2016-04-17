/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */

package com.unimelb.swen30006.metromadness;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * Simulates the running of a metro train service
 *
 */
public class Simulation {

	/** Stations in simulation */
	private ArrayList<Station> stations;
	/** Train lines in simulation */
	private ArrayList<Line> lines;
	/** Trains that are running in the simulation */
	private ArrayList<Train> trains;

	public Simulation(String fileName) {
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
	}

	/**
	 * Update all the trains in the simulation
	 */
	public void update() {
		// Update all the trains
		for (Train train : this.trains) {
			train.update(Gdx.graphics.getDeltaTime());
		}
	}

	/**
	 * Renders all elements on screen
	 * 
	 * @param renderer
	 *            render object
	 */
	public void render(ShapeRenderer renderer) {
		for (Line line : this.lines) {
			line.render(renderer);
		}

		for (Train train : this.trains) {
			train.render(renderer);
		}
		for (Station station : this.stations) {
			station.render(renderer);
		}
	}
}
