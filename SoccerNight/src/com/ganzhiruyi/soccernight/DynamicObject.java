package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.math.Vector2;

public class DynamicObject extends GameObject {
	private Vector2 accel;
	private Vector2 velocity;

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

}
