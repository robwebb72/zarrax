package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.assets.AssetLoader;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

public class Zarrax extends BaseGame {

	private static SpriteBatch batch;
	private static Viewport viewPort;
	BitmapFont customFont;

	public static SpriteBatch getSpriteBatch() {
		return batch;
	}

	public static Viewport getViewPort() {
		return viewPort;
	}

	@Override
	public void create() {

		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		viewPort = new FitViewport(672,768, camera);
		ParticleFoundry.getInstance();
		setActiveScreen(new AssetLoader());
	}

	public void dispose() {
		batch.dispose();
	}
}


