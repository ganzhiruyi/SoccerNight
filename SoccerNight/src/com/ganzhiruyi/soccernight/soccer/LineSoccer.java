package com.ganzhiruyi.soccernight.soccer;

public class LineSoccer extends Soccer {
	public LineSoccer(float x, float y) {
		super(x, y);
	}
	@Override
	public void roll(float deltaTime) {
		// roll as previous velocity
		update(deltaTime, velocity.x, velocity.y);
		if (isOutofStage)
			state = DyObjectState.DEAD;
	}
	@Override
	protected boolean isObjectCanOut() {
		return true;
	}
}
