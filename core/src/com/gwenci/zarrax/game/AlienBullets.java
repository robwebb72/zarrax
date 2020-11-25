package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.TextureManager;

public class AlienBullets extends BulletManager<BulletBaseActor> {

	private static final int MAX_BULLETS = 250;
	private static Texture texture;

	static {
		texture = TextureManager.getInstance().get("assets/alien_bullet.png");

	}


	AlienBullets(Viewport vp, SpriteBatch batch) {
		super(MAX_BULLETS);
		for(int i= 0; i<MAX_BULLETS; i++) {
			bullets[i] = new BulletBaseActor(texture, 1, 0.0f);
		}
		super.SetStage(vp,batch);
	}



	void fireBullet(float x, float y, float dx, float dy) {
		BulletBaseActor bullet = super.getNextBullet();
		if(!bullet.isInPlay()) {
			bullet.fire(x, y, dx, dy);
		}
	}

}
