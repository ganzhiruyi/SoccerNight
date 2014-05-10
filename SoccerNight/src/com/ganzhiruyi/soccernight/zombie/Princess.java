package com.ganzhiruyi.soccernight.zombie;

import java.util.Random;

public class Princess extends Zombie {
	private static final float PRINCESS_VELOCITY = 1f;
	private static final float WIDTH = 68f;
	private static final float HEIGHT = 70f;
	private static final float IDLE_LIMIT = 2;
	public int blood;
	public static int WALK = 0;
	public static int STAB = 1;
	public static int HACK = 2;
	public static int WAVE = 3;
	public static int DEAD = 4;
	private int move;
	private float preMoveTime;
	private Random rand;
	private int hurricaneNum;
	private int fireNum;

	public Princess(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		blood = 20;
		move = STAB;
		preMoveTime = hurricaneNum = fireNum = 0;
		rand = new Random();
	}

	@Override
	public void update(float deltaTime, float accelX, float accelY) {
		if (state == DyObjectState.DEAD)
			return;
		if (blood <= 0)
			move = DEAD;
		if (state == DyObjectState.IDLE) {
			if (move != WALK) {
				if (stateTime - preMoveTime <= IDLE_LIMIT)
					stateTime += deltaTime;
				else if (move != DEAD) {
					move = WALK;
					super.update(deltaTime, accelX, accelY);
				} else
					state = DyObjectState.DEAD;
			}
		} else {
			int nextMove = rand.nextInt(100);
			if (nextMove == 30) {
				move = STAB;
				state = DyObjectState.IDLE;
				preMoveTime = stateTime;
			} else if (nextMove == 60) {
				move = HACK;
				state = DyObjectState.IDLE;
				preMoveTime = stateTime;
			} else if (nextMove == 90) {
				move = WAVE;
				state = DyObjectState.IDLE;
				preMoveTime = stateTime;
			} else
				super.update(deltaTime, accelX, accelY);
		}
	}

	public int getHurricaneNum() {
		return hurricaneNum;
	}

	public void addHurricaneNum(int magicNum) {
		this.hurricaneNum += magicNum;
		this.hurricaneNum = Math.max(this.hurricaneNum, 0);
	}

	public int getFireNum() {
		return fireNum;
	}

	public void addFireNum(int fireNum) {
		this.fireNum += fireNum;
		this.fireNum = Math.max(this.fireNum, 0);
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	@Override
	public float getVelocity() {
		return PRINCESS_VELOCITY;
	}
}
