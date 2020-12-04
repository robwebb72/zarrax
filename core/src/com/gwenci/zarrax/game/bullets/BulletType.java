package com.gwenci.zarrax.game.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.gwenci.zarrax.Animator;

public class BulletType {

	private final Texture texture;
	private final Animator animator;
	private final float speed;

	public Sound getFireSound() {
		return fireSound;
	}

	private final Sound fireSound;

	public BulletType(Texture texture, Animator animator, float speed, Sound fireSound) {
		this.texture = texture;
		this.animator = animator;
		this.speed = speed;
		this.fireSound = fireSound;

	}

	public Texture getTexture() {
		return texture;
	}

	public Animator getAnimator() {
		return animator;
	}

	public float getSpeed() {
		return speed;
	}
}
