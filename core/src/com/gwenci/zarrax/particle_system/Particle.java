package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Function;

class Particle {

	private final Vector2 position = new Vector2();
	private final Vector2 velocity = new Vector2();
	private ParticleEmitter emitter;
	private float remainingLife;
	private float lifeInSeconds;
	private int colour;
	private Texture texture;
	private TextureRegion texRegion;
	private Function<Float, Integer> colourFunc;
	private Function<Float, Integer> sizeFunc;


	void initialise(Vector2 location, ParticleType pt, Texture texture) {
		this.lifeInSeconds = pt.lifeTime.get();
		this.remainingLife = lifeInSeconds;
		this.position.set(location).add(pt.offset).add(pt.offsetFunc.get());
		this.velocity.set(pt.moveFunc.get());
		this.texture = texture;        // must be declared BEFORE setColour is used
		this.colourFunc = pt.colourFunc;
		this.sizeFunc = pt.sizeFunc;
		float time_pc = this.remainingLife / this.lifeInSeconds;
		setColour(colourFunc.apply(time_pc));
	}

	void reset() {
		lifeInSeconds = 0.0f;
		remainingLife = 0.0f;
	}

	void setEmitter(ParticleEmitter emitter) {
		this.emitter = emitter;
	}

	void act(float dt) {
		if (this.isActive()) {
			position.mulAdd(velocity, dt);
			remainingLife -= dt;
			if (remainingLife <= 0.0f) {
				emitter.particleHasDied(this);
			}
		}
	}

	void render(SpriteBatch batch) {
		if (this.isActive()) {
			float time_pc = remainingLife / lifeInSeconds;
			updateColour(colourFunc.apply(time_pc));
			int size = sizeFunc.apply(time_pc);
			batch.draw(texRegion, position.x, position.y, size, size);
		}
	}

	private void updateColour(int colour) {
		if (this.colour != colour) setColour(colour);
	}

	private void setColour(int colour) {
		this.colour = colour;
		texRegion = new TextureRegion(this.texture, colour * 2, 0, 2, 2);
	}

	boolean isActive() {
		return (remainingLife > 0.0f);
	}

}