package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gwenci.zarrax.*;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.particle_system.ParticleEffectPlayerExplosion;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {


	// GUI
	boolean muted = false;
	boolean paused = false;

	PlayerScore playerScore;

	Starfield starfield;
	FrameRate framerate;

	private ParticleFoundry particleFoundry;

	private List<Updatable> updatables;
	private GameState gameState;
	private GameWorld gameWorld;
	private Renderer renderer;


	enum GameState {
		LEVEL_START,
		LEVEL_END,
		PLAYER_START,
		GAME_LOOP,
		PLAYER_DIED,
		GAME_OVER
	}



	@Override
	public void initialize() {
		renderer = new Renderer();
		starfield = Starfield.getInstance();
		particleFoundry = ParticleFoundry.getInstance();
		ParticleFoundry.getInstance().resetFoundry();
		framerate = new FrameRate();
		framerate.setDisplay(true);
		playerScore = new PlayerScore(0.01f);  // the displayScore counts up by 1 every 0.01s up to the value of the score

		gameWorld = new GameWorld();
		gameWorld.initialise();

		gameState = GameState.LEVEL_START;
		setUpUpdatables();
	}

	private void setUpUpdatables() {
		updatables = new ArrayList<>();
		// GUI
		updatables.add(playerScore);

		// Background
		updatables.add(starfield);
		updatables.add(particleFoundry);

		// GameWorld
		updatables.add(gameWorld.playerBullets);
		updatables.add(gameWorld.alienBullets);

	}


	@Override
	public void update(float dt) {
		framerate.update();

		switch (gameState) {
			case LEVEL_START:
				gameWorld.initialiseAliens();
				updatables.add(gameWorld.aliens);
				gameState = GameState.PLAYER_START;
				break;
			case PLAYER_START:
				gameWorld.initialisePlayer();
				gameState = GameState.GAME_LOOP;
				break;
			case GAME_LOOP:
				updateGameWorld(dt);
				handleInputs();
				break;
			case PLAYER_DIED:
				updatePlayerDied(dt);
				updateGameWorld(dt);
				handleInputs();
				break;
			case LEVEL_END:
				updatables.remove(gameWorld.aliens);
				gameWorld.playerBullets.reset();
				gameWorld.alienBullets.reset();
				gameState = GameState.LEVEL_START;
			case GAME_OVER:
		}

	}

	float playerDiedTimer = 1.0f;
	private void updatePlayerDied(float dt) {
		if (playerDiedTimer >= 0.0f) {
			playerDiedTimer -= dt;
		} else {
			// if player lives > 0 ... and so on...
			changeState(GameState.PLAYER_START);
			playerDiedTimer = 1.0f;
		}
	}

	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) gameWorld.aliens.killAllAliens(playerScore);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) changeState(GameState.PLAYER_DIED);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) paused = !paused;
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) gameWorld.playerActor.setShieldsOn(3.0f);


		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			muted = !muted;
			SoundSystem.getInstance().setMute(muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			Zarrax.setActiveScreen(new AssetDisposer());
		}

	}

	private void updateGameWorld(float dt) {
		if(gameWorld.aliens.noOfLiveAliens()==0)  {
			gameState = GameState.LEVEL_END;
			return;
		}
		if(!paused) {
			gameWorld.playerActor.act(dt);
			updatables.forEach(update -> update.update(dt));
			gameWorld.aliens.handleCollisions(gameWorld.playerBullets.getActiveBullets(), playerScore);
		}
	}

	@Override
	public void render() {
		renderer.render(this, gameWorld);
	}

	@Override
	public void resize(int width, int height) {

	}


	public void changeState(GameState state) {
		if (this.gameState==state) return;
		this.gameState = state;

		switch(state){
			case PLAYER_DIED:
				setUpPlayerDied();
				break;
			default:
				break;
		}
	}


	private void setUpPlayerDied() {
		// TODO: Player Lives - 1
		// TODO: Screen shake
		// TODO: Play Explosion
		// TODO: Print "Gotcha!" message
		gameWorld.playerActor.setIsAlive(false);

		particleFoundry.newEmitter(gameWorld.playerActor, new ParticleEffectPlayerExplosion());
	}

	@Override
	public void dispose() {

	}
}
