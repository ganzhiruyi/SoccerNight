package com.ganzhiruyi.soccernight.zombie;

import com.ganzhiruyi.soccernight.object.DynamicObject;

public class Zombie extends DynamicObject {
	private static final float ZOMBIE_VELOCITY = 0.5f;
	public static float ZOMBIE_WIDTH = 20f;
	public static float ZOMBIE_HEIGHT = 30f;

	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT);
	}

	public Zombie(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
	}

	@Override
	public float getVelocity() {
		return ZOMBIE_VELOCITY;
	}
}
