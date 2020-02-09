package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;

public class PlayerBulletActor extends BulletBaseActor {

	private static final float SPEED = 400f;    // speed in seconds

	PlayerBulletActor(Texture texture) {
		setTexture(texture);
	}


	boolean fire(float x, float y) {
		return super.fire(x + 14f, y + 14f, 0, SPEED);
	}

}
