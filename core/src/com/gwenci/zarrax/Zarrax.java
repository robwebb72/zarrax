package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Zarrax extends BaseGame {

	private static SpriteBatch batch;
	private static Viewport viewPort;
	BitmapFont customFont;

	static SpriteBatch getSpriteBatch() {
		return batch;
	}

	static Viewport getViewPort() {
		return viewPort;
	}

	@Override
	public void create() {

		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		viewPort = new FitViewport(672,768, camera);
		ParticleFoundry.getInstance();
		setActiveScreen(new MenuScreen());
	}

	public void dispose() {
		batch.dispose();
	}
}


