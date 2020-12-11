package com.gwenci.zarrax.game.states;

public abstract class GameState {

	StateManager stateManager;
	void setStateManager(StateManager stateManager) {
		this.stateManager = stateManager;
	};
	abstract void start();
	abstract public void update(float dt);
	abstract public void render();
}
