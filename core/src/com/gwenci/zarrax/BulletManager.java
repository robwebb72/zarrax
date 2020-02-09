package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BulletManager {

	private final int maxBullets;
	private Stage bulletStage;

	PlayerBulletActor[] bullets;
	private int nextBullet = 0;

	BulletManager(int maxBullets) {
		this.maxBullets = maxBullets;
		this.bullets = new PlayerBulletActor[maxBullets];
	}

	void SetStage(Viewport vp, SpriteBatch batch) {
		bulletStage = new Stage(vp, batch);
		for(int i= 0; i<maxBullets; i++) {
			bulletStage.addActor(bullets[i]);
		}
	}

	PlayerBulletActor getNextBullet() {
		if (nextBullet >= maxBullets) nextBullet = 0;
		return bullets[nextBullet++];
	}

	void act(float dt) {
		bulletStage.act(dt);
	}

	void draw() {
		bulletStage.draw();
	}

	List<PlayerBulletActor> getActiveBullets() {
		return Arrays.stream(bullets).filter(PlayerBulletActor::isInPlay).collect(Collectors.toList());
	}

	void dispose() {
		bulletStage.dispose();
	}

}
