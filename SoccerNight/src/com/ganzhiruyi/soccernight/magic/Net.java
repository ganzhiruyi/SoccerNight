package com.ganzhiruyi.soccernight.magic;

public class Net extends Magic {
	private boolean catched;

	public Net(float x, float y) {
		super(x, y);
		catched = false;
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if (state == DyObjectState.DEAD)
			return;
		if (stateTime >= 3.5) {
			state = DyObjectState.DEAD;
			return;
		}
		if (catched || stateTime >= 1) {
			state = DyObjectState.IDLE;
			stateTime += deltaTime;
		} else
			super.update(deltaTime, accelX, accelY);
	}

	@Override
	protected boolean isObjectCanOut() {
		return false;
	}

	public void setCatched(boolean catched) {
		this.catched = catched;
	}

	public boolean getCatched() {
		return catched;
	}
}
