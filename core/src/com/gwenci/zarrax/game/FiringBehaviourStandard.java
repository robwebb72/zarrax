package com.gwenci.zarrax.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class FiringBehaviourStandard implements IFiringBehaviour {

	long lastTimeFired = TimeUtils.millis();

	public FiringBehaviourStandard() {

	}

	@Override
	public void fire(float chanceToFire) {

	}



	public boolean canFire() {
		return TimeUtils.timeSinceMillis(lastTimeFired) > 1000;
	}

	public boolean isFiring(float chanceToFire) {
		boolean firing = (MathUtils.random(1.0f) < chanceToFire) && canFire();

		if (firing) {
			lastTimeFired = TimeUtils.millis();
		}
		return firing;
	}




}
