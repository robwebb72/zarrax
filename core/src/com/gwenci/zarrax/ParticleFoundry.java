package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class ParticleFoundry {

	private static final int MAX_EMITTERS = 50;
	private static final int MAX_PARTICLES_PER_EMITTER = 300;
	private static final int MAX_PARTICLES = MAX_PARTICLES_PER_EMITTER * MAX_EMITTERS;

	private static ParticleFoundry instance = new ParticleFoundry();
	private Particle[] particles = new Particle[MAX_PARTICLES];
	private ParticleEmitter[] particleEmitters = new ParticleEmitter[50];
	private int emitterIndex = 0;

	private ParticleFoundry() {
		setUpEmitters();
	}

	static ParticleFoundry getInstance() {
		return instance;
	}

	private void setUpEmitters() {
		for (int i = 0; i < MAX_PARTICLES; i++) {
			particles[i] = new Particle();
		}
		int particleCounter = 0;
		for (int i = 0; i < MAX_EMITTERS; i++) {
			particleEmitters[i] = new ParticleEmitter();
			for (int j = 0; j < MAX_PARTICLES_PER_EMITTER; j++) {
				particleEmitters[i].add(particles[particleCounter++]);
			}
		}
	}

	private ParticleEmitter getNextEmitter() {
		int originalIndex = emitterIndex;
		emitterIndex++;

		while (emitterIndex != originalIndex) {
			if (emitterIndex == MAX_EMITTERS) emitterIndex = 0;
			if (!particleEmitters[emitterIndex].isLive()) {
				return particleEmitters[emitterIndex];
			}
			emitterIndex++;
		}
		return null;
	}

	void newEmitter(float x, float y) {
		ParticleEmitter emitter = getNextEmitter();
		if (emitter!=null) emitter.initialize(x, y);
	}

	void render(SpriteBatch batch) {
		for (ParticleEmitter emitter : particleEmitters) emitter.render(batch);
	}

	void act(float dt) {
		for (ParticleEmitter emitter : particleEmitters) emitter.act(dt);
	}
}
