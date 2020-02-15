package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;


public class AudioManager {

	private HashMap<String,Sound> sounds = new HashMap<>();
	private static AudioManager instance = new AudioManager();

	private AudioManager() {}

	public static AudioManager getInstance() {
		return instance;
	}

	public Sound get(String soundName) {
		if (sounds.containsKey(soundName)) {
			return sounds.get(soundName);
		}

		Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundName));
		sounds.put(soundName, sound);
		return sound;
	}

	public void dispose() {
		sounds.values().forEach(
				s -> { s.stop(); s.dispose(); }
		);
		sounds.clear();
	}
}
