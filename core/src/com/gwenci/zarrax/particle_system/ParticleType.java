package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.math.Vector2;

import java.util.function.Function;
import java.util.function.Supplier;

class ParticleType {

	int particleCount;
	Supplier<Float> lifeTime;                // the lifetime of the particle, set on initialisation and not changed
	Supplier<Vector2> moveFunc;              // the move vector of the particle, set on initialisation and not changed
	Function<Float, Integer> colourFunc;     // the colour of the particle, reevaluated during the lifetime of the object
	Function<Float, Integer> sizeFunc;       // the size of the particle, reevaluated during the lifetime of the object
	Vector2 offset;                          // an offset vector applied to a particle's initial starting location

	ParticleType(int particleCount,
				 Supplier<Float> lifeTime,
				 Function<Float, Integer> colourFunc,
				 Function<Float, Integer> sizeFunc,
				 Supplier<Vector2> moveFunc,
				 Vector2 offset
	) {
		this.particleCount = particleCount;
		this.lifeTime = lifeTime;
		this.moveFunc = moveFunc;
		this.colourFunc = colourFunc;
		this.sizeFunc = sizeFunc;
		this.offset = offset;
	}
}
