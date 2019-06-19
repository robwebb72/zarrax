package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

class AlienWrangler {

	private static final int MAX_ALIENS = 50;
	private Stage stage;
	private Texture alien1texture =  new Texture(Gdx.files.internal("assets/galaxian_1_1.png"));
	private Texture alien2texture =  new Texture(Gdx.files.internal("assets/galaxian_2_1.png"));
	private ParticleFoundry particleFoundry = ParticleFoundry.getInstance();
	private BaseAlien[] aliens = new BaseAlien[MAX_ALIENS];
	private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth()/2;
	private float swarmXTimer = 0.0f;
	private static Sound explosionSound;

	static {
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/alienexpl.wav"));
	}


	AlienWrangler(Viewport vp, SpriteBatch batch) {
		stage = new Stage(vp,batch);
		for(int i = 0 ; i< MAX_ALIENS; i++) {
			if(i>=30) {
				aliens[i] = new AlienActor2(alien2texture);
			} else {
				aliens[i] = new AlienActor1(alien1texture);
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
 		}

		stage.act(dt);
	}
	void draw() {
		stage.draw();
	}


	void handleCollisions(List<PlayerBulletActor> bullets, PlayerScore score) {
		for (int i= 0; i< MAX_ALIENS; i++) {

			if(!aliens[i].isAlive()) continue;
			for (PlayerBulletActor bullet : bullets) {
				if(aliens[i].collidesWith(bullet)) {
					killAlien(i);
					score.updateScore(aliens[i].getScore());
					bullet.setInPlay(false);
				}
			}
		}

	}


	void killAllAliens(PlayerScore score) {
		for (int i= 0; i< MAX_ALIENS; i++) {
			if(aliens[i].isAlive()) {
				killAlien(i);
				score.updateScore(aliens[i].getScore());
			}
		}
	}


	private void killAlien(int i) {
		aliens[i].setState(AlienState.DEAD);
		particleFoundry.newEmitter(aliens[i].getCentreX(), aliens[i].getCentreY());

		float pan = (aliens[i].getCentreX() - HALF_SCREEN_WIDTH)/HALF_SCREEN_WIDTH;
		explosionSound.play(1.0f,((float) Math.random() * 0.6f) + 0.7f,pan);
	}

}
