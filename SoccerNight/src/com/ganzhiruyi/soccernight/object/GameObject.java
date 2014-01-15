package com.ganzhiruyi.soccernight.object;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;

	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		if (isObjectWide())
			this.bounds = new Rectangle(x, y, width, height);
		else
			this.bounds = new Rectangle(x + width / 4, y + width / 4,
					width / 2, height / 2);
	}

	protected boolean isObjectWide() {
		// represent the object bounds whether is wide,affect the judement of overlap
		return false;
	}
}
