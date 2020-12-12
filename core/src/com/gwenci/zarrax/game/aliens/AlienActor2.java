package com.gwenci.zarrax.game.aliens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.bullets.BulletType;
import com.gwenci.zarrax.particle_system.ParticleEffectAlienExplosion01;
import com.gwenci.zarrax.particle_system.EmitterType;
import com.gwenci.zarrax.particle_system.ParticleColours;

public class AlienActor2 extends BaseAlien {

	public AlienActor2(Texture texture) {
		super(texture, 3, 0.25f);
		bulletType = new BulletType(
				TextureManager.getInstance().get("assets/alien_bullet2.png"),
				new Animator(2, 0.2f),
				350f,
				AudioManager.getInstance().get("assets/sfx/alien_fire_2.wav")

		);
	}

	@Override
	public int getScore() {
		return 20;
	}

	@Override
	public boolean canFire() {
		return TimeUtils.timeSinceMillis(lastTimeFired) > 100;
	}

	@Override
	public boolean isFiring(float chanceToFire) {
		boolean firing = (MathUtils.random(1.0f) < chanceToFire) && canFire();

		if (firing) {
			lastTimeFired = TimeUtils.millis();
		}
		return firing;
	}

	@Override
	public EmitterType particleExplosion() {
		return new ParticleEffectAlienExplosion01(ParticleColours.MAGENTA);
	}
}