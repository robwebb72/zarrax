package com.gwenci.zarrax.asset_handler;

import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.BaseScreen;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.menu.MenuScreen;


public class AssetLoader extends BaseScreen {

	@Override
	public void initialize() {
		loadTextures();
		loadSounds();
		loadFonts();
	}

	private void loadTextures() {
		TextureManager texManager = TextureManager.getInstance();
		texManager.get("stars.png");
		texManager.get("spectrum.png");
		texManager.get("alien_bullet.png");
		texManager.get("alien_bullet2.png");
		texManager.get("sprites/alien01.png");
		texManager.get("sprites/alien02.png");
		texManager.get("sprites/alien03.png");
		texManager.get("sprites/alien04.png");
		texManager.get("player_bullet.png");
		texManager.get("player.png");

	}

	private void loadSounds() {
		AudioManager am = AudioManager.getInstance();

		// explosions
		am.get("sfx/alienexpl.wav");
		am.get("sfx/player_expd.wav");

		// firing sounds
		am.get("sfx/player_fire.wav");
		am.get("sfx/alien_fire_1.wav");
		am.get("sfx/alien_fire_2.wav");

		// other sounds
		am.get("sfx/shield_hit.wav");
	}

	private void loadFonts() {
		// TODO: Need to handle fonts as assets
	}


	@Override
	public void update(float dt) {
		Zarrax.setActiveScreen(new MenuScreen());
	}


	@Override
	public void render() {
	}


	@Override
	public void resize(int width, int height) {
	}

}

