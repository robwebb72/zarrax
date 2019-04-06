package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayerBullets {

	private static final int MAX_BULLETS = 50;
	private static final int BULLETS_PER_SEC = 10;
	private static final int MS_BETWEEN_BULLETS = 1000 / BULLETS_PER_SEC;
	private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth()/2;
	private static Texture texture;
	private static Sound effect;

	static {
		texture = new Texture(Gdx.files.internal("assets/player_bullet.png"));
		effect = Gdx.audio.newSound(Gdx.files.internal("assets/shoot.wav"));
	}

	private PlayerBulletActor[] bullets = new PlayerBulletActor[MAX_BULLETS];
	private int nextBullet = 0;
	private long lastMs = System.currentTimeMillis();
	private Stage bulletStage;

	PlayerBullets(Viewport vp, SpriteBatch batch) {
		bulletStage = new Stage(vp, batch);
		for (int i = 0; i<MAX_BULLETS; i++) {
			bullets[i] = new PlayerBulletActor(texture);
			bulletStage.addActor(bullets[i]);
		}
	}

	void fireBullets(float x, float y) {
		if(lastMs + MS_BETWEEN_BULLETS < System.currentTimeMillis()) {
			lastMs = System.currentTimeMillis();
			boolean shotFired = addBullets(x , y);
			float pan = (x- HALF_SCREEN_WIDTH)/HALF_SCREEN_WIDTH;
			if (shotFired) effect.play(1.0f,1.0f,pan);
		}
	}

	private boolean addBullets(float x, float y) {
		if(nextBullet>=MAX_BULLETS) nextBullet = 0;
		return bullets[nextBullet++].fire(x, y);
	}

	void act(float dt) {
		bulletStage.act(dt);
	}

	void draw() {
		bulletStage.draw();
	}

	void dispose() {
		if(effect != null) {
			effect.stop();
			effect.dispose();
		}
		bulletStage.dispose();
		texture.dispose();
	}

	List<PlayerBulletActor> getList() {
		return Arrays.stream(bullets).filter(PlayerBulletActor::isInPlay).collect(Collectors.toList());
	}
}
