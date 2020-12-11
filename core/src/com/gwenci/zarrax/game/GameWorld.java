package com.gwenci.zarrax.game;

import com.gwenci.zarrax.Zarrax;

public class GameWorld {

	public PlayerActor playerActor;	// TODO - create a player wrangler - but outside of this class
	public PlayerBullets playerBullets;


	public void initialise() {

		playerBullets = new PlayerBullets(Zarrax.getViewPort(), Zarrax.getSpriteBatch());
		playerActor = new PlayerActor(playerBullets, Zarrax.getViewPort(), Zarrax.getSpriteBatch());

	}


}
