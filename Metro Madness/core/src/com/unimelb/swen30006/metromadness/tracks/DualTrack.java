/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */
package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D.Float;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * Variation of track that allows travel in a forwards and backwards direction
 *
 */
public class DualTrack extends Track {
	/** Forward part of track is occupied */
	private boolean forwardOccupied;
	/** Backward part of track is occupied */
	private boolean backwardOccupied;

	public DualTrack(Float start, Float end, Color col) {
		super(start, end, col);
		this.forwardOccupied = false;
		this.backwardOccupied = false;
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y,
				LINE_WIDTH);
		renderer.setColor(new Color(245f / 255f, 245f / 255f, 245f / 255f, 0.5f)
				.lerp(this.trackColour, 0.5f));
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y,
				LINE_WIDTH / 3);
		renderer.setColor(this.trackColour);
	}

	@Override
	public void enter(Train t) {
		if (t.isForward()) {
			this.forwardOccupied = true;
		} else {
			this.backwardOccupied = true;
		}
	}

	@Override
	public boolean canEnter(boolean forward) {
		if (forward) {
			return !this.forwardOccupied;
		} else {
			return !this.backwardOccupied;
		}
	}

	@Override
	public void leave(Train t) {
		if (t.isForward()) {
			this.forwardOccupied = false;
		} else {
			this.backwardOccupied = false;
		}
	}

}
