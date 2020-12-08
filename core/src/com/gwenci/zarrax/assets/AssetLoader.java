package com.gwenci.zarrax.assets;

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
		texManager.get("assets/stars.png");
		texManager.get("assets/spectrum.png");
		texManager.get("assets/alien_bullet.png");
		texManager.get("assets/alien_bullet2.png");
		texManager.get("assets/sprites/galaxian_1_1.png");
		texManager.get("assets/sprites/galaxian_2_1.png");
		texManager.get("assets/sprites/galaxian_3_1.png");
		texManager.get("assets/player_bullet.png");
		texManager.get("assets/player.png");

	}

	private void loadSounds() {
		AudioManager am = AudioManager.getInstance();

		// explosions
		am.get("assets/sfx/alienexpl.wav");

		// firing sounds
		am.get("assets/sfx/player_fire.wav");
		am.get("assets/sfx/alien_fire_1.wav");
		am.get("assets/sfx/alien_fire_2.wav");
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

