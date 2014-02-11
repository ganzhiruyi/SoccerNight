package com.ganzhiruyi.soccernight.object;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	public float width;
	public float height;
	
	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x + width / 8, y + height / 8,
				width * 3 / 4, height * 3 / 4);
		this.width = width;
		this.height = height;
	}

	protected boolean isObjectWide() {
		// represent the object bounds whether is wide,affect the judement of
		// overlap
		return false;
	}
}
