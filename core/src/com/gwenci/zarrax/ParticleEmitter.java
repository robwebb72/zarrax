package com.gwenci.zarrax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter {

	private List<Particle> particles = new ArrayList<>();
	private Texture texture =  new Texture(Gdx.files.internal("assets/spectrum.png"));
	private boolean isLive = false;

	void add(Particle particle) {
		particles.add(particle);
	}

	void initialise(float x, float y) {
		int counter = 0;
		int size = particles.size();
		for (Particle p : particles) {
			switch (counter) {
				case 0:
					p.initialise(x, y, 150, texture, 2, 1.0f);
					break;
				case 1:
					p.initialise(x, y, 300, texture, 1, 1.0f);
					break;
				case 2:
				case 3:
					p.initialise(x, y, 900, texture, 0, 1.0f);
			}
			counter++;
			if (counter>3) counter= 0;
		}
		isLive = true;
	}

	boolean isLive() {
		return isLive;
	}

	void render(SpriteBatch batch) {
		if (!isLive) return;
		for(Particle p : particles) p.render(batch);
	}

	void act(float dt) {
		if (!isLive) return;
		for (Particle p : particles) p.act(dt);
		if(allParticlesDead()) isLive = false;
	}

	private boolean allParticlesDead() {
		for (Particle p : particles) if(!p.isFinished()) return false;
		return true;

	}
}
