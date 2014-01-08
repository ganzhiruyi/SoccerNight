package com.ganzhiruyi.soccernight.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.ganzhiruyi.soccernight.role.Bob;
import com.ganzhiruyi.soccernight.role.Soccer;
import com.ganzhiruyi.soccernight.role.Zombie;
import com.ganzhiruyi.soccernight.utils.Config;

/**
 * 
 * @author ganzhiruyi World is a class manager the objects in the game, such as
 *         check collision, update the object and so on.
 * 
 */
public class World {
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;

	public interface WorldListener {
		public void hit();

		public void getCoins();

		public void getSoccer(int type);
	}

	WorldListener listener;
	private List<Soccer> soccers;
	private List<Zombie> zombies;
	private Bob bob;
	private Random rand;
	private int state;

	public World(WorldListener listener) {
		this.listener = listener;
		initObjects();
		generateLevel();
	}

	private void initObjects() {
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		rand = new Random();
		state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {
		/*
		 * generate the zombies, soccers and coins.
		 */
	}

	public void update(float deltaTime, float accelX, float accelY) {
		updateBob(deltaTime, accelX, accelY);
	}

	private void updateBob(float deltaTime, float accelX, float accelY) {
		bob.update(deltaTime, accelX, accelY);
	}

	public Bob getBob() {
		return bob;
	}
}
