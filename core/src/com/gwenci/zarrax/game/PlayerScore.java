package com.gwenci.zarrax.game;

import com.gwenci.zarrax.Updatable;

public class PlayerScore implements Updatable {

	private long actualScore= 0;
	private long displayScore= 0;
	private float lastScoreUpdate;
	private final float scoreRate;

	PlayerScore(float scoreRate) {
		this.scoreRate = scoreRate;
	}

	public void addScore(int score) {
		actualScore += score;
	}

	public long getDisplayScore() {
		return displayScore;
	}

	@Override
	public void update(float dt) {
		lastScoreUpdate -= dt;
		if(lastScoreUpdate<0.0f) {
			if (displayScore < actualScore) displayScore++;
			lastScoreUpdate = scoreRate;
		}
	}
}
