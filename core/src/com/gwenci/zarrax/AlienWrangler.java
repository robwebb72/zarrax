package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AlienWrangler {

	private static final int MAX_ALIENS = 50;
	private Texture alien1texture =  new Texture(Gdx.files.internal("assets/galaxian_1_1.png"));
	private Stage stage;

	AlienActor[] aliens = new AlienActor[MAX_ALIENS];

	AlienWrangler(Viewport vp, SpriteBatch batch) {
		stage = new Stage(vp,batch);
		for(int i = 0 ; i< MAX_ALIENS; i++) {
			aliens[i] = new AlienActor(alien1texture);
			stage.addActor(aliens[i]);
		}

		aliens[0].setState(AlienState.ALIVE);
		placeAliens();
	}


	private void placeAliens() {
		for( int j = 0; j < 5 ; j++) {
			for(int i =0; i < 10; i++) {
				aliens[i + j * 10].setLocation(60 + i * 60, 575 + j * 35);
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

}
