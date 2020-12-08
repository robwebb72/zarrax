package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public abstract class BaseScreen implements Screen {

	public BaseScreen() {
		initialize();

	}

	public abstract void initialize();

	public abstract void update(float dt);

	@Override
	public void render(float dt) {
		update(dt);
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		render();
	}

	public abstract void render();

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

}
