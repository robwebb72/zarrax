package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends BaseScreen {

	private SpriteBatch batch = Zarrax.getSpriteBatch();

	private Starfield stars;
	private FrameRate framerate;

	private PlayerBullets playerBullets;

	private Stage playerStage;
	private PlayerActor player;

	private AlienWrangler aliens;
	private ParticleFoundry particleFoundry;

	@Override
	public void initialize() {

		stars = Starfield.getInstance();
		playerStage = new Stage(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		player = new PlayerActor();
		playerStage.addActor(player);
		aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		framerate = new FrameRate();
		particleFoundry = ParticleFoundry.getInstance();
	}

	@Override
	public void update(float dt) {

		stars.update(dt);
		player.act(dt);
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			playerBullets.fireBullets(player.getX(), player.getY());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			particleFoundry.newEmitter(300, 300);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			aliens.killAllAliens();
		}
		playerBullets.act(dt);
		aliens.act(dt);
		particleFoundry.act(dt);
		aliens.handleCollisions(playerBullets.getList());
		framerate.update();
	}

	@Override
	public void render() {
		batch.begin();
		stars.render(batch);
		framerate.render(batch);
		particleFoundry.render(batch);
		batch.end();
		playerBullets.draw();
		playerStage.draw();
		aliens.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {
		playerBullets.dispose();

	}
}

