package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerBulletActor extends Actor {

	private static final float BULLET_HEIGHT = 16f;
	private static final float BULLET_SCREEN_HEIGHT_LIMIT = Gdx.graphics.getHeight() + BULLET_HEIGHT;
	private static final float SPEED = 1200f;    // speed in seconds

	private Texture texture;
	private boolean inPlay = false;
	private float x = 0f;
	private float y = 0f;

	PlayerBulletActor(Texture texture) {
		this.texture = texture;
	}

	void fire(float x, float y) {
		if (inPlay) return;
		this.x = x + 14f;
		this.y = y + 16f;
		inPlay = true;
	}

	@Override
	public void act(float delta) {  // note: delta is time in seconds, not milliseconds
		if(!inPlay) return;
		y += SPEED * delta;
		if (y >= BULLET_SCREEN_HEIGHT_LIMIT) inPlay = false;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(!inPlay) return;
		super.draw(batch, parentAlpha);
		batch.draw(texture, x, y);
	}

}
