package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.bullets.BulletType;

public class PlayerBullets extends BulletManager {

	private static final int MAX_BULLETS = 50;
	private static final int BULLETS_PER_SECOND = 8;
	private static final int MS_BETWEEN_BULLETS = 1000/BULLETS_PER_SECOND;
	private static final float BULLET_SPEED = 400f;

	private long lastMs = System.currentTimeMillis();

	BulletType playerBullet = new BulletType(
			TextureManager.getInstance().get("assets/player_bullet.png"),
			new Animator(2, 0.05f),
			1.0f,
			AudioManager.getInstance().get("assets/sfx/player_fire.wav")
	);

	PlayerBullets(Viewport vp, SpriteBatch batch) {
		super(MAX_BULLETS);
//		super.setStage(vp,batch);
	}

	public void fireBullet(Vector2 location) {
		if (lastMs + MS_BETWEEN_BULLETS < System.currentTimeMillis()) {
			BulletBaseActor bullet = getNextBullet();
			bullet.initialise(playerBullet);
			if(!bullet.isInPlay()) {
				bullet.fire(playerBullet, location, new Vector2(0.0f,BULLET_SPEED));
				lastMs = System.currentTimeMillis();
			}
		}
	}

}
