package com.ganzhiruyi.soccernight.role;

import com.badlogic.gdx.math.Vector2;

public class DynamicObject extends GameObject {
	public static final float RESISTANCE_ACCEL = 6f;
	protected Vector2 accel;
	protected Vector2 velocity;
	public enum DyObjectState {
		IDLE,DEAD,MOVING;
	}

	public DynamicObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.accel = new Vector2();
		this.velocity = new Vector2();
	}

	public Vector2 getAccel() {
		return accel;
	}

	public void setAccel(Vector2 accel) {
		this.accel = accel;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public float calOffset(float v, float accel, float t) {
		// calculate the offset
		return v * t + accel * t * t / 2;
	}

}
