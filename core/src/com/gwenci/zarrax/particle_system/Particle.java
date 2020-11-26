package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

class Particle {

	private final Vector2 position = new Vector2();
	private final Vector2 velocity = new Vector2();
	private float lifeLeftInSeconds;
	private float halfLifeInSeconds;
	private int colour;
	private Texture texture;
	private TextureRegion texRegion;
	private boolean bright = true;
	private int size = 2;


	void initialise(float x, float y, float speed, Texture texture, int colour, float lifeInSeconds) {
		this.position.set(x, y);
		this.velocity.set(createVelocityVector(speed));
		this.colour = colour;
		this.texture = texture;
		texRegion = new TextureRegion(texture, colour * 2, 0, 2, 2);
		this.lifeLeftInSeconds = lifeInSeconds;
		this.halfLifeInSeconds = lifeInSeconds / 2.0f;
	}


	private Vector2 createVelocityVector(float speed) {
		Vector2 velocity = new Vector2();

		// do... while... loop to ensure particles are within a circle
		do {
			velocity.set(
					((float) Math.random() * 2.0f - 1.0f) * speed,
					((float) Math.random() * 2.0f - 1.0f) * speed
			);
		} while (velocity.len2() > speed * speed);

		return velocity;
	}


	void act(float dt) {
		if (this.isActive()) {
			position.mulAdd(velocity, dt);
			lifeLeftInSeconds -= dt;
		}
	}


	void render(SpriteBatch batch) {
		if (this.isActive()) {
			if (lifeLeftInSeconds < halfLifeInSeconds) {
				if (bright) {
					texRegion = new TextureRegion(this.texture, (colour + 8) * 2, 0, 2, 2);
					bright = false;
				}
			}
			if (lifeLeftInSeconds < halfLifeInSeconds / 2) size = 1;
			batch.draw(texRegion, position.x, position.y, size, size);
		}
	}


	boolean isActive() {
		return (lifeLeftInSeconds > 0.0f);
	}

}