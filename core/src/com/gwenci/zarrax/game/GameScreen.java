package com.gwenci.zarrax.game;

import com.gwenci.zarrax.BaseScreen;
import com.gwenci.zarrax.Starfield;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.game.states.*;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

	// GUI
	public boolean muted = false;
	public boolean paused = false;
	public PlayerScore playerScore;
	public FrameRate framerate;

	private List<Updatable> updatables;
	private GameWorld gameWorld;
	private Renderer renderer;

	private StateManager stateManager;


	@Override
	public void initialize() {
		ParticleFoundry.getInstance().resetFoundry();
		framerate = new FrameRate();
		framerate.setDisplay(true);
		playerScore = new PlayerScore(0.01f);  // the displayScore counts up by 1 every 0.01s up to the value of the score

		gameWorld = new GameWorld();
		gameWorld.initialise();
		renderer = new Renderer(this, gameWorld);
		setUpUpdatables();
		setUpStateMachine();
	}

	private void setUpUpdatables() {
		updatables = new ArrayList<>();
		// GUI
		updatables.add(playerScore);

		// Background
		updatables.add(Starfield.getInstance());
		updatables.add(ParticleFoundry.getInstance());

		// GameWorld
		updatables.add(gameWorld.playerBullets);
		updatables.add(gameWorld.alienBullets);

	}


	private void setUpStateMachine() {
		stateManager = new StateManager();
		stateManager.addState(GameStateKey.LEVEL_START,new LevelStart(gameWorld, updatables));
		stateManager.addState(GameStateKey.PLAYER_START,new PlayerStart(gameWorld));
		stateManager.addState(GameStateKey.GAME_LOOP,new GameLoop(this, gameWorld, updatables, renderer));
		stateManager.addState(GameStateKey.PLAYER_DIED, new PlayerDied(this, gameWorld, updatables, renderer));
		stateManager.addState(GameStateKey.LEVEL_END, new LevelEnd(this, gameWorld,updatables,renderer));
		stateManager.changeState(GameStateKey.LEVEL_START);

	}

	@Override
	public void update(float dt) {
		framerate.update();
		stateManager.getCurrentState().update(dt);
	}

	@Override
	public void render() {
		stateManager.getCurrentState().render();
	}

	@Override
	public void resize(int width, int height) {	}

	@Override
	public void dispose() {	}
}
