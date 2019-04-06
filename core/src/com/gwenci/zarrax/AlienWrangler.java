package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AlienWrangler {

	private static final int MAX_ALIENS = 50;
	private Stage stage;
	private Texture alien1texture =  new Texture(Gdx.files.internal("assets/galaxian_1_1.png"));

	private AlienActor[] aliens = new AlienActor[MAX_ALIENS];

	AlienWrangler(Viewport vp, SpriteBatch batch) {
		stage = new Stage(vp,batch);
		for(int i = 0 ; i< MAX_ALIENS; i++) {
			aliens[i] = new AlienActor(alien1texture);
			stage.addActor(aliens[i]);
		}
		placeAliens();

		// just the one alien, please..
//		aliens[0] = new AlienActor(alien1texture);
//		aliens[0].setPosition(250, 575);
//		aliens[0].setState(AlienState.ALIVE);
//		stage.addActor(aliens[0]);

	}

	private void placeAliens() {
		for( int j = 0; j < 5 ; j++) {
			for(int i =0; i < 10; i++) {
				aliens[i + j * 10].setPosition(60 + i * 60, 575 + j * 35);
				aliens[i + j * 10].setState(AlienState.ALIVE);
			}
		}
	}

	void act(float dt) {
		stage.act(dt);
	}
	void draw() {
		stage.draw();
	}

	void handleCollisions(List<PlayerBulletActor> bullets) {
		for (int i= 0; i< MAX_ALIENS; i++) {

			if(!aliens[i].isAlive()) continue;
			for (PlayerBulletActor bullet : bullets) {
				if(aliens[i].collidesWith(bullet)) {
					aliens[i].setState(AlienState.DEAD);
					bullet.setInPlay(false);
					// NEED AN EXPLOSION!
				}
			}
		}

	}

}
