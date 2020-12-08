package com.gwenci.zarrax.assets;

import com.badlogic.gdx.Gdx;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.BaseScreen;
import com.gwenci.zarrax.TextureManager;

public class AssetDisposer extends BaseScreen {

	@Override
	public void initialize() {
		TextureManager.getInstance().dispose();
		AudioManager.getInstance().dispose();
		Gdx.app.exit();
	}

	@Override
	public void update(float dt) { }

	@Override
	public void render() { }

	@Override
	public void resize(int width, int height) {	}

}

