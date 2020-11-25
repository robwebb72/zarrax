package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class AlienActor1 extends BaseAlien {


	public AlienActor1(Texture texture) {
		super(texture, 3, 0.2f);
	}

	@Override
	public int getScore() {
		return 10;
	}

	@Override
	public boolean canFire() {
		return TimeUtils.timeSinceMillis(lastTimeFired) > 1000;
	}

	@Override
	public boolean isFiring(float chanceToFire) {
		boolean firing = (MathUtils.random(1.0f) < chanceToFire) && canFire();

		if (firing) {
			lastTimeFired = TimeUtils.millis();
		}
		return firing;
	}
}