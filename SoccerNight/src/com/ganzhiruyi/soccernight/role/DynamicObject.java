package com.ganzhiruyi.soccernight.role;

import com.badlogic.gdx.math.Vector2;
import com.ganzhiruyi.soccernight.utils.Config;

public abstract class DynamicObject extends GameObject {
	public Vector2 velocity;
	protected DyObjectState state;
	protected float stateTime;
	public boolean isRight = true;
	public boolean isUp = true;
	public enum DyObjectState {
		IDLE, DEAD, MOVING;
	}
	public boolean isOutofStage;

	public DynamicObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.velocity = new Vector2();
		state = DyObjectState.IDLE;
		stateTime = 0f;
		isOutofStage = false;
	}
	public void update(float deltaTime, float accelX, float accelY) {
		if (Math.abs(accelX) < 0.1 && Math.abs(accelY) < 0.1) {
			state = DyObjectState.IDLE;
			stateTime = 0;
			velocity.x = velocity.y = 0;
			return;
		}
		if (Math.abs(accelX) < 0.1)
			accelX = 0;
		else{
			velocity.x = accelX*getVelocity();
			isRight = velocity.x > 0;
		}
			
		if (Math.abs(accelY) < 0.1)
			accelY = 0;
		else{
			velocity.y = accelY*getVelocity();
			isUp = velocity.y > 0;
		}
		updatePosition();
		state = DyObjectState.MOVING;
		stateTime += deltaTime;
	}
	protected void updatePosition(){
		position.add(velocity.x, velocity.y);
		float width = getWidth(), height = getHeight();
		if(!isObjectCanOut()){
			if (position.x < 0)
				position.x = 0;
			if (position.x + width > Config.SCREEN_WIDTH)
				position.x = Config.SCREEN_WIDTH - width;
			if (position.y < 0)
				position.y = 0;
			if (position.y + height > Config.SCREEN_HEIGHT)
				position.y = Config.SCREEN_HEIGHT - height;
		}
		else{
			if(position .x < 0 || position .x > Config.SCREEN_WIDTH || position.y < 0 || position.y > Config.SCREEN_HEIGHT)
				isOutofStage = true;
		}
		//update the bounds of object, and the "+10f" is to relax the edge
		bounds.x = position.x + 10f;
		bounds.y = position.y + 10f;
		
	}
	public DyObjectState getState() {
		return state;
	}

	public float getStateTime() {
		return stateTime;
	}
	protected boolean isObjectCanOut(){
		//judge the object whether can out of the edge
		return false;
	}
	protected abstract float getWidth();
	protected abstract float getHeight();
	protected abstract float getVelocity();
}
