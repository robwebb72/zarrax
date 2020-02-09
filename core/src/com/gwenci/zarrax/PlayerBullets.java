package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

class PlayerBullets extends BulletManager {

	private static final int MAX_BULLETS = 250;

	private static final int BULLETS_PER_SECOND = 8;
	private static final int MS_BETWEEN_BULLETS = 1000/BULLETS_PER_SECOND;
	private long lastMs = System.currentTimeMillis();

	private static final float HALF_SCREEN_WIDTH = 672 / 2; //Gdx.graphics.getWidth() / 2;
	private static Sound effect = Gdx.audio.newSound(Gdx.files.internal("assets/shoot.wav"));

	private static Texture texture = new Texture(Gdx.files.internal("assets/player_bullet.png"));


	PlayerBullets(Viewport vp, SpriteBatch batch) {
		super(MAX_BULLETS);
		for(int i= 0; i<MAX_BULLETS; i++) {
			bullets[i] = new PlayerBulletActor(texture);
		}
		SetStage(vp,batch);
	}

	void fireBullet(float x, float y) {
		if (lastMs + MS_BETWEEN_BULLETS < System.currentTimeMillis()) {
			PlayerBulletActor bullet = getNextBullet();
			if(!bullet.isInPlay()) {
				bullet.fire(x, y);
				float pan = (x - HALF_SCREEN_WIDTH) / HALF_SCREEN_WIDTH;
				effect.play(1.0f, ((float) Math.random() * 0.6f) + 0.7f, pan);
				lastMs = System.currentTimeMillis();
			}
		}
	}

	void dispose() {
		super.dispose();
		if (effect != null) {
			effect.stop();
			effect.dispose();
		}
		texture.dispose();
	}
}
