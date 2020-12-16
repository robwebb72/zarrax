package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameFont {

	private static GameFont instance = new GameFont();
	BitmapFont customFont;
	private GameFont() {
		FreeTypeFontGenerator fontGenerator =
				new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P-vaV7.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameters.size = 16;
		fontParameters.color = Color.WHITE;

		customFont = fontGenerator.generateFont(fontParameters);
	}

	public static GameFont getInstance() {
		return instance;
	}
	public BitmapFont getFont() {
		return customFont;
	}
}
