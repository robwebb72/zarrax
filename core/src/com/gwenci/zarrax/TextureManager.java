package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;


public class TextureManager {

	private Map<String,Texture> textures = new HashMap<>();
	private static TextureManager instance = new TextureManager();

	private TextureManager() {}

	public static TextureManager getInstance() {
		return instance;
	}

	public Texture get(String textureName) {
		if (textures.containsKey(textureName)) {
			return textures.get(textureName);
		}

		Texture texture = new Texture(Gdx.files.internal(textureName));
		textures.put(textureName,texture);
		return texture;
	}

	public void dispose() {
		textures.values().forEach(Texture::dispose);
		textures.clear();
	}
}
