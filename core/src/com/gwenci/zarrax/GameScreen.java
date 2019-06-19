package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class GameScreen extends BaseScreen implements PlayerScore {

	private SpriteBatch batch = Zarrax.getSpriteBatch();

	private Starfield stars;
	private FrameRate framerate;

	private PlayerBullets playerBullets;

	private Stage playerStage;
	private PlayerActor player;

	private AlienWrangler aliens;
	private ParticleFoundry particleFoundry;
	private int score = 0;
	private BitmapFont font = new BitmapFont();

	@Override
	public void updateScore(int dScore) {
		score += dScore;
	}

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
		score = 0;
	}

	private boolean spacePressed= false;
	@Override
	public void update(float dt) {

		stars.update(dt);
		player.act(dt);

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (!spacePressed) {
				playerBullets.fireBullets(player.getX(), player.getY());
			}
			spacePressed = true;
		} else spacePressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			particleFoundry.newEmitter(300, 300);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			aliens.killAllAliens(this);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		playerBullets.act(dt);
		aliens.act(dt);
		particleFoundry.act(dt);
		aliens.handleCollisions(playerBullets.getList(), this);
		framerate.update();
	}

	@Override
	public void render() {
		batch.begin();
		stars.render(batch);
		framerate.render(batch);
		particleFoundry.render(batch);
		font.draw(batch, String.format("%08d",score) , 300, 768- 3);
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

