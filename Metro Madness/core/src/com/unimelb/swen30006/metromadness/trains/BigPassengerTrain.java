/**
 * Daewin (679182)
 * Julian (677491)
 * Rahul (696272)
 * Last Edited: 17th April 2016
 * SWEN30006, Semester 1
 */
package com.unimelb.swen30006.metromadness.trains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * Variation of train that has a large passenger size limit
 *
 */

public class BigPassengerTrain extends Train {
	/**
	 * Limit of the number of passengers for small train
	 */
	private static final int PASSENGERLIMIT  = 80;

	public BigPassengerTrain(Line trainLine, Station start, boolean forward) {
		super(trainLine, start, forward);
	}

	@Override
	public void embark(Passenger p) throws Exception {
		if (this.passengers.size() > PASSENGERLIMIT) {
			throw new Exception();
		}
		this.passengers.add(p);
	}

	@Override
	public void render(ShapeRenderer renderer) {
		if (!this.inStation()) {
			Color col = this.isForward() ? FORWARD_COLOUR : BACKWARD_COLOUR;
			float percentage = this.passengers.size() / 20f;
			renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
			renderer.circle(this.pos.x, this.pos.y,
					TRAIN_WIDTH * (1 + percentage));
		}
	}

}
