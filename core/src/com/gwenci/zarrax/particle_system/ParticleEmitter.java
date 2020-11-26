package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.TextureManager;

import java.util.ArrayList;


class ParticleEmitter {
	private final ArrayList<Particle> particles = new ArrayList<>();
	private final Texture texture = TextureManager.getInstance().get("assets/spectrum.png");
	private boolean isActive = false;
	private boolean reserved = false;


	void addParticle(Particle particle) {
		particles.add(particle);
	}


	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	void initialize(float x, float y) {
		int counter = 0;

		for (Particle p : particles) {
			switch (counter) {
				case 0:
					p.initialise(x, y, 150, texture, 2, 1.0f);
					break;
				case 1:
					p.initialise(x, y, 300, texture, 1, 1.0f);
					break;
				case 2:
				case 3:
					p.initialise(x, y, 900, texture, 0, 1.0f);
			}
			counter++;
			if (counter > 3) counter = 0;
		}
		isActive = true;
	}

	boolean isAvailable() {
		return !isActive && !reserved;
	}

	boolean isActive() {
		return isActive;
	}


	void render(SpriteBatch batch) {
		if (isActive) {
			particles.forEach(particle -> particle.render(batch));
		}
	}


	void act(float dt) {
		if (isActive) {
			particles.parallelStream().forEach(particle -> particle.act(dt));
			isActive = anyParticleAlive();
		}
	}


	private boolean anyParticleAlive() {
		return particles.parallelStream().anyMatch(Particle::isActive);
	}
}
