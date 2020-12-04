package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.game.bullets.BulletType;

import java.util.Arrays;
import java.util.stream.Stream;

public class BulletManager implements Updatable {

	private final int maxBullets;
	private Stage bulletStage;
	private int nextBullet = 0;

	BulletBaseActor[] bullets;


	BulletManager(int maxBullets) {
		this.maxBullets = maxBullets;
		this.bullets = new BulletBaseActor[maxBullets];
	}


	void SetStage(Viewport vp, SpriteBatch batch) {
		bulletStage = new Stage(vp, batch);
		for(int i= 0; i<maxBullets; i++) {
			bulletStage.addActor(bullets[i]);
		}
	}


	BulletBaseActor getNextBullet() {
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


	Stream<BulletBaseActor> getActiveBullets() {
		return Arrays.stream(bullets).filter(BulletBaseActor::isInPlay);
	}


	public void fireBullet(BulletType bulletType, Vector2 location, Vector2 delta_vec) {
		BulletBaseActor bullet=getNextBullet();
		bullet.initialise(bulletType);
		delta_vec.scl(bulletType.getSpeed());
		bullet.fire(location, delta_vec);
	}

	void dispose() {
		bulletStage.dispose();
	}

}
