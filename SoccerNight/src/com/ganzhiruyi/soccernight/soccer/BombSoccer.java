package com.ganzhiruyi.soccernight.soccer;

public class BombSoccer extends Soccer {
	private float startTime;
	public BombSoccer(float x, float y) {
		super(x, y);
	}

	@Override
	public void roll(float deltaTime) {
		if(state == DyObjectState.DEAD)
			return;
		if(startTime >= 3){
			state = DyObjectState.DEAD;
			return;
		}
		stateTime += deltaTime;
		startTime += deltaTime;;
	}
	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		super.update(deltaTime, accelX, accelY);
		bounds.width = bounds.height = 40f;
		startTime = 0;
	}
	
}
