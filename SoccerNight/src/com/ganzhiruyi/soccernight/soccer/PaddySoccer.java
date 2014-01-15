package com.ganzhiruyi.soccernight.soccer;

public class PaddySoccer extends Soccer {
	public static final int RE_DIRECTION_LIMIT = 5; 
	private int reDirectionTime;
	public PaddySoccer(float x, float y) {
		super(x, y);
		reDirectionTime = 0;
	}
	@Override
	public void roll(float deltaTime) {
		if(reDirectionTime >= RE_DIRECTION_LIMIT){
			state = DyObjectState.DEAD;
			return;
		}
		update(deltaTime, velocity.x, velocity.y);
		if(isOnStageEdge == EdgeType.BOTTOM && velocity.y < 0){
			velocity.y = -velocity.y;
			reDirectionTime++;
		}
		else if(isOnStageEdge == EdgeType.TOP && velocity.y > 0){
			velocity.y = -velocity.y;
			reDirectionTime++;
		}
		else if(isOnStageEdge == EdgeType.LEFT && velocity.x < 0){
			velocity.x = -velocity.x;
			reDirectionTime++;
		}
		else if(isOnStageEdge == EdgeType.RIGHT && velocity.x > 0){
			velocity.x = -velocity.x;
			reDirectionTime++;
		}
	}
	
	@Override
	protected boolean isObjectCanOut() {
		return false;
	}
}
