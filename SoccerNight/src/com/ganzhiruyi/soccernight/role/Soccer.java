package com.ganzhiruyi.soccernight.role;

public class Soccer extends DynamicObject {
	public static float SOCCER_WIDTH = 20f;
	public static float SOCCER_HEIGHT = 20f;
	public static float SOCCER_VELOCITY = 2f;
	public int dirX = 0;
	public int dirY = 0;
//	static{
//		SOCCER_WIDTH = Assets.aniSoccerIdle.getRegionWidth();
//		SOCCER_HEIGHT = Assets.aniSoccerIdle.getRegionHeight();
//	}

	public Soccer(float x, float y) {
		super(x, y, SOCCER_WIDTH, SOCCER_HEIGHT);
	}
	
	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
		dirX = (int) accelX;
		dirY = (int) accelY;
	}
	public void roll(float deltaTime){
		super.update(deltaTime, dirX, dirY);
	}
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
