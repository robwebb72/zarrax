package com.gwenci.zarrax.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.TextureManager;

import java.util.ArrayList;


class ParticleEmitter {
	private ArrayList<Particle> particles = new ArrayList<>();
	private Texture texture = TextureManager.getInstance().get("assets/spectrum.png");
	private boolean isLive = false;


	void addParticle(Particle particle) {
		particles.add(particle);
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
		isLive = true;
	}


	boolean isLive() {
		return isLive;
	}


	void render(SpriteBatch batch) {
		if (isLive) {
			particles.forEach(particle -> particle.render(batch));
		}
	}


	void act(float dt) {
		if (isLive) {
			particles.parallelStream().forEach(particle -> particle.act(dt));
			isLive = anyParticleAlive();
		}
	}


	private boolean anyParticleAlive() {
		return particles.parallelStream().anyMatch(Particle::isActive);
	}
}
