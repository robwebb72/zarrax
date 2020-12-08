package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.gwenci.zarrax.TextureManager;

import java.util.ArrayList;


public class ParticleEmitter {

	private final ArrayList<Particle> particles = new ArrayList<>();
	private final Texture texture = TextureManager.getInstance().get("assets/spectrum.png");
	ArrayList<ParticleType> particleTypes = new ArrayList<>();
	int particleRange;
	int nParticles;
	private boolean isActive = false;
	private boolean reserved = false;
	private boolean infinite = false;
	private boolean isOn = true;

	private ILocation location;

	void addParticle(Particle particle) {
		particles.add(particle);
		particle.setEmitter(this);
	}

	void particleHasDied(Particle p) {
		if (infinite) p.initialise(this.location.location(), getParticleType(), texture);
	}

	void reset() {
		isActive = false;
		infinite = false;
		particleTypes.clear();
		for(Particle part: particles) part.reset();
	}

	void initialize(ILocation location, EmitterType type) {

		particleTypes.clear();
		nParticles = type.nParticles;
		if (nParticles > ParticleFoundry.MAX_PARTICLES_PER_EMITTER) nParticles = ParticleFoundry.MAX_PARTICLES_PER_EMITTER;
		particleTypes = type.getParticleTypes();
		initialize(location);
		infinite = type.isInfinite();
	}


	void initialize(ILocation location) {
		this.location = location;
		particleRange = 0;
		for (ParticleType pt : particleTypes) particleRange += pt.particleCount;

		if (particleRange == 0) return;
		for(int i= 0; i< nParticles; i++) {
			particles.get(i).initialise(this.location.location(), getParticleType(), texture);
		}
		isActive = true;
	}


	private ParticleType getParticleType() {
		int rangeCounter = MathUtils.random(particleRange);

		for (ParticleType pt : particleTypes) {
			rangeCounter -= pt.particleCount;
			if (rangeCounter < 0) return pt;
		}
		return particleTypes.get(0);
	}

	public void setOn(boolean onState) {
		isOn= onState;
	}

	boolean isAvailable() {
		return !isActive && !reserved;
	}

	boolean isActive() {
		return isActive;
	}


	void render(SpriteBatch batch) {
		if (isActive && isOn) {
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
