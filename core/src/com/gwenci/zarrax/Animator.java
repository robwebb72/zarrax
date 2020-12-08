package com.gwenci.zarrax;

public class Animator {
	private final float frameTimeInSecs;
	private float timerInSecs;
	private final int nFrames;
	private int currentFrame;

	public Animator(int nFrames, float frameTimeInSecs) {
		this.nFrames = nFrames;
		this.frameTimeInSecs = frameTimeInSecs;
	}

	public int getNFrames() {
		return nFrames;
	}

	public void update(float dtInSec) {
		timerInSecs += dtInSec;
		if (timerInSecs > frameTimeInSecs) {
			timerInSecs = 0;
			currentFrame = (currentFrame == nFrames-1) ? 0 : currentFrame + 1;
		}
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}
}
