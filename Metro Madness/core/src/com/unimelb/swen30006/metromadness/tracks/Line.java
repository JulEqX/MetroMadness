package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A sequences of stations that are joined together in a single line
 */
public class Line {

	/** The colour of this line */
	private Color lineColour;
	/** The colour of the track */
	public Color trackColour;

	/** Name of this line */
	private String name;
	/** The stations that are on the line */
	private ArrayList<Station> stations;
	/** The tracks that are on the line connecting the stations */
	private ArrayList<Track> tracks;

	public Line(Color stationColour, Color lineColour, String name) {
		// Set the line colour
		this.setLineColour(stationColour);
		this.trackColour = lineColour;
		this.setName(name);

		// Create the data structures
		this.stations = new ArrayList<Station>();
		this.tracks = new ArrayList<Track>();
	}

	/**
	 * Add a station to the line
	 * 
	 * @param s
	 *            The station to add to the line
	 * @param two_way
	 *            Whether line to station is dual way
	 */
	public void addStation(Station s, Boolean two_way) {
		// We need to build the track if this is adding to existing stations
		if (this.stations.size() > 0) {
			// Get the last station
			Station last = this.stations.get(this.stations.size() - 1);

			// Generate a new track
			Track t;
			if (two_way) {
				t = new DualTrack(last.getPosition(), s.getPosition(),
						this.trackColour);
			} else {
				t = new Track(last.getPosition(), s.getPosition(),
						this.trackColour);
			}
			this.tracks.add(t);
		}

		// Add the station
		s.registerLine(this);
		this.stations.add(s);
	}

	@Override
	public String toString() {
		return "Line [lineColour=" + getLineColour() + ", trackColour="
				+ trackColour + ", name=" + getName() + "]";
	}

	/**
	 * Check if station is at the end of the line
	 * 
	 * @param s
	 *            Station to check
	 * @return Whether station is at end of line
	 * @throws Exception
	 */
	public boolean endOfLine(Station s) throws Exception {
		if (this.stations.contains(s)) {
			int index = this.stations.indexOf(s);
			return (index == 0 || index == this.stations.size() - 1);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Gets the next track after a specifed station
	 * 
	 * @param currentStation
	 *            The station to find the next track from
	 * @param forward
	 *            The direction of travel
	 * @return the next track
	 * @throws Exception
	 */
	public Track nextTrack(Station currentStation, boolean forward)
			throws Exception {
		if (this.stations.contains(currentStation)) {
			// Determine the track index
			int curIndex = this.stations.lastIndexOf(currentStation);
			// Increment to retrieve
			if (!forward) {
				curIndex -= 1;
			}

			// Check index is within range
			if ((curIndex < 0) || (curIndex > this.tracks.size() - 1)) {
				throw new Exception();
			} else {
				return this.tracks.get(curIndex);
			}

		} else {
			throw new Exception();
		}
	}

	/**
	 * Gets the next station after a specifed station and direction
	 * 
	 * @param s
	 *            Station to find next station from
	 * @param forward
	 *            Direction of travel
	 * @return The next station
	 * @throws Exception
	 */
	public Station nextStation(Station s, boolean forward) throws Exception {
		if (this.stations.contains(s)) {
			int curIndex = this.stations.lastIndexOf(s);
			if (forward) {
				curIndex += 1;
			} else {
				curIndex -= 1;
			}

			// Check index is within range
			if ((curIndex < 0) || (curIndex > this.stations.size() - 1)) {
				throw new Exception();
			} else {
				return this.stations.get(curIndex);
			}
		} else {
			throw new Exception();
		}
	}

	/**
	 * Renders the line to the map
	 * 
	 * @param renderer
	 *            Object that renders points, lines, shape outlines and filled
	 *            shapes.
	 */
	public void render(ShapeRenderer renderer) {
		// Set the color to our line
		renderer.setColor(trackColour);

		// Draw all the track sections
		for (Track t : this.tracks) {
			t.render(renderer);
		}
	}

	/**
	 * @return returns the stations that this line goes through
	 */
	public ArrayList<Station> getStations() {
		return this.stations;
	}

	/**
	 * @return get the colour of the line
	 */
	public Color getLineColour() {
		return lineColour;
	}

	/**
	 * Set the line to particular colour
	 * 
	 * @param lineColour
	 *            new line colour
	 */
	public void setLineColour(Color lineColour) {
		this.lineColour = lineColour;
	}

	/**
	 * @return the name of the line
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the line
	 * 
	 * @param name
	 *            new name of the line
	 */
	public void setName(String name) {
		this.name = name;
	}

}
