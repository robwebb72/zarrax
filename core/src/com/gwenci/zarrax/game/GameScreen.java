package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.*;
import com.gwenci.zarrax.asset_handler.AssetDisposer;
import com.gwenci.zarrax.particle_system.ParticleFoundry;
import com.gwenci.zarrax.particle_system.ParticleEffectPlayerExplosion;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

	private final SpriteBatch batch = Zarrax.getSpriteBatch();
	private static final int SCREEN_WIDTH = 672;

	private PlayerScore playerScore;
	private BitmapFont font;

	private Starfield starfield;
	private FrameRate framerate;

	private ParticleFoundry particleFoundry;

	private List<Updatable> updatables;
	private GameState gameState;
	private GameWorld gameWorld;

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
		playerScore = new PlayerScore(0.01f);  // the displayScore counts up by 1 every 0.01s up to the value of the score

		gameWorld = new GameWorld();
		gameWorld.initialise();

		gameState = GameState.LEVEL_START;
		setUpUpdatables();
	}

	private void setUpUpdatables() {
		updatables = new ArrayList<>();
		// GUI
		updatables.add(playerScore);

		// Background
		updatables.add(starfield);
		updatables.add(particleFoundry);

		// GameWorld
		updatables.add(gameWorld.playerBullets);
		updatables.add(gameWorld.alienBullets);

	}

	private boolean muted = false;
	private boolean paused = false;

	@Override
	public void update(float dt) {
		framerate.update();

		switch (gameState) {
			case LEVEL_START:
				gameWorld.setUpAliens();


				updatables.add(gameWorld.aliens);
				gameState = GameState.PLAYER_START;
				break;
			case PLAYER_START:
				gameWorld.playerActor.setPosition( SCREEN_WIDTH / 2.0f - 16f, 25f);
				gameWorld.playerActor.setIsAlive(true);
				gameWorld.playerActor.setShieldsOn(3.0f);
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
				updatables.remove(gameWorld.aliens);
				gameWorld.playerBullets.reset();
				gameWorld.alienBullets.reset();
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
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) gameWorld.aliens.killAllAliens(playerScore);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) framerate.flipDisplay();
		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) changeState(GameState.PLAYER_DIED);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) paused = !paused;
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) gameWorld.playerActor.setShieldsOn(3.0f);


		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			muted = !muted;
			SoundSystem.getInstance().setMute(muted);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			Zarrax.setActiveScreen(new AssetDisposer());
		}

	}

	private void updateGameWorld(float dt) {
		if(gameWorld.aliens.noOfLiveAliens()==0)  {
			gameState = GameState.LEVEL_END;
			return;
		}
		if(!paused) {
			gameWorld.playerActor.act(dt);

			updatables.forEach(update -> update.update(dt));
			gameWorld.aliens.handleCollisions(gameWorld.playerBullets.getActiveBullets(), playerScore);
		}
	}

	@Override
	public void render() {
		batch.begin();

		// background
		starfield.render(batch);

		// game world
		gameWorld.playerBullets.getActiveBullets().forEach(b -> b.draw(batch));
		gameWorld.alienBullets.getActiveBullets().forEach(b->b.draw(batch));
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
		gameWorld.playerActor.draw();
		gameWorld.aliens.draw();
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
		gameWorld.playerActor.setIsAlive(false);

		particleFoundry.newEmitter(gameWorld.playerActor, new ParticleEffectPlayerExplosion());
	}

	@Override
	public void dispose() {

	}
}

