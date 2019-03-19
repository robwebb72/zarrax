package com.gwenci.zarrax;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuScreen extends BaseScreen {

	private Starfield stars;
	private SpriteBatch batch = Zarrax.getSpriteBatch();

	@Override
	public void initialize() {
		stars = Starfield.getInstance();
	}

	@Override
	public void update(float dt) {
		Zarrax.setActiveScreen(new GameScreen());
	}

	@Override
	public void render() {
		batch.begin();
		stars.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

}
