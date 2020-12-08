package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.Updatable;

import java.util.Arrays;


public class ParticleFoundry implements Updatable {

	private static final int MAX_EMITTERS = 60;
	static final int MAX_PARTICLES_PER_EMITTER = 300;
	private static final int MAX_PARTICLES = MAX_PARTICLES_PER_EMITTER * MAX_EMITTERS;

	private static final ParticleFoundry instance = new ParticleFoundry();
	private final Particle[] particles = new Particle[MAX_PARTICLES];
	private final ParticleEmitter[] particleEmitters = new ParticleEmitter[MAX_EMITTERS];
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
		for (int i = 0; i < MAX_EMITTERS; i++) {
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


	public void resetFoundry() {
		for(ParticleEmitter pe : particleEmitters) pe.reset();
	}

	public ParticleEmitter newEmitter(ILocation loc, EmitterType type) {
		ParticleEmitter emitter = getNextEmitter();

		if (emitter==null) return null;
		emitter.initialize(loc, type); //, velocity_func, colour_func, speed_func);
		return emitter;

	}


	private ParticleEmitter getNextEmitter() {
		int originalIndex = emitterIndex;
		do {
			emitterIndex++;
			if (emitterIndex == MAX_EMITTERS)
				emitterIndex = 0;
			if (particleEmitters[emitterIndex].isAvailable())
				return particleEmitters[emitterIndex];
		} while (emitterIndex != originalIndex);
		return null;
	}


	public void render(SpriteBatch batch) {
		Arrays.stream(particleEmitters).forEach(emitter -> emitter.render(batch));
	}


	@Override
	public void update(float dt) {
		Arrays.stream(particleEmitters).parallel().filter(ParticleEmitter::isActive).forEach(emitter -> emitter.act(dt));
	}
}

