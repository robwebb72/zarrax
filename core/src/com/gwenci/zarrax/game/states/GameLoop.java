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

public class GameLoop extends GameState {

	private final GameScreen gs;
	private final GameWorld gameWorld;
	private final Renderer renderer;
	private final List<Updatable> updatables;

	private StateManager stateManager;

	public GameLoop(GameScreen gs, GameWorld gameWorld, List<Updatable> updatables, Renderer renderer) {
		this.gameWorld = gameWorld;
		this.updatables = updatables;
		this.renderer = renderer;
		this.gs = gs;
	}

	@Override
	public void setStateManager(StateManager stateManager) {
		this.stateManager = stateManager;
	}

	@Override
	public void start() { }

	@Override
	public void update(float dt) {
		handleInputs();
		updateWorld(dt);
		if(gameWorld.aliens.noOfLiveAliens()==0) {
			stateManager.changeState(GameStateKey.LEVEL_END);
 		}
	}

	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z))
			gameWorld.aliens.killAllAliens(gs.playerScore);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) gs.framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) stateManager.changeState(GameStateKey.PLAYER_DIED);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) gs.paused = !gs.paused;
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) gameWorld.playerActor.setShieldsOn(3.0f);
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			gs.muted = !gs.muted;
			SoundSystem.getInstance().setMute(gs.muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Zarrax.setActiveScreen(new AssetDisposer());
			gs.dispose();
		}
	}

	public void updateWorld(float dt) {
		if(gs.paused) return;
		gameWorld.playerActor.act(dt);
		updatables.forEach(update -> update.update(dt));

		//TODO: handle collisions
		// aliens and players bullets
		// player and aliens' bullets
		// player and aliens
		// player and power ups
		gameWorld.aliens.handleCollisions(gameWorld.playerBullets.getActiveBullets(), gs.playerScore);

	}

	@Override
	public void render() {
		renderer.render();
	}
}
