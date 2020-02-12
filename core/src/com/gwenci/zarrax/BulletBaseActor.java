package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class BulletBaseActor extends BaseActor {

	private static final int SCREEN_HEIGHT = 768;

	private float dx;
	private float dy;
	private boolean inPlay;
	Texture texture;

	BulletBaseActor(Texture texture) {
		setTexture(texture);
	}
	boolean fire(float x, float y, float dx, float dy) {
		if (this.inPlay) return false;
		super.setPosition(x, y);
		this.dx = dx;
		this.dy = dy;
		this.inPlay = true;
		return true;
	}

	void setTexture(Texture texture) {
		this.texture = texture;
		super.setBoundingRect(texture.getWidth(), texture.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!this.inPlay) return;

		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY());
	}

	@Override
	public void act(float dt) {  // note: delta is time in seconds, not milliseconds
		if (this.inPlay) {
			super.moveBy(dx * dt, dy * dt);
			this.inPlay = getY() > 0 && getY() < SCREEN_HEIGHT;
		}
	}

	boolean isInPlay() {
		return inPlay;
	}

	void setInPlay(boolean value) {
		inPlay = value;
	}

	void removeFromPlay() {inPlay = false; }
}




