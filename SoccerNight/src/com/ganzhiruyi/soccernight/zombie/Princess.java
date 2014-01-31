package com.ganzhiruyi.soccernight.zombie;

public class Princess extends Zombie {
	private static final float PRINCESS_VELOCITY = 1.5f;
	public int blood;
	public static int WALK = 0;
	public static int STAB = 1;
	public static int HACK = 2;
	public static int DEAD = 3;
	private int move;
	public Princess(float x, float y) {
		super(x, y);
		blood = 20;
		move = WALK;
	}
	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if(state == DyObjectState.DEAD)
			return;
		super.update(deltaTime, accelX, accelY);
		if(move == DEAD)
			state = DyObjectState.DEAD;
	}
	public int getMove(){
		return move;
	}
	public void setMove(int move){
		this.move = move;
	}
	
	@Override
	protected float getVelocity() {
		return PRINCESS_VELOCITY;
	}
}
