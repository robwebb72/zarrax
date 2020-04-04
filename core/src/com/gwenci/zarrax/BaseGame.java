package com.gwenci.zarrax;

import com.badlogic.gdx.Game;

abstract class BaseGame extends Game {

	private static BaseGame game;

	BaseGame() {
		game = this;
	}

	public static void setActiveScreen(BaseScreen s) {
		game.setScreen(s);
	}
}
