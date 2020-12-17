package com.gwenci.zarrax.particle_system;

import com.badlogic.gdx.math.Vector2;


public class ParticleEffectPlayerExplosion extends EmitterType {

    public ParticleEffectPlayerExplosion() {

        particleTypes.clear();

        particleTypes.add (new ParticleType(
                100,
                ParticleFunctionLibrary.lifeSupplier.apply(0.4f, 0.6f),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_COL_1.apply(life_left_pc, ParticleColours.RED),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_SIZE_1.apply(life_left_pc, 2),
                ParticleFunctionLibrary.ALIEN_EXP_MOVE_01.apply(100.0f),
                () -> new Vector2(0.0f, 0.0f),
                new Vector2(0.0f, 0.0f)
        ));

        particleTypes.add (new ParticleType(
                100,
                ParticleFunctionLibrary.lifeSupplier.apply(0.2f, 0.4f),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_COL_1.apply(life_left_pc, ParticleColours.YELLOW),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_SIZE_1.apply(life_left_pc, 2),
                ParticleFunctionLibrary.ALIEN_EXP_MOVE_01.apply(40.0f),
                () -> new Vector2(0.0f, 0.0f),
                new Vector2(0.0f, 0.0f)
        ));

        particleTypes.add (new ParticleType(
                80,
                ParticleFunctionLibrary.lifeSupplier.apply(0.6f, 0.8f),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_COL_1.apply(life_left_pc, ParticleColours.WHITE),
                life_left_pc -> ParticleFunctionLibrary.ALIEN_EXP_SIZE_1.apply(life_left_pc, 2),
                ParticleFunctionLibrary.ALIEN_EXP_MOVE_01.apply(2000.0f),
                () -> new Vector2(0.0f, 0.0f),
                new Vector2(0.0f, 0.0f)
        ));

        particleTypes.add (new ParticleType(
                20,
                ParticleFunctionLibrary.lifeSupplier.apply(0.4f, 0.6f),
                life_left_pc -> ParticleColours.WHITE,
                life_left_pc -> 6,
                ParticleFunctionLibrary.ALIEN_EXP_MOVE_01.apply(500.0f),
                () -> new Vector2(0.0f, 0.0f),
                new Vector2(0.0f, 0.0f)
        ));

        particleTypes.add (new ParticleType(
                5,
                ParticleFunctionLibrary.lifeSupplier.apply(0.4f, 0.6f),
                life_left_pc -> ParticleColours.RED,
                life_left_pc -> 6,
                ParticleFunctionLibrary.ALIEN_EXP_MOVE_01.apply(500.0f),
                () -> new Vector2(0.0f, 0.0f),
                new Vector2(0.0f, 0.0f)
        ));


    }
}
