package com.gwenci.zarrax.game;

import com.badlogic.gdx.math.Vector2;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.bullets.BulletType;

public class PlayerBullets extends BulletManager {

	private static final int MAX_BULLETS = 50;
	private static final float BULLET_SPEED = 400f;

	private long lastMs = System.currentTimeMillis();

	BulletType playerBullet = new BulletType(
			TextureManager.getInstance().get("assets/player_bullet.png"),
			new Animator(2, 0.05f),
			1.0f,
			AudioManager.getInstance().get("assets/sfx/player_fire.wav")
	);

	public PlayerBullets() {
		super(MAX_BULLETS);
	}

	// TODO - the timing functionality should be in the playerActor class
	public boolean fireBullet(Vector2 location) {
		BulletBaseActor bullet = getNextBullet();
		bullet.initialise(playerBullet);
		if(!bullet.isInPlay()) {
			bullet.fire(playerBullet, location, new Vector2(0.0f,BULLET_SPEED));
			lastMs = System.currentTimeMillis();
			return true;
		}
		return false;
	}

}
