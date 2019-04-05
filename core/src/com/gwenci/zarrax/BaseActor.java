package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseActor extends Actor {

	Polygon boundingPolygon;
	static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

	void setBoundingPolygon(float[] vertices) {
		boundingPolygon = new Polygon(vertices);
	}

	@Override
	public void act(float delta) {  // note: delta is time in seconds, not milliseconds
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
