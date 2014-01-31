package com.ganzhiruyi.soccernight.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.object.DynamicObject.DyObjectState;
import com.ganzhiruyi.soccernight.soccer.LineSoccer;
import com.ganzhiruyi.soccernight.soccer.PaddySoccer;
import com.ganzhiruyi.soccernight.soccer.Soccer;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.zombie.Knight;
import com.ganzhiruyi.soccernight.zombie.Princess;
import com.ganzhiruyi.soccernight.zombie.Tracker;
import com.ganzhiruyi.soccernight.zombie.Zombie;

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
	public static final int LIMIT_NUM_ZOMBIE = 5;
	public static final int LIMIT_NUM_SOCCER = 4;
	public static final int LEVEL_NUM_ZOMBIE = 10;

	public interface WorldListener {
		public void hit();

		public void getCoins();

		public void getSoccer(int type);
	}

	WorldListener listener;
	private List<Soccer> soccers;
	private List<Zombie> zombies;
	private Bob bob;
	private Princess princess;
	private Random rand;
	private int state;
	private int zombieCount = 0;
	private int score = 0;

	public World(WorldListener listener) {
		this.listener = listener;
		initObjects();
		generateLevel();
	}

	private void initObjects() {
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		rand = new Random();
		state = WORLD_STATE_RUNNING;
	}

	private void initZombies() {
		for (int i = 0; i < 2; i++) {
			int edge = rand.nextInt(4);
			addZombie(0, edge);
		}
		addZombie(2, rand.nextInt(4));
	}

	private void addZombie(int type, int edge) {
		if (zombies.size() >= LIMIT_NUM_ZOMBIE
				|| zombieCount >= LEVEL_NUM_ZOMBIE)
			return;
		zombieCount++;
		float x = 0, y = 0;
		if (edge == 0)
			x = rand.nextInt((int) Config.SCREEN_WIDTH);
		else if (edge == 1)
			y = rand.nextInt((int) Config.SCREEN_HEIGHT);
		else if (edge == 2) {
			x = Config.SCREEN_WIDTH;
			y = rand.nextInt((int) Config.SCREEN_HEIGHT);
		} else {
			x = rand.nextInt((int) Config.SCREEN_WIDTH);
			y = Config.SCREEN_HEIGHT;
		}
		if (type == 0)
			zombies.add(new Tracker(x, y));
		else if (type == 1)
			zombies.add(new Knight(x, y));
		else if (type == 2)
			zombies.add(new Princess(x, y));
	}

	private void initSoccers() {
		for (int i = 0; i < 4; i++) {
			addSoccer(i % 2);
		}
	}

	private void addSoccer(int type) {
		if (soccers.size() >= LIMIT_NUM_SOCCER)
			return;
		float w = Bob.BOB_WIDTH, h = Bob.BOB_HEIGHT;
		int x = rand.nextInt((int) (Config.SCREEN_WIDTH - w));
		int y = rand.nextInt((int) (Config.SCREEN_HEIGHT - h));
		if (x < w)
			x = (int) w;
		if (y < h)
			y = (int) h;
		if (type == 0)
			soccers.add(new LineSoccer(x, y));
		else if (type == 1)
			soccers.add(new PaddySoccer(x, y));
	}

	private void generateLevel() {
		/*
		 * generate the zombies, soccers and coins.
		 */
		initSoccers();
		initZombies();
	}

	public void update(float deltaTime, float accelX, float accelY) {
		if (state == WORLD_STATE_GAME_OVER)
			return;
		updateBob(deltaTime, accelX, accelY);
		updateZombies(deltaTime);
		addNewObject();
		checkCollision(deltaTime);
	}

	private void updateBob(float deltaTime, float accelX, float accelY) {
		bob.update(deltaTime, accelX, accelY);
	}

	private void updateZombies(float deltaTime) {
		if (zombies.size() == 0) {
			if (zombieCount >= LEVEL_NUM_ZOMBIE && princess.blood <= 0) {
				state = WORLD_STATE_NEXT_LEVEL;
			}
			return;
		}
		int accelZombie = rand.nextInt(zombies.size());
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			float x = bob.position.x > z.position.x ? 1 : -1;
			float y = bob.position.y > z.position.y ? 1 : -1;
			if (z instanceof Tracker) {
				if (i == accelZombie) {
					x += x > 0 ? 1 : -1;
					y += y > 0 ? 1 : -1;
				}
				z.update(deltaTime, x, y);
			} else if (z instanceof Knight) {
				if (Math.abs(bob.position.x - z.position.x) < 0.1)
					y = 2;
				else
					y = (bob.position.y - z.position.y)
							/ (bob.position.x - z.position.x) * x;
				z.update(deltaTime, x, y);
			} else if (z instanceof Princess) {
				int m = rand.nextInt(100);
				if (m < 50)
					((Princess) z).setMove(Princess.WALK);
				else if (m < 51)
					((Princess) z).setMove(Princess.HACK);
				else if (m < 99)
					((Princess) z).setMove(Princess.WALK);
				else
					((Princess) z).setMove(Princess.STAB);
				z.update(deltaTime, x, y);
			}
		}
	}

	private void addNewObject() {
		int nextObject = rand.nextInt() % 50;
		if (nextObject <= 1)
			addSoccer(nextObject);
		else if (nextObject == 2)
			addZombie(0, rand.nextInt(4));
		else if (nextObject == 3)
			addZombie(1, rand.nextInt(4));
	}

	private void checkCollision(float deltaTime) {
		collisionZombie();
		collisionSoccer(deltaTime);
	}

	private void collisionZombie() {
		for (Zombie z : zombies) {
			if (OverlapTester.overlapRectangles(bob.bounds, z.bounds)) {
				state = WORLD_STATE_GAME_OVER;
				break;
			}
		}
	}

	private void collisionSoccer(float deltaTime) {
		for (int i = 0; i < soccers.size(); i++) {
			Soccer s = soccers.get(i);
			if (s.getState() == DyObjectState.MOVING) {
				for (int j = 0; j < zombies.size(); j++) {
					Zombie z = zombies.get(j);
					if (OverlapTester.overlapRectangles(s.bounds, z.bounds)) {
						if (z instanceof Princess) {
							((Princess) z).blood--;
							if (((Princess) z).blood <= 0) {
								((Princess) z).setMove(Princess.DEAD);
							}
						} else
							zombies.remove(j);
						score++;
					}
				}
				s.roll(deltaTime);
			} else if (s.getState() == DyObjectState.DEAD) {
				soccers.remove(i);
			} else if (OverlapTester.overlapRectangles(bob.bounds, s.bounds)) {
				float accelX = bob.velocity.x;
				float accelY = bob.velocity.y;
				s.update(deltaTime, accelX, accelY);
			}
		}
	}

	public Bob getBob() {
		return bob;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}

	public List<Soccer> getSoccers() {
		return soccers;
	}

	public int getState() {
		return state;
	}

	public int getScore() {
		return score;
	}
}
