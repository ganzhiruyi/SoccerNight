package com.ganzhiruyi.soccernight.role;

public class PaddySoccer extends Soccer {
	public PaddySoccer(float x, float y) {
		super(x, y);
	}
	
	
	@Override
	protected boolean isObjectCanOut() {
		return false;
	}

}
