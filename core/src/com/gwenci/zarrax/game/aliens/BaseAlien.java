package com.gwenci.zarrax.game.aliens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.BaseActor;
import com.gwenci.zarrax.game.bullets.BulletType;
import com.gwenci.zarrax.particle_system.EmitterType;

abstract class BaseAlien extends BaseActor {

	private AlienState state;
	private final Texture alienTexture;

	long lastTimeFired = TimeUtils.millis();

	private final Animator animator;
	BulletType bulletType;

	BaseAlien(Texture texture, int nFrames, float animFrameRate) {

		animator = new Animator(nFrames, animFrameRate);

		alienTexture = texture;
		int frameWidth = texture.getWidth() / nFrames;
		super.setWidth(frameWidth);
		super.setHeight(texture.getHeight());

		animator.setCurrentFrame((int) (nFrames * Math.random()));
		setPosition(SCREEN_WIDTH / 2.0f - 16f, 300f);
		setBoundingRect(frameWidth, texture.getHeight());
		setState(AlienState.ALIVE);
	}

	int getScore() {
		return 0;
	}

	void setState(AlienState state) {
		this.state = state;
	}


	float getCentreX() {
		 return getX() + getWidth()/2.0f;
	 }


	float getCentreY() {
		 return getY() + getHeight()/2.0f;
	 }

	boolean isAlive() {
		return state == AlienState.ALIVE;
	 }

	public void moveTo(float x, float y, float dt) {
		super.moveBy(x-getX(), y-getY(), dt);
	}


	public void act(float dt) {
		if (state != AlienState.ALIVE ) return;
		animator.update(dt);
		super.act(dt);
		super.update(dt);
	}


	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE ) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, getX(), getY(), animator.getCurrentFrame() *  (int) getWidth(), 0, (int) getWidth(),
				(int) getHeight() );
	}


	public abstract boolean canFire();

	public abstract boolean isFiring(float chanceToFire);


	public BulletType getBulletType() {
		return bulletType;
	};
	public abstract EmitterType particleExplosion();

 }