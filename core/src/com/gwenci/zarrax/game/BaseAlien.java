package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.BaseActor;

abstract class BaseAlien extends BaseActor {

	private AlienState state;
	private final Texture alienTexture;
	private final int frameWidth;
	private final int halfFrameWidth;
	private final int halfFrameHeight;

	long lastTimeFired = TimeUtils.millis();

	private final Animator animator;

	BaseAlien(Texture texture, int nFrames, float animFrameRate) {

		animator = new Animator(nFrames, animFrameRate);

		alienTexture = texture;
		frameWidth = texture.getWidth() / nFrames;

		halfFrameWidth = this.alienTexture.getWidth() / 2;
		halfFrameHeight = this.alienTexture.getHeight() / 2;

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
		 return getX() + halfFrameWidth;
	 }


	float getCentreY() {
		 return getY() + halfFrameHeight;
	 }


	boolean isAlive() {
		return state == AlienState.ALIVE;
	 }


	public void act(float dt) {
		super.act(dt);
		animator.update(dt);
	}


	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE ) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, getX(), getY(), animator.getCurrentFrame() * frameWidth, 0, frameWidth,
				this.alienTexture.getHeight() );
	}


	public abstract boolean canFire();

	public abstract boolean isFiring(float chanceToFire);

 }