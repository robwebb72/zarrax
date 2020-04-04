package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;

public class AlienActor2 extends BaseAlien {

	public AlienActor2(Texture texture) {
		super(texture, 3, 0.25f);
	}

	@Override
	public int getScore() {
		return 20;
	}
}