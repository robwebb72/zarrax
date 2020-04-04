package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;

public class AlienActor1 extends BaseAlien {

	public AlienActor1(Texture texture) {
		super(texture, 3, 0.2f);

	}

	@Override
	public int getScore() {
		return 10;
	}
}