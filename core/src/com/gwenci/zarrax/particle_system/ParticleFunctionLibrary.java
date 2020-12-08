package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

class ParticleFunctionLibrary {

	static final BiFunction<Float, Float, Supplier<Float>> lifeSupplier = (minLife, maxLife) ->
			() -> MathUtils.random(minLife, maxLife);

	static final BiFunction<Float, Integer, Integer> ALIEN_EXP_COL_1 = (time_pc, colour) -> {
		if (colour > 7) return colour;
		if (time_pc < 0.5f) return colour + 8;
		return colour;
	};

	static final BiFunction<Float, Integer, Integer> ALIEN_EXP_SIZE_1 = (time_pc, size) -> (time_pc < 0.25f) ? size / 2 : size;

	static final Function<Float, Vector2> RANDOM_CIRCULAR_UNIT_VECTOR = speed ->
			new Vector2().set(0.0f, MathUtils.random(speed * 0.01f, speed)).rotate(MathUtils.random(360.0f));

	static final Function<Float, Supplier<Vector2>> ALIEN_EXP_MOVE_01 = speed ->
			() -> RANDOM_CIRCULAR_UNIT_VECTOR.apply(speed);

	static final Function <Float, Supplier<Vector2>> PLUME_MOVE_VECTOR = speed ->
			() -> new Vector2(0.0f, -MathUtils.random(speed * 0.5f,speed));

}
