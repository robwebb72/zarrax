package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.math.Vector2;

public class ParticleEffectPlayerShield extends EmitterType {

	private static final float SHIELD_SIZE = 26.0f;

	public ParticleEffectPlayerShield() {

		particleTypes.clear();
		nParticles = 300;


		particleTypes.add(new ParticleType(
				150,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				life_left_pc -> ParticleColours.WHITE,
				life_left_pc -> 2,
				() -> new Vector2(0.0f,0.0f),
				ParticleFunctionLibrary.CIRCULAR_UNIT_VECTOR.apply(SHIELD_SIZE),
				new Vector2(0.0f, 0.0f)
		));

		particleTypes.add(new ParticleType(
				80,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				life_left_pc -> ParticleColours.CYAN,
				life_left_pc -> 2,
				() -> new Vector2(0.0f,0.0f),
				ParticleFunctionLibrary.CIRCULAR_UNIT_VECTOR.apply(SHIELD_SIZE),
				new Vector2(0.0f, 0.0f)
		));

		particleTypes.add(new ParticleType(
				40,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				life_left_pc -> ParticleColours.DULL_WHITE,
				life_left_pc -> 2,
				() -> new Vector2(0.0f,0.0f),
				ParticleFunctionLibrary.CIRCULAR_UNIT_VECTOR.apply(SHIELD_SIZE - 2.0f),
				new Vector2(0.0f, 0.0f)
		));
		particleTypes.add(new ParticleType(
				20,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				life_left_pc -> ParticleColours.DULL_CYAN,
				life_left_pc -> 2,
				() -> new Vector2(0.0f,0.0f),
				ParticleFunctionLibrary.CIRCULAR_UNIT_VECTOR.apply(SHIELD_SIZE - 4.0f),
				new Vector2(0.0f, 0.0f)
		));
		particleTypes.add(new ParticleType(
				10,
				ParticleFunctionLibrary.lifeSupplier.apply(0.02f, 0.05f),
				life_left_pc -> ParticleColours.DULL_CYAN,
				life_left_pc -> 2,
				() -> new Vector2(0.0f,0.0f),
				ParticleFunctionLibrary.CIRCULAR_UNIT_VECTOR.apply(SHIELD_SIZE - 6.0f),
				new Vector2(0.0f, 0.0f)
		));

	}

		public boolean isInfinite() {
			return true;
		}
}
