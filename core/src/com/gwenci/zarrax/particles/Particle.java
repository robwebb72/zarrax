package com.gwenci.zarrax.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

class Particle {
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();
	private float lifeLeftInSeconds;
	private float halfLifeInSeconds;
	private int colour;
	private Texture texture;


	Particle() { }


	void initialise(float x, float y, float speed, Texture texture, int colour, float lifeInSeconds) {
		this.position.set(x, y);
		this.velocity.set(createVelocityVector(speed));
		this.colour = colour;
		this.texture = texture;
		this.lifeLeftInSeconds = lifeInSeconds;
		this.halfLifeInSeconds = lifeInSeconds / 2.0f;
	}


	private Vector2 createVelocityVector(float speed) {
		Vector2 velocity = new Vector2();

		do {
			velocity.set (
					((float) Math.random() * 2.0f - 1.0f) * speed,
					((float) Math.random() * 2.0f - 1.0f) * speed
			);
		} while (velocity.len2() > speed * speed);

		return velocity;
	}


	void act(float dt) {
		if (isActive()) {
			position.mulAdd(velocity,dt);
			lifeLeftInSeconds -= dt;
		}
	}


	void render(SpriteBatch batch) {
		if (this.isActive()) {
			int dullnessColourOffset = lifeLeftInSeconds < halfLifeInSeconds ? 8 : 0;
			batch.draw(texture, position.x, position.y, (colour + dullnessColourOffset) * 2, 0, 2, 2);
		}
	}


	boolean isActive() {
		return (lifeLeftInSeconds > 0.0f);
	}

}