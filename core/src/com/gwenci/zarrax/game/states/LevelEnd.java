package com.gwenci.zarrax.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gwenci.zarrax.SoundSystem;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.game.GameScreen;
import com.gwenci.zarrax.game.GameWorld;
import com.gwenci.zarrax.game.Renderer;

import java.util.List;

public class LevelEnd extends GameState {

	private final GameWorld gameWorld;
	private final List<Updatable> updatables;
	private final Renderer renderer;
	private final GameScreen gameScreen;

	private float timer;

	public LevelEnd(GameScreen gameScreen, GameWorld gameWorld, List<Updatable> updatables, Renderer renderer) {
		this.gameWorld = gameWorld;
		this.gameScreen = gameScreen;
		this.updatables = updatables;
		this.renderer = renderer;
	}

	@Override
	public void start() {
		updatables.remove(gameWorld.aliens);
		gameWorld.playerBullets.reset();
		gameWorld.alienBullets.reset();
		timer = 2.0f;
	}

	@Override
	public void update(float dt) {
		timer -= dt;
		handleInputs();
		updateWorld(dt);

		if (timer<=0.0f) stateManager.changeState(GameStateKey.LEVEL_START);

	}
	private void handleInputs() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) gameScreen.framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) stateManager.changeState(GameStateKey.PLAYER_DIED);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) gameScreen.paused = !gameScreen.paused;
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) gameWorld.playerActor.setShieldsOn(3.0f);
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			gameScreen.muted = !gameScreen.muted;
			SoundSystem.getInstance().setMute(gameScreen.muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Zarrax.setActiveScreen(new AssetDisposer());
			gameScreen.dispose();
		}
	}

	public void updateWorld(float dt) {
		if(gameScreen.paused) return;
		gameWorld.playerActor.act(dt);
		updatables.forEach(update -> update.update(dt));
		gameWorld.handleCollisions(gameScreen);
	}

	@Override
	public void render() {
		renderer.render();
	}
}
