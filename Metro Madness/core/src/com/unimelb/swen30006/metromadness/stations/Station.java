package com.unimelb.swen30006.metromadness.stations;

import java.awt.geom.Point2D;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Station {

	/** Number of platforms at the station */
	protected static final int PLATFORMS = 2;
	/** Position of station on map */
	private Point2D.Float position;
	/** Radius of station on map */
	private static final float RADIUS = 6;
	/** Segments of the circle */
	private static final int NUM_CIRCLE_SEGMENTS = 100;
	/** Name of stations */
	private String name;
	/** Lines that go through the station */
	private ArrayList<Line> lines; // Station is on 1 or many lines
	/** Trains that are at the station */
	protected ArrayList<Train> trains;
	/** Departure time of a train at this station */
	public final float DEPARTURE_TIME = 2;
	/** Router that routes passengers */
	private PassengerRouter router;

	public Station(float x, float y, PassengerRouter router, String name) {
		this.setName(name);
		this.router = router;
		this.setPosition(new Point2D.Float(x, y));
		this.lines = new ArrayList<Line>();
		this.trains = new ArrayList<Train>();
	}

	/**
	 * Registers a line to go through this station
	 * 
	 * @param l
	 *            Line class of line passing through station
	 */
	public void registerLine(Line l) {
		this.lines.add(l);
	}

	/**
	 * Renders station and its lines
	 * 
	 * @param renderer
	 *            Render object that renders points, lines, shape outlines and
	 *            filled shapes.
	 */
	public void render(ShapeRenderer renderer) {

		float currentRadius = RADIUS;
		for (int i = 0; i < this.lines.size(); i++) {
			Line l = this.lines.get(i);
			renderer.setColor(l.getLineColour());
			renderer.circle(this.getPosition().x, this.getPosition().y,
					currentRadius, NUM_CIRCLE_SEGMENTS);
			currentRadius = currentRadius - 1;
		}

		// Calculate the percentage
		float t = this.trains.size() / (float) PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		renderer.setColor(c);
		renderer.circle(this.getPosition().x, this.getPosition().y,
				currentRadius, NUM_CIRCLE_SEGMENTS);
	}

	/**
	 * Check if a train can enter the station
	 * 
	 * @param t
	 *            Train that wants to enter the station
	 * @throws Exception
	 *             No space for train
	 */
	public void enter(Train t) throws Exception {
		// Check is space for train to enter*/
		if (trains.size() >= PLATFORMS) {
			throw new Exception();
		} else {
			this.trains.add(t);
		}
	}

	/**
	 * Remove train from station when it departs
	 * 
	 * @param t
	 *            Train that is departing from station
	 * @throws Exception
	 */
	public void depart(Train t) throws Exception {
		if (this.trains.contains(t)) {
			this.trains.remove(t);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Check if a station can enter the station
	 * 
	 * @return whether train can enter station
	 * @throws Exception
	 */
	public boolean canEnter() throws Exception {
		return trains.size() < PLATFORMS;
	}

	/**
	 * Check if a passenger should disembark at this station
	 * 
	 * @param p
	 *            Current passenger
	 * @return Whether a passenger should leave
	 */
	public boolean shouldLeave(Passenger p) {
		return this.router.shouldLeave(this, p);
	}

	@Override
	public String toString() {
		return "Station [position=" + getPosition() + ", name=" + getName()
				+ ", trains=" + trains.size() + ", router=" + router + "]";
	}

	/**
	 * @return lines going through station
	 */
	public ArrayList<Line> getLines() {
		return this.lines;
	}

	/**
	 * @return position of station
	 */
	public Point2D.Float getPosition() {
		return position;
	}

	/**
	 * Sets position on map of station
	 * 
	 * @param position
	 *            Coordinate position on map
	 */
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}

	/**
	 * @return name of station
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            sets name of station
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isActive() {
		return false;
	}

}
