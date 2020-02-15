package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseActor extends Actor {

	static final int SCREEN_WIDTH = 672; // Gdx.graphics.getWidth();
	static final int SCREEN_HEIGHT = 768; //Gdx.graphics.getHeight();
	private Rectangle boundingRect = new Rectangle();

	@Override
	public void act(float delta) {  // note: delta is time in seconds, not milliseconds
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		boundingRect.setPosition(x,y);
	}

	@Override
	public void moveBy(float dx, float dy) {
		super.moveBy(dx,dy);
		boundingRect.setPosition(this.getX(), this.getY());
	}

	void setBoundingRect(int width, int height) {
		boundingRect.width = width;
		boundingRect.height = height;
	}

	boolean collidesWith(BaseActor other) {
		return this.boundingRect.overlaps(other.boundingRect);
	}
}
