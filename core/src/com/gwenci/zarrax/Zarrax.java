package com.gwenci.zarrax;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Zarrax extends BaseGame {

	private static SpriteBatch batch;
	private static Viewport viewPort;
	private static ParticleFoundry particleFoundry;

	@Override
	public void create ()
	{
		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		viewPort = new ScreenViewport(camera);
		particleFoundry = ParticleFoundry.getInstance();
		setActiveScreen(new MenuScreen());
	}

	public static SpriteBatch getSpriteBatch() {
		return batch;
	}

	public static Viewport getViewPort() {
		return viewPort;
	}

	public void dispose() {
		batch.dispose();
	}
}


