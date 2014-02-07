package com.ganzhiruyi.soccernight.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author ganzhiruyi The class is to test whether overlap is happen.
 */
public class OverlapTester {
	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		return r1.overlaps(r2);
	}

	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.x <= p.x && p.x <= r.x + r.width && r.y <= p.y
				&& p.y <= r.y + r.height;
	}

	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.x <= x && x <= r.x + r.width && r.y <= y
				&& y <= r.y + r.height;
	}
}
