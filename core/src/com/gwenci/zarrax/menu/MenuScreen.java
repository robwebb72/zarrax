package com.gwenci.zarrax.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.BaseScreen;
import com.gwenci.zarrax.game.GameScreen;
import com.gwenci.zarrax.Starfield;
import com.gwenci.zarrax.Zarrax;

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
