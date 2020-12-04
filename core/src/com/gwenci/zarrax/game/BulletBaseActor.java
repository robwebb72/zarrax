package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.gwenci.zarrax.Animator;
import com.gwenci.zarrax.BaseActor;
import com.gwenci.zarrax.game.bullets.BulletType;


public class BulletBaseActor extends BaseActor {

	// TODO : SCREEN_HEIGHT hard coded here
//	private static final int SCREEN_HEIGHT = 768;

	private Vector2 delta_vec;
	private boolean inPlay;
	private Animator animator;

	Texture texture;

	BulletBaseActor() {}

	void initialise(BulletType bulletType) {
		this.texture = bulletType.getTexture();
		this.animator = bulletType.getAnimator();
		if(texture!=null) {
			setTexture(texture);
			int frameWidth = texture.getWidth() / animator.getNFrames();
			super.setWidth(frameWidth);
			super.setHeight(texture.getHeight());
		}

	}


	void fire(Vector2 location, Vector2 delta_vec) {
		if (!this.inPlay) {
			this.delta_vec = delta_vec;
			super.setPosition(location.x, location.y);
			this.inPlay = true;
		}
	}

	void setTexture(Texture texture) {
		this.texture = texture;
		super.setBoundingRect(texture.getWidth(), texture.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (this.inPlay) {
			super.draw(batch, parentAlpha);
			batch.draw(texture, getX(), getY(), animator.getCurrentFrame() *  (int) getWidth(), 0, (int) getWidth(),
					(int) getHeight() );		}
	}

	@Override
	public void act(float dtInSeconds) {
		if (this.inPlay) {
			animator.update(dtInSeconds);
			Vector2 temp_vec = new Vector2(delta_vec).scl(dtInSeconds);
			super.moveBy(temp_vec.x, temp_vec.y);
			this.inPlay = getY() > 0 && getY() < SCREEN_HEIGHT;
		}
	}


	boolean isInPlay() {
		return inPlay;
	}

	void removeFromPlay() {inPlay = false; }
}




