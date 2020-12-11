package com.gwenci.zarrax.game;

import com.badlogic.gdx.math.Vector2;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.game.bullets.BulletType;

import java.util.Arrays;
import java.util.stream.Stream;

public class BulletManager implements Updatable {

	private final int maxBullets;
	private int nextBullet = 0;

	BulletBaseActor[] bullets;


	BulletManager(int maxBullets) {
		this.maxBullets = maxBullets;
		this.bullets = new BulletBaseActor[maxBullets];
		for(int i= 0; i<this.maxBullets; i++) {
			bullets[i] = new BulletBaseActor();
		}

	}


	public BulletBaseActor getNextBullet() {
		nextBullet++;
		if (nextBullet >= maxBullets) nextBullet= 0;
		return bullets[nextBullet];
	}


	public void update(float dt) {
		getActiveBullets().forEach(b->b.act(dt));
	}



	public Stream<BulletBaseActor> getActiveBullets() {
		return Arrays.stream(bullets).filter(BulletBaseActor::isInPlay);
	}


	public void reset() {
		getActiveBullets().forEach(BulletBaseActor::removeFromPlay);
	}

	public void fireBullet(BulletType bulletType, Vector2 location, Vector2 direction) {
		BulletBaseActor bullet = getNextBullet();
		bullet.fire(bulletType, location, direction);
	}
}
