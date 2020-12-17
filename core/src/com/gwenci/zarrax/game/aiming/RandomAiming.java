package com.gwenci.zarrax.game.aiming;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gwenci.zarrax.BaseActor;

public class RandomAiming implements IAimingBehaviour {

	private float direction;
	private float range;

	@Override
	public void initialise(BaseActor source, BaseActor target, float bulletSpeed, float defaultAngle, float angleRange) {
		direction = defaultAngle;
		range = angleRange;
	}

	@Override
	public Vector2 getFireVector() {
		Vector2 unitVector = new Vector2(1.0f, 0.0f);
		return unitVector.rotate(MathUtils.random(direction-range,direction+range));
	}
}
