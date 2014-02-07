package com.ganzhiruyi.soccernight.soccer;

import com.ganzhiruyi.soccernight.utils.Config;

public class WaveSoccer extends Soccer {
	private enum Direction {
		DOWN, RIGHT, UP, LEFT
	} 
	private float x0, y0;
	private Direction dir;

	public WaveSoccer(float x, float y) {
		super(x, y);
		x0 = x;
		y0 = y;
	}

	@Override
	public void roll(float deltaTime) {
		if (state == DyObjectState.DEAD)
			return;
		if (position.x < 0 || position.x > Config.SCREEN_WIDTH
				|| position.y < 0 || position.y > Config.SCREEN_HEIGHT)
			isOutofStage = true;
		if (isOutofStage) {
			state = DyObjectState.DEAD;
			return;
		}
		float x = position.x, y = position.y;
		if (dir == Direction.LEFT) {
			x -= 5;
			y = MSin(x - x0) + y0;
		} else if (dir == Direction.RIGHT) {
			x += 5;
			y = MSin(x - x0) + y0;
		} else if (dir == Direction.UP) {
			y += 5;
			x = MSin(y - y0) + x0;
		} else {
			y -= 5;
			x = MSin(y - y0) + x0;
		}
		bounds.x = position.x = x;
		bounds.y = position.y = y;
		state = DyObjectState.MOVING;
		stateTime += deltaTime;
	}

	private float MSin(float x) {
		return (float) (Math.sin(x)) * 20f;
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if (Math.abs(accelX) > Math.abs(accelY))
			dir = accelX > 0 ? Direction.RIGHT : Direction.LEFT;
		else
			dir = accelY > 0 ? Direction.UP : Direction.DOWN;
		super.update(deltaTime, accelX, accelY);
	}

}
