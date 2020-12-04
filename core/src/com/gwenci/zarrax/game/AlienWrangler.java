package com.gwenci.zarrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gwenci.zarrax.SoundSystem;
import com.gwenci.zarrax.TextureManager;
import com.gwenci.zarrax.Updatable;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AlienWrangler implements Updatable {

	private static final int MAX_ALIENS = 50;
	private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth()/2.0f;
	private static final Sound explosionSound;

	private final Stage stage;
	private final ParticleFoundry particleFoundry = ParticleFoundry.getInstance();
	private final BaseAlien[] aliens = new BaseAlien[MAX_ALIENS];
	private final AlienBullets alienBullets;

	private float swarmXTimer = 0.0f;
	private float chanceToFireMod = 1;

	static {
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/alienexpl.wav"));
	}

	AlienWrangler(Viewport vp, SpriteBatch batch, AlienBullets alienBullets) {
		stage = new Stage(vp,batch);
		for(int i = 0 ; i< MAX_ALIENS; i++) {
			if(i>=30) {
				aliens[i] = new AlienActor2(TextureManager.getInstance().get("assets/galaxian_2_1.png"));
			} else {
				aliens[i] = new AlienActor1(TextureManager.getInstance().get("assets/galaxian_1_1.png"));
			}
			stage.addActor(aliens[i]);
		}
		placeAliens();
		this.alienBullets = alienBullets;
		updateChanceToFire();
		// just the one alien, please..
//		aliens[0] = new AlienActor1(alien1texture);
//		aliens[0].setPosition(250, 575);
//		aliens[0].setState(AlienState.ALIVE);
//		stage.addActor(aliens[0]);
	}

	private void placeAliens() {
		for( int j = 0; j < 5 ; j++) {
			for(int i = 0; i < 10; i++) {
				aliens[i + j * 10].setPosition(120 + i * 45, 525 + j * 40);
				aliens[i + j * 10].setState(AlienState.ALIVE);
			}
		}
	}


	@Override
	public void update(float dt) {
		swarmXTimer += dt;
		if (swarmXTimer > 2.0f) swarmXTimer = 0.0f;
		float swarmXPos = (swarmXTimer > 1.0f) ? 2.0f - swarmXTimer: swarmXTimer;

		for( int i= 0; i< MAX_ALIENS; i++) {
			if(!aliens[i].isAlive()) continue;
			aliens[i].setPosition(100 + swarmXPos * 30 + (i % 10) * 45 , aliens[i].getY());
		}

		firing();
		stage.act(dt);
	}


	void firing() {
		float chanceToFire = updateChanceToFire() * chanceToFireMod;
		LiveAliens().forEach(
				alien -> {
					if (alien.isFiring(chanceToFire)) {
						Vector2 fire_vector = new Vector2(MathUtils.random(-50.0f, 50.0f), -MathUtils.random(250.0f, 350.f));
						fire_vector.scl(1.0f/fire_vector.len());
						fire_vector.scl(updateBulletSpeed());
						alienBullets.fireBullet(
									alien.getBulletType(),
									new Vector2(alien.getCentreX(), alien.getY()-alien.getHeight()),
									fire_vector
						);
					}
				}
		);
	}


	void draw() {
		stage.draw();
	}


	private float updateBulletSpeed() {
		int numberAliens = (int) LiveAliens().count();

		if (numberAliens < 2) return 500.0f;
		if (numberAliens < 5) return 350.0f;
		if (numberAliens < 10) return 350.0f;
		if (numberAliens < 20) return 250.0f;
		if (numberAliens < 35) return 250.0f;
		return 250.0f;
	}



	private float updateChanceToFire() {
		int numberAliens = (int) LiveAliens().count();

		if (numberAliens < 2) return 0.06f;
		if (numberAliens < 5) return 0.03f;
		if (numberAliens < 10) return 0.008f;
		if (numberAliens < 20) return 0.005f;
		if (numberAliens < 35) return 0.002f;
		return 0.00025f;
	}


	void handleCollisions(Stream<BulletBaseActor> bullets, PlayerScore score) {
		List<BulletBaseActor> bulletsList= bullets.collect(Collectors.toList());
		LiveAliens().forEach(
				alien -> {
					bulletsList.stream().filter(alien::collidesWith).forEach(
							bullet -> {
								killAlien(alien);
								score.addScore(alien.getScore());
								bullet.removeFromPlay();
							}
					);
				}
		);
	}


	void killAllAliens(PlayerScore score) {
		LiveAliens().forEach(
				alien -> {killAlien(alien); score.addScore(alien.getScore());}
		);
	}

	boolean temp = false;
	private void killAlien(BaseAlien alien) {

		alien.setState(AlienState.DEAD);
		particleFoundry.newEmitter(alien, alien.particleExplosion());

		float pan = (alien.getCentreX() - HALF_SCREEN_WIDTH)/HALF_SCREEN_WIDTH;
		SoundSystem.getInstance().play(explosionSound,1.0f,MathUtils.random(0.7f,1.3f),pan);
	}


	private Stream<BaseAlien> LiveAliens() {
		return Arrays.stream(aliens).filter(BaseAlien::isAlive);
	}
}
