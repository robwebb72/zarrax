package com.gwenci.zarrax.game.states;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

	private final Map<GameStateKey,GameState> states = new HashMap<>();
	private GameState currentState= null;

	public void addState(GameStateKey stateKey, GameState state) {
		states.put(stateKey,state);
		state.setStateManager(this);
	}

	public void changeState(GameStateKey stateKey) {
		GameState newState = states.get(stateKey);
		if (newState==null) return;
		if (newState!=currentState) {
			currentState = newState;
			currentState.start();
		}
	}

	public GameState getCurrentState() {
		return currentState;
	}
}
