package com.gwenci.zarrax.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.BaseActor;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.PlayerBullets;
import com.gwenci.zarrax.particle_system.*;

public class PlayerActor extends BaseActor {

	private static final int N_ANIM_FRAMES = 3;
	private static final int SCREEN_WIDTH = 672;
	private static final int SCREEN_HEIGHT = 768;
	private static final int MAX_PLAYER_HEIGHT = SCREEN_HEIGHT / 3;
	private static final float SPEED = 200f;  //pixels per second
	private static final int BULLETS_PER_SECOND = 8;
	private static final int MS_BETWEEN_BULLETS = 1000/BULLETS_PER_SECOND;

	private final Texture playerTexture;
	private Direction direction;
	private final PlayerBullets bullets;
	private final EngineLocation leftEngineLoc, rightEngineLoc;
	private final ParticleEmitter shieldEmitter, leftEngine, rightEngine;
	private boolean shield;
	private float shieldTimer = 0.0f;
	private long lastBulletFiredMS = 0;

	private final Stage stage;
	private boolean isAlive = false;

	class EngineLocation implements ILocation {
		Vector2 offset;

		EngineLocation(Vector2 offset) {
			setOffset(offset);
		}

		void setOffset(Vector2 offset) {
			this.offset = offset;
		}

		void setOffsetX(float x) {
			offset.x = x;
		}
		@Override
		public Vector2 location() {
			return PlayerActor.this.getPositionVector().add(offset);
		}
	}

	public PlayerActor(PlayerBullets bullets, Viewport vp, SpriteBatch batch) {
		playerTexture = TextureManager.getInstance().get("assets/player.png");
		int frameWidth = playerTexture.getWidth() / N_ANIM_FRAMES;
		super.setWidth(frameWidth);
		super.setHeight(playerTexture.getHeight());
		setBoundingRect(frameWidth, playerTexture.getHeight());

		this.bullets = bullets;
		this.stage = new Stage(vp,batch);
		stage.addActor(this);
		leftEngineLoc = new EngineLocation(new Vector2(10.0f,2.0f));
		leftEngine = ParticleFoundry.getInstance().newEmitter(leftEngineLoc,new ParticleEffectJetPlume());

		rightEngineLoc = new EngineLocation(new Vector2(15.0f,2.0f));
		rightEngine = ParticleFoundry.getInstance().newEmitter(rightEngineLoc,new ParticleEffectJetPlume());

		shieldEmitter = ParticleFoundry.getInstance().newEmitter(this,new ParticleEffectPlayerShield());
		setParticleEffects(false);
	}

	@Override
	public void act(float dt) {
		if (!isAlive) return;
		super.act(dt);
		float dx = 0.0f;
		float dy = 0.0f;

		if(shield) {
			shieldTimer -= dt;
			shield = shieldTimer > 0.0f;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			dx -= SPEED * dt;
			direction = Direction.LEFT;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			dx += SPEED * dt;
			direction = Direction.RIGHT;
		}
		if (dx == 0.0f) direction = Direction.STRAIGHT;
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			dy += SPEED * dt;

		}
		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) dy -= SPEED * dt;
		super.moveBy(dx, dy);

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) fireBullet();
		updateEngineOffsets(direction);
		shieldEmitter.setOn(shield);
		checkBounds();
	}


	private void fireBullet() {
		if(TimeUtils.timeSinceMillis(lastBulletFiredMS)>MS_BETWEEN_BULLETS) {
			boolean bulletFired = bullets.fireBullet(fireLocation());
			if (bulletFired) lastBulletFiredMS = TimeUtils.millis();
		}
	}

	private Vector2 fireLocation() {
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight());
	}


	private void updateEngineOffsets(Direction direction) {
		switch (direction) {
			case LEFT:
				leftEngineLoc.setOffsetX(11.0f);
				rightEngineLoc.setOffsetX(14.0f);
				break;
			case RIGHT:
				leftEngineLoc.setOffsetX(12.0f);
				rightEngineLoc.setOffsetX(15.0f);
				break;
			default:
				leftEngineLoc.setOffsetX(10.0f);
				rightEngineLoc.setOffsetX(16.0f);
		}
	}

	public void setShieldsOn(float seconds) {
		this.shieldTimer = seconds;
		shield = true;
		shieldEmitter.setOn(true);
	}


	private void checkBounds() {
		float x = super.getX();
		float y = super.getY();
		x = Math.max(x, 10f);
		x = Math.min(x, SCREEN_WIDTH - 42f);
		y = Math.max(y, 25f);
		y = Math.min(y, MAX_PLAYER_HEIGHT);
		super.setPosition(x, y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!isAlive) return;
		super.draw(batch, parentAlpha);
		int srcX;

		if (direction == Direction.LEFT)
			srcX = 30;
		else if (direction == Direction.RIGHT)
			srcX = 60;
		else
			srcX = 0;

		batch.draw(playerTexture, getX(), getY(), srcX, 0, 30, 30);
	}

	public void draw() {
		stage.draw();
	}
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
		setParticleEffects(isAlive);

	}

	public boolean isAlive() {
		return isAlive;
	}

	private void setParticleEffects(boolean on) {
		rightEngine.setOn(on);
		leftEngine.setOn(on);
		shieldEmitter.setOn(on);
	}

	enum Direction {
		LEFT,
		RIGHT,
		STRAIGHT
	}

}
