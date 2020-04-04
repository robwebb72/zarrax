package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.stream.Stream;

public class BulletManager<T extends BulletBaseActor> {

	private int maxBullets;
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


	void draw() {
		bulletStage.draw();
	}


	Stream<T> getActiveBullets() {
		return Arrays.stream(bullets).filter(BulletBaseActor::isInPlay);
	}


	void dispose() {
		bulletStage.dispose();
	}

}
