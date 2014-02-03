package com.ganzhiruyi.soccernight.soccer;

import com.badlogic.gdx.math.Vector2;

public class RoundSoccer extends Soccer {
	private float radius;
	private float radian;
	private Vector2 center;
	private float startTime;
	private static float pi = (float) Math.PI;

	public RoundSoccer(float x, float y) {
		super(x, y);
		radius = 50f;
	}

	@Override
	public void roll(float deltaTime) {
		if (state == DyObjectState.DEAD)
			return;
		if (startTime >= 3) {
			state = DyObjectState.DEAD;
			return;
		}
		radian += pi / 16;
		position.x = (float) (radius * Math.cos(radian) + center.x);
		position.y = (float) (radius * Math.sin(radian) + center.y);
		startTime += deltaTime;
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		center = new Vector2();
		if (accelX > 0) {
			radian = -pi / 2;
			center.x = position.x;
			center.y = position.y + radius;
		} else if (accelX < 0) {
			radian = pi / 2;
			center.x = position.x;
			center.y = position.y - radius;
		} else if (accelY > 0) {
			radian = 0;
			center.x = position.x - radius;
			center.y = position.y;
		} else if (accelY < 0) {
			radian = pi;
			center.x = position.x + radius;
			center.y = position.y;
		}
		state = DyObjectState.MOVING;
		stateTime += deltaTime;
	}

	@Override
	protected boolean isObjectCanOut() {
		return true;
	}
}
