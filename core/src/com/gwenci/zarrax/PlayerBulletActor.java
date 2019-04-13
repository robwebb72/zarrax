package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PlayerBulletActor extends BaseActor {

	private static final float BULLET_HEIGHT = 16f;
	private static final float BULLET_SCREEN_HEIGHT_LIMIT = SCREEN_HEIGHT + BULLET_HEIGHT;
	private static final float SPEED = 400f;    // speed in seconds

	private Texture texture;
	private boolean inPlay = false;

	PlayerBulletActor(Texture texture) {
		this.texture = texture;
		super.setBoundingRect(texture.getWidth(),texture.getHeight());
	}

	boolean fire(float x, float y) {
		if (inPlay) return false;
		super.setPosition(x + 14f, y+ 14f);
		inPlay = true;
		return true;
	}

	@Override
	public void act(float delta) {  // note: delta is time in seconds, not milliseconds
		if(!inPlay) return;
		super.moveBy(0.0f, SPEED * delta);
		inPlay = getY() < BULLET_SCREEN_HEIGHT_LIMIT;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(!inPlay) return;
		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY());
	}

	boolean isInPlay() {
		return inPlay;
	}

	void setInPlay(boolean value) {
		inPlay = value;
	}
}
