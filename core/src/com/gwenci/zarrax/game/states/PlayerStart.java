package com.gwenci.zarrax.game.states;

import com.gwenci.zarrax.game.GameWorld;

public class PlayerStart extends GameState {

	private final GameWorld gameWorld;


	public PlayerStart(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void start() {
		gameWorld.initialisePlayer();
		this.stateManager.changeState(GameStateKey.GAME_LOOP);
	}

	@Override
	public void update(float dt) { }

	@Override
	public void render() { }
}
