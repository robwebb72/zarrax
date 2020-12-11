package com.gwenci.zarrax.game;

import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.game.aliens.AlienWrangler;
import com.gwenci.zarrax.game.player.PlayerActor;

public class GameWorld {

	private static final int MAX_ALIEN_BULLETS = 250;
	private static final int SCREEN_WIDTH = 672;

	public PlayerActor playerActor;	// TODO - create a player wrangler - but outside of this class
	public PlayerBullets playerBullets;
	public BulletManager alienBullets;
	public AlienWrangler aliens;


	public void initialise() {

		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerActor = new PlayerActor(playerBullets, Zarrax.getViewPort(), Zarrax.getSpriteBatch());

		alienBullets = new BulletManager(MAX_ALIEN_BULLETS);

	}

	public void initialiseAliens() {
		aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch(),alienBullets);
	}

	public void initialisePlayer() {
		if (!playerActor.isAlive()) {
			playerActor.setPosition(SCREEN_WIDTH / 2.0f - 16f, 25f);
			playerActor.setIsAlive(true);
			playerActor.setShieldsOn(3.0f);
		}
	}

}
