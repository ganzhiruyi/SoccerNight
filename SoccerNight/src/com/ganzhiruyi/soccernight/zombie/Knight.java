package com.ganzhiruyi.soccernight.zombie;

public class Knight extends Zombie {
	private float preStateTime = 0;
	private static final float IDLE_TIME = 1f;
	private static final float MOVE_TIME = 5f;
	private static final float KNIGHT_VELOCITY = 2f; 

	public Knight(float x, float y) {
		super(x, y);
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if (state == DyObjectState.DEAD)
			return;
		if (state == DyObjectState.IDLE) {
			if (stateTime - preStateTime >= IDLE_TIME) {
				preStateTime = stateTime;
				super.update(deltaTime, accelX, accelY);
			}
			else
				stateTime += deltaTime;
		} else {
			if (stateTime - preStateTime >= MOVE_TIME) {
				state = DyObjectState.IDLE;
				preStateTime = stateTime;
			} else
				super.update(deltaTime, velocity.x, velocity.y);
		}
	}
	@Override
	protected float getVelocity() {
		return KNIGHT_VELOCITY;
	}
}
