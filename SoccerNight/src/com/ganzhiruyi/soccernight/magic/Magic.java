package com.ganzhiruyi.soccernight.magic;

import com.ganzhiruyi.soccernight.object.DynamicObject;

public class Magic extends DynamicObject {
	public static final float HEIGHT = 30;
	public static final float WIDTH = 30;
	public static final float VELOCITY = 1.5f;
	public Magic(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
	}
	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if(state == DyObjectState.DEAD)
			return;
		if(isOutofStage){
			state = DyObjectState.DEAD;
			return;
		}
		super.update(deltaTime, accelX, accelY);
	}
	@Override
	protected boolean isObjectCanOut() {
		return true;
	}

	@Override
	protected float getWidth() {
		return WIDTH;
	}

	@Override
	protected float getHeight() {
		return HEIGHT;
	}

	@Override
	protected float getVelocity() {
		return VELOCITY;
	}
	
}
