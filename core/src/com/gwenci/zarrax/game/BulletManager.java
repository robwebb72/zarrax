package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.Updatable;

import java.util.Arrays;
import java.util.stream.Stream;

public class BulletManager<T extends BulletBaseActor> implements Updatable {

	private final int maxBullets;
	private Stage bulletStage;
	private int nextBullet = 0;

	T[] bullets;


	BulletManager(int maxBullets) {
		this.maxBullets = maxBullets;
		this.bullets = (T[]) new BulletBaseActor[maxBullets];
	}


	void SetStage(Viewport vp, SpriteBatch batch) {
		bulletStage = new Stage(vp, batch);
		for(int i= 0; i<maxBullets; i++) {
			bulletStage.addActor(bullets[i]);
		}
	}


	T getNextBullet() {
		nextBullet++;
		if (nextBullet >= maxBullets) nextBullet= 0;
		return bullets[nextBullet];
	}


	void act(float dt) {
		bulletStage.act(dt);
	}


	@Override
	public void update(float dt) {
		act(dt);
	}

	void draw() {
		bulletStage.draw();
	}


	Stream<T> getActiveBullets() {
		return Arrays.stream(bullets).filter(BulletBaseActor::isInPlay);
	}


	public void fireBullet(float x, float y, float dx, float dy) {
		getNextBullet().fire(x, y, dx, dy);
	}


	void dispose() {
		bulletStage.dispose();
	}

}
