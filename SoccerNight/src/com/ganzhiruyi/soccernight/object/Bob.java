package com.ganzhiruyi.soccernight.object;

public class Bob extends DynamicObject {
	public static float BOB_WIDTH = 20f;
	public static float BOB_HEIGHT = 30f;
	public static final float BOB_VELOCITY = 1.5f;
	
	public Bob(float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
	}

	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
	}

	@Override
	public float getVelocity() {
		return BOB_VELOCITY;
	}
	@Override
	protected boolean isObjectWide() {
		return true;
	}
}
		
		
		