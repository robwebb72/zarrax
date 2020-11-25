package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.gwenci.zarrax.BaseActor;

abstract class BaseAlien extends BaseActor {
	AlienState state;
	Texture alienTexture;
	private int frameWidth;
	private int nframes;
	int halfFrameWidth;
	int halfFrameHeight;
	float animFrameRate;
	float animFrameRateTimer = 0;
	int animFrame = 0;
	long lastTimeFired = TimeUtils.millis();


	BaseAlien(Texture texture, int nframes, float animFrameRate) {

		alienTexture = texture;
		frameWidth = texture.getWidth() / nframes;
		this.nframes = nframes;

		halfFrameWidth = this.alienTexture.getWidth() / 2;
		halfFrameHeight = this.alienTexture.getHeight() / 2;

		animFrame = (int) (nframes * Math.random());
		setPosition(SCREEN_WIDTH / 2.0f - 16f, 300f);
		setBoundingRect(frameWidth, texture.getHeight());
		setState(AlienState.ALIVE);
		this.animFrameRate = animFrameRate;
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
		animFrameRateTimer += dt;
		 if (animFrameRateTimer > animFrameRate) {
			 animFrameRateTimer = 0;
			 animFrame--;
			 if (animFrame < 0) animFrame = nframes-1;
		 }
	}


	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE ) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, getX(), getY(),
				animFrame * frameWidth, 0,
				frameWidth, this.alienTexture.getHeight() );
	 }


	 public abstract boolean canFire();

	 public abstract boolean isFiring(float chanceToFire);

 }