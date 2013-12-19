package com.ganzhiruyi.soccernight.role;

import com.ganzhiruyi.soccernight.utils.SoccerState;

public class Soccer extends DynamicObject {
	public static float SOCCER_WIDTH = 0.8f;
	public static float SOCCER_HEIGHT = 0.8f;
	private SoccerState state;

	public Soccer(float x, float y) {
		super(x, y, SOCCER_WIDTH, SOCCER_HEIGHT);
		state = SoccerState.STAY;
	}

}
