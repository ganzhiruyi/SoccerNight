package com.ganzhiruyi.soccernight.role;


public class Zombie extends DynamicObject {
	private static final float ZOMBIE_VELOCITY = 1f; 
	public static float ZOMBIE_WIDTH = 97f;
	public static float ZOMBIE_HEIGHT = 50f;
	static{
		ZOMBIE_HEIGHT = Bob.BOB_HEIGHT;
		ZOMBIE_WIDTH = Bob.BOB_WIDTH;
	}
	
	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT);
	}
	public void update(float deltaTime, float accelX, float accelY){
		super.update(deltaTime, accelX, accelY);
	}

	@Override
	protected float getWidth() {
		return ZOMBIE_WIDTH;
	}

	@Override
	protected float getHeight() {
		return ZOMBIE_HEIGHT;
	}
	@Override
	protected float getVelocity() {
		return ZOMBIE_VELOCITY;
	}
}
