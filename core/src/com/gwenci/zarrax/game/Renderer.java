package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.GameFont;
import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

public class Renderer {

	private final SpriteBatch batch = Zarrax.getSpriteBatch();
	private final BitmapFont font;

	public Renderer() {
		font = GameFont.getInstance().getFont();
	}

	public void render(GameScreen gs, GameWorld gw) {
		batch.begin();

		// background
		gs.starfield.render(batch);
		batch.end();

		batch.begin();
		// game world
		gw.playerBullets.getActiveBullets().forEach(b -> b.draw(batch));
		gw.alienBullets.getActiveBullets().forEach(b->b.draw(batch));
		ParticleFoundry.getInstance().render(batch);
		// TODO: Aliens and Player need to be drawn here

		// gui
		gs.framerate.render(batch);
		font.draw(batch, String.format("%08d",gs.playerScore.getDisplayScore()) , 275, 768- 3);
		if(gs.muted) font.draw(batch, "muted" , 570, 43);
		if(gs.paused) font.draw(batch, "paused" , 300, 400);
		//	font.draw(batch,"hi 00000700" , 4, 768- 3);
		batch.end();

		// TODO: Need to make the drawing method consistent - have all items draw in the same batch
		gw.playerActor.draw();
		gw.aliens.draw();
	}

	public void end() {
		batch.end();
	}
}
