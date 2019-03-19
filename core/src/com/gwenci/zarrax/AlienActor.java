package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AlienActor extends Actor {
	private Texture alienTexture;

	private static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	private static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
	private float x;
	private float y;



	private AlienState state = AlienState.DEAD;

	AlienActor(Texture texture) {
		alienTexture = texture;
		x = SCREEN_WIDTH / 2.0f - 16f;
		y = 300f;
	}

	public void setState(AlienState state) {
		this.state = state;
	}

	public void setTexture(Texture texture) {
		this.alienTexture = texture;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void act(float dt) {
		if (state != AlienState.ALIVE) return;
		super.act(dt);
	}

	private void checkBounds() {
		x = Math.max(x, 10f);
		x = Math.min(x, SCREEN_WIDTH-42f);
		y = Math.max(y, 5f);
		y = Math.min(y, SCREEN_HEIGHT-62f);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, (int) x, (int) y);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
}
