package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class AlienWrangler {

	private static final int MAX_ALIENS = 50;
	private Stage stage;
	private ParticleFoundry particleFoundry = ParticleFoundry.getInstance();
	private BaseAlien[] aliens = new BaseAlien[MAX_ALIENS];
	private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth()/2.0f;
	private float swarmXTimer = 0.0f;
	private static Sound explosionSound;

	static {
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/alienexpl.wav"));
	}


	AlienWrangler(Viewport vp, SpriteBatch batch) {
		stage = new Stage(vp,batch);
		for(int i = 0 ; i< MAX_ALIENS; i++) {
			if(i>=30) {
				aliens[i] = new AlienActor2(TextureManager.getInstance().get("assets/galaxian_2_1.png"));
			} else {
				aliens[i] = new AlienActor1(TextureManager.getInstance().get("assets/galaxian_1_1.png"));
			}
			stage.addActor(aliens[i]);
		}
		placeAliens();

		// just the one alien, please..
//		aliens[0] = new AlienActor1(alien1texture);
//		aliens[0].setPosition(250, 575);
//		aliens[0].setState(AlienState.ALIVE);
//		stage.addActor(aliens[0]);
	}

	private void placeAliens() {
		for( int j = 0; j < 5 ; j++) {
			for(int i = 0; i < 10; i++) {
				aliens[i + j * 10].setPosition(120 + i * 45, 525 + j * 40);
				aliens[i + j * 10].setState(AlienState.ALIVE);
			}
		}
	}

	void act(float dt) {
		swarmXTimer += dt;
		if (swarmXTimer > 2.0f) swarmXTimer = 0.0f;
		float swarmXPos = (swarmXTimer > 1.0f) ? 2.0f - swarmXTimer: swarmXTimer;

		for( int i= 0; i< MAX_ALIENS; i++) {
			if(!aliens[i].isAlive()) continue;
			aliens[i].setPosition(100 + swarmXPos * 30 + (i % 10) * 45 , aliens[i].getY());
			int fire_chance = MathUtils.random(10000);
			if(fire_chance < 100) {
				// alien fires
			}
 		}

		stage.act(dt);
	}


	void draw() {
		stage.draw();
	}


	void handleCollisions(List<BulletBaseActor> bullets, PlayerScore score) {

		LiveAliens().forEach(
				a -> {
					bullets.stream().filter(a::collidesWith).forEach(
							b -> {
								killAlien(a);
								score.updateScore(a.getScore());
								b.removeFromPlay();
							}
					);
				}
		);
	}


	void killAllAliens(PlayerScore score) {

		LiveAliens().forEach(
				a -> {killAlien(a); score.updateScore(a.getScore());}
		);
	}


	private void killAlien(BaseAlien alien) {

		alien.setState(AlienState.DEAD);

		particleFoundry.newEmitter(alien.getCentreX(), alien.getCentreY());

		float pan = (alien.getCentreX() - HALF_SCREEN_WIDTH)/HALF_SCREEN_WIDTH;
		explosionSound.play(1.0f,((float) Math.random() * 0.6f) + 0.7f,pan);
	}


	private Stream<BaseAlien> LiveAliens() {
		return Arrays.stream(aliens).filter(BaseAlien::isAlive);
	}
}
