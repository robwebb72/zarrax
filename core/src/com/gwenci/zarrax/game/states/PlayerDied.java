package com.gwenci.zarrax.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.SoundSystem;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.game.GameScreen;
import com.gwenci.zarrax.game.GameWorld;
import com.gwenci.zarrax.game.Renderer;
import com.gwenci.zarrax.particle_system.ParticleEffectPlayerExplosion;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

import java.util.List;

public class PlayerDied extends GameState {

	private final GameScreen gs;
	private final GameWorld gameWorld;
	private final Renderer renderer;
	private final List<Updatable> updatables;

	private float playerDiedTimer;


	public PlayerDied(GameScreen gs, GameWorld gameWorld, List<Updatable> updatables, Renderer renderer) {
		this.gameWorld = gameWorld;
		this.gs = gs;
		this.updatables = updatables;
		this.renderer = renderer;
	}

	@Override
	public void start() {
		// TODO: Player Lives - 1
		// TODO: Screen shake
		// TODO: Play Explosion
		// TODO: Print "Gotcha!" message
		gameWorld.playerActor.setIsAlive(false);
		playerDiedTimer = 1.0f;
		SoundSystem.getInstance().play(
				AudioManager.getInstance().get("sfx/player_expd.wav"),
				gameWorld.playerActor,
				1.0f,
				0.3f
		);
		ParticleFoundry.getInstance().newEmitter(gameWorld.playerActor, new ParticleEffectPlayerExplosion());
	}

	@Override
	public void update(float dt) {
		if (!gs.paused) playerDiedTimer -= dt;
		if (playerDiedTimer<=0.0f) {
			stateManager.changeState(GameStateKey.PLAYER_START);
		}
		handleInputs();
		updateWorld(dt);
	}

	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) gameWorld.aliens.killAllAliens(gs.playerScore);
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
		gameWorld.handleCollisions(gs);
	}

	@Override
	public void render() {
		renderer.render();
	}
}