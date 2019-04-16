package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class AlienActor extends BaseActor {
	private Texture alienTexture;
	private float animFrameRate;
	private int animFrame = 0;
	private AlienState state = AlienState.DEAD;

	AlienActor(Texture texture) {
		this.alienTexture = texture;
		setPosition(SCREEN_WIDTH / 2.0f - 16f, 300f);
		setBoundingRect(texture.getWidth()/3, texture.getHeight());
	}

	void setState(AlienState state) {
		this.state = state;
	}

	@Override
	public void act(float dt) {
		if (state != AlienState.ALIVE) return;
		super.act(dt);
		animFrameRate += dt;
		if (animFrameRate > 0.2) {
			animFrameRate = 0;
			animFrame--;
			if(animFrame <0) animFrame = 2;
		}
	}

	boolean isAlive() {
		return state == AlienState.ALIVE;
	}

	float getCentreX() {
		int textureHalfWidth = this.alienTexture.getWidth() / 2;
		return getX() + textureHalfWidth;
	}

	float getCentreY() {
		int textureHalfHeight = this.alienTexture.getHeight() / 2;
		return getY() + textureHalfHeight;
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, super.getX(), super.getY(), animFrame * 22, 0, 22, 16);
	}
}
