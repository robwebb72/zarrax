package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.Updatable;

import java.util.Arrays;


public class ParticleFoundry implements Updatable {
	private static final int MAX_EMITTERS = 50;
	private static final int MAX_PARTICLES_PER_EMITTER = 300;
	private static final int MAX_PARTICLES = MAX_PARTICLES_PER_EMITTER * MAX_EMITTERS;

	private static ParticleFoundry instance = new ParticleFoundry();
	private Particle[] particles = new Particle[MAX_PARTICLES];
	private ParticleEmitter[] particleEmitters = new ParticleEmitter[50];
	private int emitterIndex = 0;


	private ParticleFoundry() {
		setUpParticleSystem();
	}


	public static ParticleFoundry getInstance() {
		return instance;
	}


	private void setUpParticleSystem() {
		createParticles();
		createEmitters();
		assignParticlesToEmitters();
	}


	private void createParticles() {
		for (int i = 0; i < MAX_PARTICLES; i++) {
			particles[i] = new Particle();
		}
	}


	private void createEmitters() {
		for(int i = 0; i < MAX_EMITTERS; i++) {
			particleEmitters[i] = new ParticleEmitter();
		}
	}


	private void assignParticlesToEmitters() {
		int particleIndex = 0;
		for (int i = 0; i < MAX_EMITTERS; i++) {
			for (int j = 0; j < MAX_PARTICLES_PER_EMITTER; j++) {
				particleEmitters[i].addParticle(particles[particleIndex++]);
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


	public void newEmitter(float x, float y) {
		ParticleEmitter emitter = getNextEmitter();
		if (emitter!=null) emitter.initialize(x, y);
	}


	public void render(SpriteBatch batch) {
		Arrays.stream(particleEmitters).forEach(emitter -> emitter.render(batch));
	}


	@Override
	public void update(float dt) {
		Arrays.stream(particleEmitters).parallel().filter(ParticleEmitter::isLive).forEach(emitter -> emitter.act(dt));
	}
}

