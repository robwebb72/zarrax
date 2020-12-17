package com.gwenci.zarrax.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.BaseActor;
import com.gwenci.zarrax.ILocation;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.BulletBaseActor;
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
	private static final float SHIELD_RADIUS = 30.0f;

	private final Texture playerTexture;
	private Direction direction;
	private final PlayerBullets bullets;
	private EngineLocation leftEngineLoc, rightEngineLoc;
	private ParticleEmitter shieldEmitter, leftEngine, rightEngine;
	private boolean shield;
	private float shieldTimer = 0.0f;
	private long lastBulletFiredMS = 0;

	private final Stage stage;
	private boolean isAlive = false;

	private final Rectangle generalBoundingBox = new Rectangle();
	private final Rectangle smallUpperBoundingBox = new Rectangle();
	private final Rectangle smallLowerBoundingBox = new Rectangle();
	private final Rectangle shieldBoundingBox = new Rectangle();
	private final Circle shieldCircle = new Circle();

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
		playerTexture = TextureManager.getInstance().get("player.png");
		int frameWidth = playerTexture.getWidth() / N_ANIM_FRAMES;
		super.setWidth(frameWidth);
		super.setHeight(playerTexture.getHeight());
		setBoundingRect(frameWidth, playerTexture.getHeight());

		this.bullets = bullets;
		this.stage = new Stage(vp,batch);
		stage.addActor(this);
		generalBoundingBox.setSize(30,30);
		shieldBoundingBox.setSize(SHIELD_RADIUS * 2.0f, SHIELD_RADIUS * 2.0f);
		shieldCircle.setRadius(SHIELD_RADIUS);
		initialiseParticleEffects();
	}

	private void initialiseParticleEffects() {
		leftEngineLoc = new EngineLocation(new Vector2(10.0f,2.0f));
		rightEngineLoc = new EngineLocation(new Vector2(15.0f,2.0f));

		leftEngine = ParticleFoundry.getInstance().newEmitter(leftEngineLoc,new ParticleEffectJetPlume());
		rightEngine = ParticleFoundry.getInstance().newEmitter(rightEngineLoc,new ParticleEffectJetPlume());

		shieldEmitter = ParticleFoundry.getInstance().newEmitter(this,new ParticleEffectPlayerShield(SHIELD_RADIUS));
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
		super.moveBy(dx, dy, dt);

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) fireBullet();

		shieldCircle.setPosition(location());
		shieldBoundingBox.setPosition(location().x - SHIELD_RADIUS, location().y-SHIELD_RADIUS);
		checkBounds();
		updateEngineOffsets(direction);
		updateBoundingBoxes(direction);
		shieldEmitter.setOn(shield);
	}

	private void updateBoundingBoxes(Direction direction) {
		generalBoundingBox.setPosition(this.getX(), this.getY());
		switch(direction) {
			case LEFT:
				smallUpperBoundingBox.setPosition(this.getX()+10, this.getY()+14);
				smallUpperBoundingBox.setSize(8.0f,16.0f);
				smallLowerBoundingBox.setPosition(this.getX()+2, this.getY()+5);
				smallLowerBoundingBox.setSize(26.0f,9.0f);
				break;
			case RIGHT:
				smallUpperBoundingBox.setPosition(this.getX()+12, this.getY()+14);
				smallUpperBoundingBox.setSize(8.0f,16.0f);
				smallLowerBoundingBox.setPosition(this.getX()+2, this.getY()+5);
				smallLowerBoundingBox.setSize(26.0f,9.0f);
				break;
			case STRAIGHT:
				smallUpperBoundingBox.setPosition(this.getX()+10, this.getY()+14);
				smallUpperBoundingBox.setSize(9.0f,16.0f);
				smallLowerBoundingBox.setPosition(this.getX(), this.getY()+5);
				smallLowerBoundingBox.setSize(30.0f,9.0f);
				break;
		}
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

	public boolean checkCollision(Rectangle otherBoundingBox) {
		if(!generalBoundingBox.overlaps((otherBoundingBox))) return false;
		return smallUpperBoundingBox.overlaps(otherBoundingBox) || smallLowerBoundingBox.overlaps(otherBoundingBox);
	}

	public boolean collidesWithShield(BulletBaseActor bullet) {
		if (shieldTimer<=0.0f) return false;
		if (!shieldBoundingBox.overlaps(bullet.boundingRect)) return false;
		return Intersector.overlaps(shieldCircle,bullet.boundingRect);
		// TODO: need to roughly work out the point of impact to create particle effect at this point
		//       will need point of impact (roughly) and a vector from player's centre to point of impact (for direction
		//       of particles)
	}

	public boolean collidesWith(BulletBaseActor bullet) {
		return checkCollision(bullet.boundingRect);
	}


	enum Direction {
		LEFT,
		RIGHT,
		STRAIGHT
	}

}
