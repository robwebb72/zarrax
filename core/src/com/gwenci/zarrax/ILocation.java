package com.gwenci.zarrax;

import com.badlogic.gdx.math.Vector2;

public interface ILocation {
	Vector2 location();
	default float getX() {
		return location().x;
	}
}
