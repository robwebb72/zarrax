package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;

class PlayerBullets extends BulletManager<BulletBaseActor> {

	private static final int MAX_BULLETS = 250;
	private static final int BULLETS_PER_SECOND = 8;
	private static final int MS_BETWEEN_BULLETS = 1000/BULLETS_PER_SECOND;
	private static final float BULLET_SPEED = 400f;
	private static final int PLAYER_SHIP_HEIGHT = 28;
	public static final int PLAYER_SHIP_HALF_WIDTH = 14;

	//TODO: Potential screen size issues (HALF_SCREEN_WIDTH used for panning sound)
	private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth() / 2f;

	private static Sound effect;
	private static Texture texture;

	static {
		texture = TextureManager.getInstance().get("assets/player_bullet.png");
		effect = AudioManager.getInstance().get("assets/shoot.wav");

	}

	private long lastMs = System.currentTimeMillis();


	PlayerBullets(Viewport vp, SpriteBatch batch) {
		super(MAX_BULLETS);
		for(int i= 0; i<MAX_BULLETS; i++) {
			bullets[i] = new BulletBaseActor(texture);
		}
		super.SetStage(vp,batch);
	}


	void fireBullet(float x, float y) {
		if (lastMs + MS_BETWEEN_BULLETS < System.currentTimeMillis()) {
			BulletBaseActor bullet = getNextBullet();
			if(!bullet.isInPlay()) {
				bullet.fire(x+PLAYER_SHIP_HALF_WIDTH, y+PLAYER_SHIP_HEIGHT, 0, BULLET_SPEED);
				float pan = (x - HALF_SCREEN_WIDTH) / HALF_SCREEN_WIDTH;
				effect.play(1.0f, ((float) Math.random() * 0.6f) + 0.7f, pan);
				lastMs = System.currentTimeMillis();
			}
		}
	}

}
