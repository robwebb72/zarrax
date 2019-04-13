package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Particle {

	private float x;
	private float y;
	private float dx;
	private float dy;
	private float lifeLeft;
	private float halfLife;
	private int colour;
	private Texture texture;

	Particle() {
	}

	void initialise(float x, float y, float speed, Texture texture, int colour, float life) {

		this.x = x;
		this.y = y;

		do {
			this.dx = 2.0f * ((float) Math.random() - 0.5f) * speed;
			this.dy = 2.0f * ((float) Math.random() - 0.5f) * speed;
		} while ((dx * dx + dy * dy) > speed * speed);

		this.colour = colour;
		this.texture = texture;
		lifeLeft = life;
		halfLife = life / 2.0f;
	}

	void act(float dt) {
		if (isFinished()) return;
		x += (dt * dx);
		y += (dt * dy);
		lifeLeft -= dt;

	}

	void render(SpriteBatch batch) {
		if (isFinished()) return;
		if (lifeLeft < halfLife) {
			batch.draw(texture, x, y, (colour + 8) * 2, 0, 2, 2);

		} else {
			batch.draw(texture, x, y, colour * 2, 0, 2, 2);
		}
	}

	boolean isFinished() {
		return (lifeLeft <= 0.0f);
	}
}
