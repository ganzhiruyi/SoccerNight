package com.ganzhiruyi.soccernight.zombie;

import java.util.Random;

public class Eater extends Zombie {
	private static final float MOVE_TIME = 2f;
	private static final float EATER_VELOCITY = 1f;
	private Random rand;
	private float preStateTime = 0;

	public Eater(float x, float y) {
		super(x, y);
		rand = new Random();
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if (state == DyObjectState.DEAD)
			return;
		if (state == DyObjectState.IDLE) {
			preStateTime = stateTime;
			super.update(deltaTime, accelX, accelY);
		} else {
			if (stateTime - preStateTime >= MOVE_TIME) {
				preStateTime = stateTime;
				int vx = rand.nextInt(3) - 1;
				int vy = rand.nextInt(3) - 1;
				if(vx == 0 && vy == 0)
					vx++;
				super.update(deltaTime, vx, vy);
			} else
				super.update(deltaTime, velocity.x, velocity.y);
		}
	}

	@Override
	public float getVelocity() {
		return EATER_VELOCITY;
	}
}
