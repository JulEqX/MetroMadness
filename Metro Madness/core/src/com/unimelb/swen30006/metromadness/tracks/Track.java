package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * Lines that is in between two stations. Can have one train that can travel on
 * it.
 */
public class Track {
	protected static final int LINE_WIDTH = 6;
	/** Starting point of the track */
	protected Point2D.Float startPos;
	/** End point of the track */
	protected Point2D.Float endPos;
	/** Colour of the track */
	protected Color trackColour;
	/** To check if track is occupied */
	private boolean occupied;

	public Track(Point2D.Float start, Point2D.Float end, Color trackCol) {
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
		this.occupied = false;
	}

	/**
	 * Renders the track on the map
	 * 
	 * @param renderer
	 *            Object that renders points, lines, shape outlines and filled
	 *            shapes.
	 */
	public void render(ShapeRenderer renderer) {
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y,
				LINE_WIDTH);
	}

	/**
	 * Check if the track has a train on it
	 * 
	 * @param forward
	 *            Direction of travel on track
	 * @return Whether it is occupied or not
	 */
	public boolean canEnter(boolean forward) {
		return !this.occupied;
	}

	/**
	 * Train is running on the track
	 * 
	 * @param t
	 *            Train that is on this track
	 */
	public void enter(Train t) {
		this.occupied = true;
	}

	@Override
	public String toString() {
		return "Track [startPos=" + startPos + ", endPos=" + endPos
				+ ", trackColour=" + trackColour + ", occupied=" + occupied
				+ "]";
	}

	/**
	 * Train is leaving the track
	 * 
	 * @param t
	 *            The train that has left the track
	 */
	public void leave(Train t) {
		this.occupied = false;
	}
}
