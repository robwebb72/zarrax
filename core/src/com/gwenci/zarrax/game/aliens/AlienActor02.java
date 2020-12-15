package com.gwenci.zarrax.game.aliens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.game.bullets.BulletType;
import com.gwenci.zarrax.game.bullets.BulletTypeFactory;
import com.gwenci.zarrax.particle_system.ParticleEffectAlienExplosion01;
import com.gwenci.zarrax.particle_system.EmitterType;
import com.gwenci.zarrax.particle_system.ParticleColours;

public class AlienActor02 extends BaseAlien {

	public AlienActor02(Texture texture) {
		super(texture, 3, 0.25f);
		bulletType = BulletTypeFactory.createBulletType(BulletTypeFactory.BulletTypeID.TYPE01);

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
		return new ParticleEffectAlienExplosion01(ParticleColours.GREEN);
	}
}