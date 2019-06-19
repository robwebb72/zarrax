package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;

class AlienActor1 extends BaseAlien {

	AlienActor1(Texture texture) {
		super(texture, 3, 0.2f);

	}

	@Override
	int getScore() {
		return 10;
	}
}