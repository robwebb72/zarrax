package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gwenci.zarrax.*;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.particle_system.ParticleFoundry;
import com.gwenci.zarrax.particle_system.ParticleEffectPlayerExplosion;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

	private static final int MAX_ALIEN_BULLETS = 250;
	private SpriteBatch batch = Zarrax.getSpriteBatch();
	private static final int SCREEN_WIDTH = 672;

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
				player.setPosition( SCREEN_WIDTH / 2.0f - 16f, 25f);
				player.setIsAlive(true);
				player.setShieldsOn(3.0f);
				gameState = GameState.GAME_LOOP;
				break;
			case GAME_LOOP:
				updateGameWorld(dt);
				handleInputs();
				break;
			case PLAYER_DIED:
				updatePlayerDied(dt);
				updateGameWorld(dt);
				handleInputs();
				break;
			case LEVEL_END:
				updatables.remove(aliens);
				playerBullets.reset();
				alienBullets.reset();
				gameState = GameState.LEVEL_START;
			case GAME_OVER:
		}

	}

	float playerDiedTimer = 1.0f;
	private void updatePlayerDied(float dt) {
		if (playerDiedTimer >= 0.0f) {
			playerDiedTimer -= dt;
		} else {
			// if player lives > 0 ... and so on...
			changeState(GameState.PLAYER_START);
			playerDiedTimer = 1.0f;
		}
	}

	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) aliens.killAllAliens(playerScore);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) changeState(GameState.PLAYER_DIED);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) paused = !paused;
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) player.setShieldsOn(3.0f);


		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			muted = !muted;
			SoundSystem.getInstance().setMute(muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			Zarrax.setActiveScreen(new AssetDisposer());
		}


		// TEMPORARY HACK TO "FAKE" PLAYER EXPLOSION
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			particleFoundry.newEmitter(player, new ParticleEffectPlayerExplosion());
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

		// background
		starfield.render(batch);

		// game world
		playerBullets.getActiveBullets().forEach(b -> b.draw(batch));
		alienBullets.getActiveBullets().forEach(b->b.draw(batch));
		particleFoundry.render(batch);
		// TODO: Aliens and Player need to be drawn here

		// gui
		framerate.render(batch);
		font.draw(batch, String.format("%08d",playerScore.getDisplayScore()) , 275, 768- 3);
		if(muted) font.draw(batch, "muted" , 570, 43);
		if(paused) font.draw(batch, "paused" , 300, 400);
	//	font.draw(batch,"hi 00000700" , 4, 768- 3);
		batch.end();

		// TODO: Need to make the drawing method consistent - have all items draw in the same batch
		playerStage.draw();
		aliens.draw();
	}

	@Override
	public void resize(int width, int height) {

	}


	public void changeState(GameState state) {
		if (this.gameState==state) return;
		this.gameState = state;

		switch(state){
			case PLAYER_DIED:
				setUpPlayerDied();
				break;
			default:
				break;
		}
	}


	private void setUpPlayerDied() {
		// TODO: Player Lives - 1
		// TODO: Screen shake
		// TODO: Play Explosion
		// TODO: Print "Gotcha!" message
		player.setIsAlive(false);

		particleFoundry.newEmitter(player, new ParticleEffectPlayerExplosion());
	}

	@Override
	public void dispose() {

	}
}

