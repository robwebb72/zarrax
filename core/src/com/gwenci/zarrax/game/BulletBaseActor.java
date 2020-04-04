package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gwenci.zarrax.BaseActor;


public class BulletBaseActor extends BaseActor {

	// TODO: SCREEN_HEIGHT hard coded here
	private static final int SCREEN_HEIGHT = 768;

	private float dx;
	private float dy;
	private boolean inPlay;
	Texture texture;


	BulletBaseActor(Texture texture) {
		setTexture(texture);
	}


	boolean fire(float x, float y, float dx, float dy) {
		if (!this.inPlay) {
			super.setPosition(x, y);
			this.dx = dx;
			this.dy = dy;
			this.inPlay = true;
			return true;
		} else {
			return false;
		}
	}

	void setTexture(Texture texture) {
		this.texture = texture;
		super.setBoundingRect(texture.getWidth(), texture.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (this.inPlay) {
			super.draw(batch, parentAlpha);
			batch.draw(texture, getX(), getY());
		}
	}

	@Override
	public void act(float dtInSeconds) {
		if (this.inPlay) {
			super.moveBy(dx * dtInSeconds, dy * dtInSeconds);
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




