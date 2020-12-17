package com.gwenci.zarrax.game.states;

import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.game.GameWorld;

import java.util.List;

public class LevelStart extends GameState {

	private final GameWorld gameWorld;
	private final List<Updatable> updatables;


	public LevelStart(GameWorld gameWorld, List<Updatable> updatables) {
		this.gameWorld = gameWorld;
		this.updatables = updatables;
	}

	@Override
	public void start() {
		this.gameWorld.initialiseAliens();
		this.updatables.add(gameWorld.aliens);
		this.stateManager.changeState(GameStateKey.PLAYER_START);
	}

	@Override
	public void update(float dt) {}

	@Override
	public void render() {}
}
