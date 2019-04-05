package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AlienActor extends BaseActor {
	private Texture alienTexture;

	private AlienState state = AlienState.DEAD;

	AlienActor(Texture texture) {
		this.alienTexture = texture;
		setPosition( SCREEN_WIDTH / 2.0f - 16f, 300f);

		float[] vertices = {0.0f, 0.0f,
				texture.getWidth(), 0.0f,
				texture.getWidth(), texture.getHeight(),
				0.0f, texture.getWidth()
		};
		super.setBoundingPolygon(vertices);
	}

	public void setState(AlienState state) {
		this.state = state;
	}

	@Override
	public void act(float dt) {
		if (state != AlienState.ALIVE) return;
		super.act(dt);
	}

	private void checkBounds() {
//		setx = Math.max(x, 10f);
//		x = Math.min(x, SCREEN_WIDTH-42f);
//		y = Math.max(y, 5f);
//		y = Math.min(y, SCREEN_HEIGHT-62f);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, getX(), getY());
	}

	public boolean collideWith(BaseActor other) {
		return Intersector.overlapConvexPolygons(this.boundingPolygon, other.boundingPolygon);
	}
}
