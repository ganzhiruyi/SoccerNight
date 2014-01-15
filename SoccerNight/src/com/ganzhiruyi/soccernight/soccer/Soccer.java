package com.ganzhiruyi.soccernight.soccer;

import com.ganzhiruyi.soccernight.object.DynamicObject;

public abstract class Soccer extends DynamicObject {
	public static float SOCCER_WIDTH = 20f;
	public static float SOCCER_HEIGHT = 20f;
	public static float SOCCER_VELOCITY = 2f;

	public Soccer(float x, float y) {
		super(x, y, SOCCER_WIDTH, SOCCER_HEIGHT);
	}
	
	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
	}
	public abstract void roll(float deltaTime);
	@Override
	protected boolean isObjectCanOut() {
		return true;
	}
	@Override
	protected boolean isObjectWide() {
		return true;
	}

	@Override
	protected float getWidth() {
		return SOCCER_WIDTH;
	}

	@Override
	protected float getHeight() {
		return SOCCER_HEIGHT;
	}

	@Override
	protected float getVelocity() {
		return SOCCER_VELOCITY;
	}

}
