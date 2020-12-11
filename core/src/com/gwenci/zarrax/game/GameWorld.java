package com.gwenci.zarrax.game;

import com.gwenci.zarrax.Zarrax;

public class GameWorld {

	private static final int MAX_ALIEN_BULLETS = 250;

	public PlayerActor playerActor;	// TODO - create a player wrangler - but outside of this class
	public PlayerBullets playerBullets;
	public BulletManager alienBullets;
	public AlienWrangler aliens;


	public void initialise() {

		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerActor = new PlayerActor(playerBullets, Zarrax.getViewPort(), Zarrax.getSpriteBatch());

		alienBullets = new BulletManager(MAX_ALIEN_BULLETS);

	}


	public void setUpAliens() {
		aliens = new AlienWrangler(Zarrax.getViewPort(), Zarrax.getSpriteBatch(),alienBullets);
	}


}
