package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * A nicer class for showing frame rate that doesn't spam the console
 * like Logger.log()
 *
 * @author William Hartman
 */
public class FrameRate implements Disposable{
	private long lastTimeCounted;
	private float sinceChange;
	private float frameRate;
	private BitmapFont font;


	FrameRate() {
		lastTimeCounted = TimeUtils.millis();
		sinceChange = 0;
		frameRate = Gdx.graphics.getFramesPerSecond();
		font = new BitmapFont();
	}

	void update() {
		long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
		lastTimeCounted = TimeUtils.millis();

		sinceChange += delta;
		if(sinceChange >= 1000) {
			sinceChange = 0;
			frameRate = Gdx.graphics.getFramesPerSecond();
		}
	}

	void render(SpriteBatch batch) {
		font.draw(batch, (int)frameRate + " fps", 3, 768- 3);
	}

	public void dispose() {
		font.dispose();
	}
}