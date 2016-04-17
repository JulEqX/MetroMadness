/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */
package com.unimelb.swen30006.metromadness.trains;

import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.*;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

/**
 * Train that can traverse through the map and can enter and exit stations
 */
public class Train {

	/** States the train can be in */
	private enum State {
		IN_STATION, READY_DEPART, ON_ROUTE, WAITING_ENTRY, FROM_DEPOT, TO_DEPOT
	}

	/** Colour of a forward moving train */
	protected static final Color FORWARD_COLOUR = Color.ORANGE;
	/** Colour of a backwards moving train */
	protected static final Color BACKWARD_COLOUR = Color.VIOLET;
	/** The draw width of the train */
	protected static final float TRAIN_WIDTH = 4;
	/** The speed of the train */
	private static final float TRAIN_SPEED = 50f;
	/** Max number of trips a train can take before going back to the depot */
	private static final int MAX_TRIPS = 1;
	/** Passengers currently on train */
	protected ArrayList<Passenger> passengers;
	/** Number of trips train has taken */
	private int tripCounter = 0;
	/** Check of train has disembarked */
	private boolean disembarked;
	/** Direction of travel */
	private boolean forward;
	/** Current line the station is on */
	private Line trainLine;;
	/** Departure timer */
	private float departureTimer;
	/** The station that the train is on */
	private Station station;
	/** The track that the train is on */
	private Track track;
	/** The position of the train on the map */
	protected Point2D.Float pos;
	/** Current state of train */
	private State state;

	public Train(Line trainLine, Station start, boolean forward) {
		this.trainLine = trainLine;
		this.station = start;
		this.state = State.FROM_DEPOT;
		this.setForward(forward);
		this.passengers = new ArrayList<Passenger>();
	}

	/**
	 * Updates the current train state
	 * 
	 * @param delta
	 *            The current change of time
	 */
	public void update(float delta) {
		// Update all passengers
		for (Passenger p : this.passengers) {
			p.update(delta);
		}

		// Update the state
		switch (this.state) {
		case FROM_DEPOT:
			// We have our station initialized we just need to retrieve the next
			// track, enter the
			// current station officially and mark as in station
			try {
				if (this.station.canEnter()) {
					// Train enters the station
					this.station.enter(this);
					// Position of this train is position of station
					this.pos = (Point2D.Float) this.station.getPosition()
							.clone();
					// Update state
					this.state = State.IN_STATION;

					this.disembarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case IN_STATION:

			// When in station we want to disembark passengers
			// and wait 10 seconds for incoming passengers
			if (!this.disembarked) {
				// Disembark passengers
				this.disembark();
				this.departureTimer = this.station.DEPARTURE_TIME;
				this.disembarked = true;
			} else {
				// Count down if departure timer.
				if (this.departureTimer > 0) {
					this.departureTimer -= delta;
				} else {
					// We are ready to depart, find the next track and wait
					// until we can enter
					try {
						boolean endOfLine = this.trainLine
								.endOfLine(this.station);
						if (endOfLine) {
							this.setForward(!this.isForward());
							this.tripCounter += 1;
						}
						// If number of trips is greater than max trips, return
						// to depot
						if (tripCounter > MAX_TRIPS) {
							tripCounter = 0;
							this.state = State.TO_DEPOT;
						} else {
							this.track = this.trainLine.nextTrack(this.station,
									this.isForward());
							this.state = State.READY_DEPART;
						}
						break;
					} catch (Exception e) {
						// Massive error.
						return;
					}
				}
			}
			break;
		case READY_DEPART:
			// When ready to depart, check that the track is clear and if
			// so, then occupy it if possible.
			if (this.track.canEnter(this.isForward())) {
				try {
					// Find the next
					Station next = this.trainLine.nextStation(this.station,
							this.isForward());
					// Depart our current station
					this.station.depart(this);
					this.station = next;

				} catch (Exception e) {
					// e.printStackTrace();
				}
				this.track.enter(this);
				this.state = State.ON_ROUTE;
			}
			break;
		case ON_ROUTE:
			// Checkout if we have reached the new station
			if (this.pos.distance(this.station.getPosition()) < 10) {
				this.state = State.WAITING_ENTRY;
			} else {
				move(delta);
			}
			break;
		case WAITING_ENTRY:
			// Waiting to enter, we need to check the station has room and if so
			// then we need to enter, otherwise we just wait
			try {
				if (this.station.canEnter()) {
					this.track.leave(this);
					this.pos = (Point2D.Float) this.station.getPosition()
							.clone();
					this.station.enter(this);
					this.state = State.IN_STATION;
					this.disembarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case TO_DEPOT:
			// If current station is active, remove passengers and add to
			// waiting list else go to next active station
			if (this.station.isActive()) {
				Iterator<Passenger> disembarkIterator = this.passengers
						.iterator();
				while (disembarkIterator.hasNext()) {
					Passenger p = disembarkIterator.next();
					((ActiveStation) this.station).addPassenger(p);
					disembarkIterator.remove();
				}
				try {
					Station next = this.trainLine.nextStation(this.station,
							this.isForward());
					// Depart our current station
					this.station.depart(this);
					this.station = next;
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.state = State.FROM_DEPOT;
			} else {
				this.state = State.READY_DEPART;
			}
			break;
		}

	}

	/**
	 * @return the current line the train is on
	 */
	public Line getTrainLine() {
		return trainLine;
	}

	/**
	 * Moves the trains to a new location
	 * 
	 * @param delta
	 *            The current change in time
	 */
	public void move(float delta) {
		// Work out where we're going
		float angle = angleAlongLine(this.pos.x, this.pos.y,
				this.station.getPosition().x, this.station.getPosition().y);
		float newX = this.pos.x
				+ (float) (Math.cos(angle) * delta * TRAIN_SPEED);
		float newY = this.pos.y
				+ (float) (Math.sin(angle) * delta * TRAIN_SPEED);
		this.pos.setLocation(newX, newY);
	}

	/**
	 * Board a passenger on to the train
	 * 
	 * @param p
	 *            Passenger to board
	 * @throws Exception
	 */
	public void embark(Passenger p) throws Exception {
		throw new Exception();
	}

	/**
	 * Passenger disembarks from train. Adds passengers that are going to a new
	 * line to the station's waiting list
	 */
	public void disembark() {
		// List of pssengers that are disembarking
		ArrayList<Passenger> disembarking = new ArrayList<Passenger>();
		Iterator<Passenger> disembarkIterator = this.passengers.iterator();
		// For each passenger on the train
		while (disembarkIterator.hasNext()) {
			Passenger p = disembarkIterator.next();
			// If passenger should leave
			if (this.station.shouldLeave(p)) {
				disembarking.add(p);
				disembarkIterator.remove();
			}
		}

		// If the station is active and passengers can board trains from it
		if (this.station.isActive()) {
			// Check if disembarked passengers are waiting for another train
			Iterator<Passenger> disembarkingIterator = disembarking.iterator();
			while (disembarkingIterator.hasNext()) {
				Passenger p = disembarkingIterator.next();
				// If they have other stations in their travel list, add to
				// waiting list
				if (p.getTravelStations().size() > 0) {
					((ActiveStation) this.station).addPassenger(p);

				}
			}
		}

		// Check if disembarked passengers are waiting for another train
		Iterator<Passenger> disembarkingIterator = disembarking.iterator();
		while (disembarkingIterator.hasNext()) {
			Passenger p = disembarkingIterator.next();

			if (p.getTravelStations().size() > 0) {
				ActiveStation active = (ActiveStation) getStation();
				active.getWaiting().add(p);

			}
		}
	}

	/**
	 * @return current station the train is on
	 */
	public Station getStation() {
		return station;
	}

	@Override
	public String toString() {
		return "Train [line=" + this.trainLine.getName() + ", departureTimer="
				+ departureTimer + ", pos=" + pos + ", forward=" + isForward()
				+ ", state=" + state + ", disembarked=" + disembarked + "]";
	}

	/**
	 * @return if train is in a station
	 */
	public boolean inStation() {
		return (this.state == State.IN_STATION
				|| this.state == State.READY_DEPART);
	}

	/**
	 * Calculate the angle of travel along the line
	 * 
	 * @param x1
	 *            Train's x position
	 * @param y1
	 *            Train's y position
	 * @param x2
	 *            Station's x position
	 * @param y2
	 *            Station's y position
	 * @return New angle
	 */
	public float angleAlongLine(float x1, float y1, float x2, float y2) {
		return (float) Math.atan2((y2 - y1), (x2 - x1));
	}

	/**
	 * Renders the train while it is not in a station
	 * 
	 * @param renderer
	 *            Render object that renders points, lines, shape outlines and
	 *            filled shapes.
	 */
	public void render(ShapeRenderer renderer) {
		if (!this.inStation()) {
			Color col = this.isForward() ? FORWARD_COLOUR : BACKWARD_COLOUR;
			renderer.setColor(col);
			renderer.circle(this.pos.x, this.pos.y, TRAIN_WIDTH);
		}
	}

	/**
	 * @return if train is going forward
	 */
	public boolean isForward() {
		return forward;
	}

	/**
	 * Set the direction of the train
	 * 
	 * @param forward
	 *            New directon of the train
	 */
	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public int getTripCounter() {
		return tripCounter;
	}

	public void setTripCounter(int tripCounter) {
		this.tripCounter = tripCounter;
	}

}
