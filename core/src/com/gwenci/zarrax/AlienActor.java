package com.gwenci.zarrax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class AlienActor extends BaseActor {
	private Texture alienTexture;

	private AlienState state = AlienState.DEAD;

	AlienActor(Texture texture) {
		this.alienTexture = texture;
		setPosition( SCREEN_WIDTH / 2.0f - 16f, 300f);
		setBoundingRect(texture.getWidth(),texture.getHeight());
	}

	void setState(AlienState state) {
		this.state = state;
	}

	@Override
	public void act(float dt) {
		if (state != AlienState.ALIVE) return;
		super.act(dt);
	}

	private void checkBounds() {
		float x = super.getX();
		float y = super.getY();
		x = Math.max(x, 10f);
		x = Math.min(x, SCREEN_WIDTH-42f);
		y = Math.max(y, 5f);
		y = Math.min(y, SCREEN_HEIGHT-62f);
		super.setPosition(x, y);
	}

	boolean isAlive() {
		return state == AlienState.ALIVE;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != AlienState.ALIVE) return;
		super.draw(batch, parentAlpha);
		batch.draw(alienTexture, getX(), getY());
	}
}
