package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gwenci.zarrax.*;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.particle_system.ParticleFoundry;
import com.gwenci.zarrax.particle_system.PlayerExplosion;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

	private static final int MAX_ALIEN_BULLETS = 250;
	private SpriteBatch batch = Zarrax.getSpriteBatch();

	private PlayerScore playerScore;

	private Starfield starfield;
	private FrameRate framerate;

	private PlayerBullets playerBullets;
	private BulletManager alienBullets;

	private Stage playerStage;
	private PlayerActor player;

	private AlienWrangler aliens;
	private ParticleFoundry particleFoundry;
	private BitmapFont font;

	private List<Updatable> updatables;
	private GameState gameState;

	enum GameState {
		LEVEL_START,
		LEVEL_END,
		PLAYER_START,
		GAME_LOOP,
		PLAYER_DIED,
		GAME_OVER
	}



	@Override
	public void initialize() {
		starfield = Starfield.getInstance();
		particleFoundry = ParticleFoundry.getInstance();
		ParticleFoundry.getInstance().resetFoundry();
		framerate = new FrameRate();
		framerate.setDisplay(true);
		font = GameFont.getInstance().getFont();


		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		alienBullets = new BulletManager(MAX_ALIEN_BULLETS);



		gameState = GameState.LEVEL_START;

		// TODO: Could this be factored out?
		playerStage = new Stage(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		player = new PlayerActor(playerBullets);
		playerStage.addActor(player);

		playerScore = new PlayerScore(0.01f);  // the displayScore counts up by 1 every 0.01s up to the value of the score

		setUpUpdatables();
	}

	private void setUpUpdatables() {
		updatables = new ArrayList<>();
		updatables.add(playerScore);
		updatables.add(starfield);
		updatables.add(playerBullets);
		updatables.add(alienBullets);
		updatables.add(particleFoundry);

	}

	private boolean muted = false;
	private boolean paused = false;

	@Override
	public void update(float dt) {
		framerate.update();

		switch (gameState) {
			case LEVEL_START:
				aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch(),alienBullets);
				updatables.add(aliens);
				gameState = GameState.PLAYER_START;
				break;
			case PLAYER_START:
				gameState = GameState.GAME_LOOP;
				updateGameWorld(dt);
				handleInputs();
				break;
			case GAME_LOOP:
				updateGameWorld(dt);
				handleInputs();
				break;
			case PLAYER_DIED:
			case LEVEL_END:
				updatables.remove(aliens);
				gameState = GameState.LEVEL_START;
			case GAME_OVER:
		}

	}

	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) aliens.killAllAliens(playerScore);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			muted = !muted;
			SoundSystem.getInstance().setMute(muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();

			Zarrax.setActiveScreen(new AssetDisposer());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			paused = !paused;
		}

		// TEMPORARY HACK TO "FAKE" PLAYER EXPLOSION
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			particleFoundry.newEmitter(player, new PlayerExplosion());
		}

	}

	private void updateGameWorld(float dt) {
		if(aliens.noOfLiveAliens()==0)  {
			gameState = GameState.LEVEL_END;
			return;
		}
		if(!paused) {
			player.act(dt);

			updatables.forEach(update -> update.update(dt));
			aliens.handleCollisions(playerBullets.getActiveBullets(), playerScore);
		}
	}

	@Override
	public void render() {
		batch.begin();
		starfield.render(batch);
		framerate.render(batch);
		particleFoundry.render(batch);
		font.draw(batch, String.format("%08d",playerScore.getDisplayScore()) , 275, 768- 3);
		if(muted) font.draw(batch, "muted" , 570, 43);
		if(paused) font.draw(batch, "paused" , 300, 400);
	//	font.draw(batch,"hi 00000700" , 4, 768- 3);
		playerBullets.getActiveBullets().forEach(b -> b.draw(batch));
		alienBullets.getActiveBullets().forEach(b->b.draw(batch));
		batch.end();

		// TODO: Need to make the drawing method consistent - have all items draw in the same batch
		playerStage.draw();
		aliens.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {
//		playerBullets.dispose();

	}
}

