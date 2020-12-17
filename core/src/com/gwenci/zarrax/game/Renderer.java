package com.gwenci.zarrax.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gwenci.zarrax.GameFont;
import com.gwenci.zarrax.Starfield;
import com.gwenci.zarrax.Zarrax;
import com.gwenci.zarrax.particle_system.ParticleFoundry;

public class Renderer {

	private final SpriteBatch batch = Zarrax.getSpriteBatch();
	private final BitmapFont font;
	private final GameScreen gs;
	private final GameWorld gameWorld;

	public Renderer(GameScreen gs, GameWorld gameWorld) {
		font = GameFont.getInstance().getFont();
		this.gs = gs;
		this.gameWorld = gameWorld;
	}

	public void render() {
		batch.begin();
		renderBackground();
		renderGameWorld();
		renderGUI();
		batch.end();

		// TODO: Need to make the drawing method consistent - have all items draw in the same batch
		gameWorld.playerActor.draw();
		gameWorld.aliens.draw();
	}


	private void renderBackground() {
		Starfield.getInstance().render(batch);
		ParticleFoundry.getInstance().render(batch);
	}

	private void renderGUI() {
		gs.framerate.render(batch);
		font.draw(batch, String.format("%08d",gs.playerScore.getDisplayScore()) , 275, 768- 3);
		if(gs.muted) font.draw(batch, "muted" , 570, 43);
		if(gs.paused) font.draw(batch, "paused" , 300, 400);
	}

	private void renderGameWorld() {
		gameWorld.playerBullets.getActiveBullets().forEach(b->b.draw(batch));
		gameWorld.alienBullets.getActiveBullets().forEach(b->b.draw(batch));
	}

}
