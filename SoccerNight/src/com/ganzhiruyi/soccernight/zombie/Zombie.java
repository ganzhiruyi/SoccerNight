package com.ganzhiruyi.soccernight.zombie;

import com.ganzhiruyi.soccernight.object.DynamicObject;

public class Zombie extends DynamicObject {
	private static final float ZOMBIE_VELOCITY = 0.5f; 
	public static float ZOMBIE_WIDTH = 20f;
	public static float ZOMBIE_HEIGHT = 30f;
	
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
