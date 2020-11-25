package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gwenci.zarrax.*;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

import java.util.*;


public class GameScreen extends BaseScreen {

	private SpriteBatch batch = Zarrax.getSpriteBatch();

	private PlayerScore playerScore;

	private Starfield stars;
	private FrameRate framerate;

	private PlayerBullets playerBullets;
	private AlienBullets alienBullets;

	private Stage playerStage;
	private PlayerActor player;

	private AlienWrangler aliens;
	private ParticleFoundry particleFoundry;
	private BitmapFont font;

	private List<Updatable> updatables;

	@Override
	public void initialize() {
		stars = Starfield.getInstance();
		playerStage = new Stage(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		alienBullets = new AlienBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		player = new PlayerActor(playerBullets);
		playerStage.addActor(player);
		aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch(),alienBullets);
		framerate = new FrameRate();
		framerate.setDisplay(true);
		particleFoundry = ParticleFoundry.getInstance();
		font = GameFont.getInstance().getFont();
		playerScore = new PlayerScore(0.01f);  // the displayScore counts up by 1 every 0.01s up to the value of the score

		setUpUpdatables();
	}

	private void setUpUpdatables() {
		updatables = new ArrayList<>();
		updatables.add(aliens);
		updatables.add(playerScore);
		updatables.add(stars);
		updatables.add(playerBullets);
		updatables.add(alienBullets);
		updatables.add(particleFoundry);

	}

	private boolean muted = false;

	@Override
	public void update(float dt) {

		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			aliens.killAllAliens(playerScore);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			framerate.flipDisplay();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			muted = !muted;
			SoundSystem.getInstance().setMute(muted);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			Gdx.app.exit();
		}

		player.act(dt);

		updatables.forEach(update -> update.update(dt));
		aliens.handleCollisions(playerBullets.getActiveBullets(), playerScore);

		framerate.update();
	}

	@Override
	public void render() {
		batch.begin();
		stars.render(batch);
		framerate.render(batch);
		particleFoundry.render(batch);
		font.draw(batch, String.format("%08d",playerScore.getDisplayScore()) , 275, 768- 3);
		if(muted) font.draw(batch, "muted" , 570, 43);
	//	font.draw(batch,"hi 00000700" , 4, 768- 3);
		batch.end();
		playerBullets.draw();
		alienBullets.draw();
		playerStage.draw();
		aliens.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {
		playerBullets.dispose();
		TextureManager.getInstance().dispose();
		AudioManager.getInstance().dispose();
	}
}

