package com.gwenci.zarrax.particle_system;

import java.util.ArrayList;


public class EmitterType {
	ArrayList<ParticleType> particleTypes;
	int nParticles = 300;

	EmitterType() {
		particleTypes = new ArrayList<>();
	}
	void addParticleType(ParticleType particleType) {
		particleTypes.add(particleType);
	}

	public ArrayList<ParticleType> getParticleTypes() {
		return particleTypes;
	}

	public int getNParticles() {
		return nParticles;
	}

	public boolean isInfinite() {
		return false;
	}

}
