package com.ganzhiruyi.soccernight.role;

import com.ganzhiruyi.soccernight.utils.Config;

public class Bob extends DynamicObject {
	public static final float BOB_WIDTH = 51f;
	public static final float BOB_HEIGHT = 84f;
	public static final float BOB_VELOCITY = 8f;
	private DyObjectState state;
	private float stateTime;

	public Bob(float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
		state = DyObjectState.IDLE;
		stateTime = 0f;
	}

	public void update(float deltaTime, float accelX, float accelY) {
		if(Math.abs(accelX) < 1) accelX = 0;
		if(Math.abs(accelY) < 1) accelY = 0;
		
		float vx = velocity.x, vy = velocity.y;
		velocity.add(accelX * deltaTime, accelY * deltaTime);
		position.add(calOffset(vx, accelX, deltaTime),
				calOffset(vy, accelY, deltaTime));
		if (position.x < 0)
			position.x = 0;
		if (position.x + BOB_WIDTH > Config.SCREEN_WIDTH)
			position.x = Config.SCREEN_WIDTH - BOB_WIDTH;
		if (position.y < 0)
			position.y = 0;
		if (position.y + BOB_HEIGHT > Config.SCREEN_HIGHT)
			position.y = Config.SCREEN_HIGHT - BOB_HEIGHT;
		System.out.println("bob position : x,y ----------: " + position.x + "," + position.y );
		if(isStill()){
			stateTime = 0;
			state = DyObjectState.IDLE;
		}
		else{
			stateTime += deltaTime;
			state = DyObjectState.MOVING;
		}
	}
	public DyObjectState getState(){
		return state;
	}
	public float getStateTime(){
		return stateTime;
	}
}
