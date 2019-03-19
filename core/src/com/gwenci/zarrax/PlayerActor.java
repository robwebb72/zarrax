package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerActor extends Actor {

	private static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	private static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
	private static final int MAX_PLAYER_HEIGHT = SCREEN_HEIGHT / 3;
	private static final float SPEED = 300f;  //pixels per second

	enum Direction {
		LEFT,
		RIGHT,
		STRAIGHT
	}

	private float x;
	private float y;
	private Texture playerTexture;
	private Direction direction;

	PlayerActor() {
		playerTexture = new Texture(Gdx.files.internal("assets/player.png"));
		x = SCREEN_WIDTH / 2.0f - 16f;
		y = 5f;
	}

	@Override
	public void act(float dt) {
		super.act(dt);
		direction = Direction.STRAIGHT;
		if(Gdx.input.isKeyPressed(Keys.LEFT) ||Gdx.input.isKeyPressed(Keys.A)) {
			x-= SPEED * dt;
			direction = Direction.LEFT;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)|| Gdx.input.isKeyPressed(Keys.D)) {
			x+= SPEED * dt;
			direction = Direction.RIGHT;
		}
		if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) y+= SPEED * dt;
		if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) y -= SPEED * dt;
		checkBounds();
	}

	private void checkBounds() {
		x = Math.max(x, 10f);
		x = Math.min(x, SCREEN_WIDTH-42f);
		y = Math.max(y, 5f);
		y = Math.min(y, MAX_PLAYER_HEIGHT);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		switch (direction) {
			case LEFT:
				batch.draw(playerTexture, x, y, 30, 0, 30,30);
				break;
			case RIGHT:
				batch.draw(playerTexture, x, y, 60, 0, 30,30);
				break;
			case STRAIGHT:
				batch.draw(playerTexture, x, y, 0, 0, 30,30);
				break;
		}
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
