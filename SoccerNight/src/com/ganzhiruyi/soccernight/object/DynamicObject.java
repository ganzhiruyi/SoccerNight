package com.ganzhiruyi.soccernight.object;

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

	public enum EdgeType {
		NONE, BOTTOM, LEFT, TOP, RIGHT;
	}

	protected boolean isOutofStage;
	protected EdgeType isOnStageEdge;

	public DynamicObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.velocity = new Vector2();
		state = DyObjectState.IDLE;
		stateTime = 0f;
		isOutofStage = false;
		isOnStageEdge = EdgeType.NONE;
	}

	public void update(float deltaTime, float accelX, float accelY) {
		if (Math.abs(accelX) < 0.1 && Math.abs(accelY) < 0.1) {
			state = DyObjectState.IDLE;
			stateTime = 0;
			velocity.x = velocity.y = 0;
			//updatePosition();
			return;
		}
		if (Math.abs(accelX) < 0.1)
			accelX = 0;
		else {
			velocity.x = accelX;
			isRight = velocity.x > 0;
		}

		if (Math.abs(accelY) < 0.1)
			accelY = 0;
		else {
			velocity.y = accelY;
			isUp = velocity.y > 0;
		}
		updatePosition();
		state = DyObjectState.MOVING;
		stateTime += deltaTime;
	}

	protected void updatePosition() {
		position.add(velocity.x * getVelocity(), velocity.y * getVelocity());
		if (!isObjectCanOut()) {
			if (position.x < 0) {
				position.x = 0;
				isOnStageEdge = EdgeType.LEFT;
			}
			if (position.x + width > Config.SCREEN_WIDTH) {
				position.x = Config.SCREEN_WIDTH - width;
				isOnStageEdge = EdgeType.RIGHT;
			}
			if (position.y < 0) {
				position.y = 0;
				isOnStageEdge = EdgeType.BOTTOM;
			}
			if (position.y + height > Config.SCREEN_HEIGHT) {
				position.y = Config.SCREEN_HEIGHT - height;
				isOnStageEdge = EdgeType.TOP;
			}
		} else {
			if (position.x < 0 || position.x > Config.SCREEN_WIDTH
					|| position.y < 0 || position.y > Config.SCREEN_HEIGHT)
				isOutofStage = true;
		}
		// update the bounds of object, and the "+10f" is to relax the edge
		bounds.x = position.x + width / 8;
		bounds.y = position.y + height / 8;

	}

	public DyObjectState getState() {
		return state;
	}

	public float getStateTime() {
		return stateTime;
	}

	protected boolean isObjectCanOut() {
		// judge the object whether can out of the edge
		return false;
	}

	public abstract float getVelocity();
}
