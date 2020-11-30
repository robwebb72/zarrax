package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.math.Vector2;

import java.util.function.Function;

public class JetPlumeParticles extends EmitterType {

	public JetPlumeParticles() {
		particleTypes.clear();

		nParticles = 50;

		particleTypes.add (new ParticleType(
				2,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				time_pc -> ParticleColours.YELLOW,
				time_pc -> 2,
				ParticleFunctionLibrary.PLUME_MOVE_VECTOR.apply(375.0f),
				new Vector2(1.0f,
						0.0f)
		));

		particleTypes.add (new ParticleType(
				1,
				ParticleFunctionLibrary.lifeSupplier.apply(0.04f, 0.06f),
				time_pc -> ParticleColours.RED,
				time_pc ->(time_pc < 0.9f) ? 2 : 1,
				ParticleFunctionLibrary.PLUME_MOVE_VECTOR.apply(750.0f),
				new Vector2(1.0f,
						0.0f)
		));

		particleTypes.add (new ParticleType(
				1,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.04f),
				time_pc -> ParticleColours.RED,
				time_pc -> 2,
				ParticleFunctionLibrary.PLUME_MOVE_VECTOR.apply(300.0f),
				new Vector2(0.0f,
						0.0f)
		));

		particleTypes.add (new ParticleType(
				1,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				time_pc -> ParticleColours.RED,
				time_pc -> 2,
				ParticleFunctionLibrary.PLUME_MOVE_VECTOR.apply(300.0f),
				new Vector2(3.0f,
						0.0f)
		));

	}

	public boolean isInfinite() {
		return true;
	}

}
