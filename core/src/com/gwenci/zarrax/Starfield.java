package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Starfield implements Updatable {

	private static final int NSTARS = 1000;

	// TODO: Screen Height and Width Hard coded

	private static final int SCREEN_WIDTH = 672;
	private static final int SCREEN_HEIGHT = 768;
	private static final Starfield instance = new Starfield();
	private final Star[] stars = new Star[NSTARS];

	// TODO: Do I need to dispose of these textures?
	private TextureRegion[] textures = new TextureRegion[8];

	private Starfield() {
		Texture texture = TextureManager.getInstance().get("stars.png");
		for (int i = 0; i < 8; i++) {
			textures[i] = new TextureRegion(texture, i, 0, 1, 1);
		}

		for (int i = 0; i < NSTARS; i++) {
			stars[i] = new Star();
		}
	}

	public static Starfield getInstance() {
		return instance;
	}

	@Override
	public void update(float dt) {
		for (int i = 0; i < NSTARS; i++) {
			stars[i].update(dt);
		}
	}

	public void render(SpriteBatch batch) {
		for (int i = 0; i < NSTARS; i++) {
			batch.draw(textures[stars[i].brightness], stars[i].x, stars[i].y);
		}
	}

	class Star {
		float x;
		float y;
		float speed;
		int brightness;

		Star() {
			x = MathUtils.random(SCREEN_WIDTH);
			y = MathUtils.random(SCREEN_HEIGHT);
			brightness = MathUtils.random(7);
			speed = newSpeed(brightness);
		}

		void update(float dt) {
			y -= speed * dt;
			if (y < 0) {
				x = MathUtils.random(SCREEN_WIDTH);
				y = SCREEN_HEIGHT;
				brightness = MathUtils.random(7);
				speed = newSpeed(brightness);
			}
		}

		private float newSpeed(int brightness) {
			// use brightness to adjust speed (darker stars are more likely to move slower)
			return (float) SCREEN_HEIGHT / MathUtils.random(1.0f, 3.0f) *    // takes 1-3 seconds for a star to traverse screen
					(float) (8 - brightness) / 8f;                             // apply inverse of brightness as a factor
		}

	}

}
