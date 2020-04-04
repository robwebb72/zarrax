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
	private AlienBullets alienBullets;

	private Stage playerStage;
	private PlayerActor player;

	private AlienWrangler aliens;
	private ParticleFoundry particleFoundry;
	private int score = 0;
	private int displayScore = 0;
	private static final float scoreUpdateRate = 0.01f;   // the displayScore counts up by 1 every 0.01s up to the value of the score
	private float lastScoreUpdate = 0;
	private BitmapFont font;

	@Override
	public void updateScore(int dScore) {
		score += dScore;
	}

	@Override
	public void initialize() {

		stars = Starfield.getInstance();
		playerStage = new Stage(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		alienBullets = new AlienBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		player = new PlayerActor();
		playerStage.addActor(player);
		aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch(),alienBullets);
		framerate = new FrameRate();
		framerate.setDisplay(true);
		particleFoundry = ParticleFoundry.getInstance();
		score = 0;
		font = GameFont.getInstance().getFont();
	}

	private boolean spacePressed= false;
	private boolean fPressed= false;

	@Override
	public void update(float dt) {


		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if(!spacePressed) {
				playerBullets.fireBullet(player.getX(), player.getY());
				spacePressed = true;
			}
		}
		else spacePressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			aliens.killAllAliens(this);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			if(!fPressed) {
				framerate.flipDisplay();
				fPressed = true;
			}
		}
		else fPressed = false;
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			Gdx.app.exit();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.P)) {
			alienBullets.fireBullet((float) Math.random()*600,700,100.0f - (float) Math.random()*200,0);
		}
		player.act(dt);
		playerBullets.act(dt);
		alienBullets.act(dt);

		stars.update(dt);
		particleFoundry.act(dt);

		aliens.act(dt);
		aliens.handleCollisions(playerBullets.getActiveBullets(), this);

		lastScoreUpdate -= dt;
		if(lastScoreUpdate<0.0f) {
			if(displayScore<score) displayScore++;
			lastScoreUpdate = scoreUpdateRate;
		}

		framerate.update();
	}

	@Override
	public void render() {
		batch.begin();
		stars.render(batch);
		framerate.render(batch);
		particleFoundry.render(batch);
		font.draw(batch, String.format("%08d",displayScore) , 275, 768- 3);
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

