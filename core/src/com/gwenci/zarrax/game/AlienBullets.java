package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.TextureManager;

public class AlienBullets extends BulletManager {

	private static final int MAX_BULLETS = 250;

	AlienBullets(Viewport vp, SpriteBatch batch) {
		super(MAX_BULLETS);
		for(int i= 0; i<MAX_BULLETS; i++) {
			bullets[i] = new BulletBaseActor();
		}
		super.SetStage(vp,batch);
	}



}
