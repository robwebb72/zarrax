package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gwenci.zarrax.particle_system.ILocation;

public abstract class BaseActor extends Actor implements ILocation {

	public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
	public Rectangle boundingRect = new Rectangle();

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

	public Vector2 getPositionVector() {
		return new Vector2(getX(),getY());
	}

	@Override
	public void moveBy(float dx, float dy) {
		super.moveBy(dx,dy);
		boundingRect.setPosition(this.getX(), this.getY());
	}

	public void setBoundingRect(int width, int height) {
		boundingRect.width = width;
		boundingRect.height = height;
	}

	public boolean collidesWith(BaseActor other) {
		return this.boundingRect.overlaps(other.boundingRect);
	}

	public boolean collidesWith(Rectangle other) {
		return this.boundingRect.overlaps(other);
	}

	public Vector2 location() {
		return new Vector2(getX()+ getWidth()/2, getY()+ getHeight()/2);
	}
}
