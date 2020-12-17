package com.gwenci.zarrax;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class SoundSystem {

	private static final int SCREEN_WIDTH = 672;
	public static final int HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;
	private static final SoundSystem instance = new SoundSystem();
	private float globalVolume = 1.0f;
	private boolean mute = false;
	private SoundSystem() {}

	public static SoundSystem getInstance() {
		return instance;
	}

	public void setGlobalVolume(float volume) {
		this.globalVolume = volume;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public void play(Sound sound, ILocation location, float volume, float pitchVariance) {
		if (!this.mute) {
			float panning = (location.getX() - HALF_SCREEN_WIDTH)/HALF_SCREEN_WIDTH;
			float pitch = MathUtils.random(1.0f - pitchVariance, 1.0f + pitchVariance);
			sound.play(volume * this.globalVolume, pitch,panning);
		}

	}
}
