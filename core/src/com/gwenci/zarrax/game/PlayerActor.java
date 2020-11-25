package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gwenci.zarrax.BaseActor;
import com.gwenci.zarrax.TextureManager;

public class PlayerActor extends BaseActor {

	private static final int SCREEN_WIDTH = 672;
	private static final int SCREEN_HEIGHT = 768;
	private static final int MAX_PLAYER_HEIGHT = SCREEN_HEIGHT / 3;
	private static final float SPEED = 200f;  //pixels per second
	private Texture playerTexture;
	private Direction direction;

	PlayerActor() {
		playerTexture = TextureManager.getInstance().get("assets/player.png");
		setPosition(SCREEN_WIDTH / 2.0f - 16f, 5f);
	}

	@Override
	public void act(float dt) {
		super.act(dt);
		float dx = 0.0f;
		float dy = 0.0f;

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			dx -= SPEED * dt;
			direction = Direction.LEFT;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			dx += SPEED * dt;
			direction = Direction.RIGHT;
		}
		if (dx == 0.0f) direction = Direction.STRAIGHT;
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) dy += SPEED * dt;
		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) dy -= SPEED * dt;
		super.moveBy(dx, dy);
		checkBounds();
	}

	private void checkBounds() {
		float x = super.getX();
		float y = super.getY();
		x = Math.max(x, 10f);
		x = Math.min(x, SCREEN_WIDTH - 42f);
		y = Math.max(y, 5f);
		y = Math.min(y, MAX_PLAYER_HEIGHT);
		super.setPosition(x, y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		int srcX;

		if (direction == Direction.LEFT)
			srcX = 30;
		else if (direction == Direction.RIGHT)
			srcX = 60;
		else
			srcX = 0;

		batch.draw(playerTexture, super.getX(), super.getY(), srcX, 0, 30, 30);
	}

	enum Direction {
		LEFT,
		RIGHT,
		STRAIGHT
	}

}
