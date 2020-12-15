package com.gwenci.zarrax.game.bullets;

import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.AudioManager;
import com.gwenci.zarrax.TextureManager;

public class BulletTypeFactory {

	public enum BulletTypeID {
		TYPE01,
		TYPE02
	}

	public static BulletType createBulletType(BulletTypeID type) {
		switch(type) {
			case TYPE01:
				return new BulletType(
						TextureManager.getInstance().get("assets/alien_bullet.png"),
						new Animator(2, 0.2f),
						250.0f,
						AudioManager.getInstance().get("assets/sfx/alien_fire_1.wav")
				);
			case TYPE02:
				return new BulletType(
						TextureManager.getInstance().get("assets/alien_bullet2.png"),
						new Animator(2, 0.2f),
						350f,
						AudioManager.getInstance().get("assets/sfx/alien_fire_2.wav")
				);
		}
		return null;
	}

}


