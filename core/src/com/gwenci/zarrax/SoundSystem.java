package com.gwenci.zarrax;

import com.badlogic.gdx.audio.Sound;

public class SoundSystem {

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
	public void play(Sound sound, float volume, float pitch, float pan) {
		if (!this.mute) sound.play(volume * this.globalVolume, pitch, pan);

	}

}
