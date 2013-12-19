package com.ganzhiruyi.soccernight.role;

import com.ganzhiruyi.soccernight.utils.Config;

public class Bob extends DynamicObject {
	public static final float BOB_WIDTH = 0.8f;
	public static final float BOB_HEIGHT = 0.8f;
	public static final float BOB_VELOCITY = 8f;
	private DyObjectState state;
	private float stateTime;

	public Bob(float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
		state = DyObjectState.IDLE;
		stateTime = 0f;
	}

	public void update(float deltaTime, float accelX, float accelY) {
		float vx = velocity.x, vy = velocity.y;
		velocity.add(accelX * deltaTime, accelY * deltaTime);
		position.add(calOffset(vx, accelX, deltaTime),
				calOffset(vy, accelY, deltaTime));
		if (position.x < 0)
			position.x = 0;
		if (position.x > Config.SCREEN_WIDTH)
			position.y = Config.SCREEN_WIDTH;
		if (position.y < 0)
			position.y = 0;
		if (position.y > Config.SCREEN_HIGHT)
			position.y = Config.SCREEN_HIGHT;
		stateTime += deltaTime;
	}
	public DyObjectState getState(){
		return state;
	}
	public float getStateTime(){
		return stateTime;
	}
}
