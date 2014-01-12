package com.ganzhiruyi.soccernight.role;

import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;

public class Bob extends DynamicObject {
	public static float BOB_WIDTH = 51f;
	public static float BOB_HEIGHT = 84f;
	public static final float BOB_VELOCITY = 5f;
	
	static{
		BOB_WIDTH = Assets.aniBobIdleL.getRegionWidth();
		BOB_HEIGHT = Assets.aniBobIdleL.getRegionHeight();
	}
	public Bob(float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
	}

	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
	}

	@Override
	protected float getWidth() {
		return BOB_WIDTH;
	}

	@Override
	protected float getHeight() {
		return BOB_HEIGHT;
	}

	@Override
	protected float getVelocity() {
		return BOB_VELOCITY;
	}
}
