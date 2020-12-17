package com.gwenci.zarrax.game.aiming;

import com.badlogic.gdx.math.Vector2;
import com.gwenci.zarrax.BaseActor;

public interface IAimingBehaviour {
	void initialise(BaseActor source, BaseActor target, float bulletSpeed, float defaultAngle, float angleRange);
	Vector2 getFireVector();
}
