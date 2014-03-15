package com.ganzhiruyi.soccernight.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.ganzhiruyi.soccernight.magic.Fire;
import com.ganzhiruyi.soccernight.magic.Hurricane;
import com.ganzhiruyi.soccernight.magic.Magic;
import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.object.DynamicObject.DyObjectState;
import com.ganzhiruyi.soccernight.soccer.BombSoccer;
import com.ganzhiruyi.soccernight.soccer.LineSoccer;
import com.ganzhiruyi.soccernight.soccer.PaddySoccer;
import com.ganzhiruyi.soccernight.soccer.RoundSoccer;
import com.ganzhiruyi.soccernight.soccer.Soccer;
import com.ganzhiruyi.soccernight.soccer.WaveSoccer;
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
	public static final int LIMIT_NUM_ZOMBIE = 10;
	public static final int LIMIT_NUM_SOCCER = 5;
	public static final int LEVEL_NUM_ZOMBIE = 20;
	public static final int SOCCER_TYPE_NUM = 5;

	public interface WorldListener {
		public void bossAppear();

		public void getCoins();

		public void getSoccer(int type);
	}

	private WorldListener listener;
	private List<Soccer> soccers;
	private List<Zombie> zombies;
	private List<Magic> magics;
	private Bob bob;
	private Random rand;
	private int state;
	private int zombieCount;
	private int score;
	private boolean isPrincessShow;

	public World(WorldListener listener) {
		this.listener = listener;
		initObjects();
		generateLevel();
	}

	private void initObjects() {
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		magics = new ArrayList<Magic>();
		rand = new Random();
		state = WORLD_STATE_RUNNING;
		zombieCount = score = 0;
		isPrincessShow = false;
	}

	private void initZombies() {
		for (int i = 0; i < 2; i++) {
			int edge = rand.nextInt(4);
			addZombie(0, edge);
		}
	}

	private void addZombie(int type, int edge) {
		if (zombies.size() >= LIMIT_NUM_ZOMBIE
				|| zombieCount >= LEVEL_NUM_ZOMBIE)
			return;
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
		else if (type == 2) {
			zombies.add(new Princess(x, y));
			isPrincessShow = true;
			listener.bossAppear();
		}
		zombieCount++;
	}

	private void initSoccers() {
		for (int i = 0; i < SOCCER_TYPE_NUM; i++) {
			addSoccer(i % (SOCCER_TYPE_NUM + 1));
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
		else if (type == 2)
			soccers.add(new RoundSoccer(x, y));
		else if (type == 3)
			soccers.add(new WaveSoccer(x, y));
		else if (type == 4)
			soccers.add(new BombSoccer(x, y));
	}

	private void generateLevel() {
		/*
		 * generate the zombies, soccers and coins.
		 */
		initSoccers();
		initZombies();
	}

	public void update(float deltaTime, float accelX, float accelY) {
		if (state == WORLD_STATE_GAME_OVER || state == WORLD_STATE_NEXT_LEVEL)
			return;
		updateBob(deltaTime, accelX, accelY);
		updateZombies(deltaTime);
		updateMagic(deltaTime);
		addNewObject();
		checkCollision(deltaTime);
	}

	private void updateBob(float deltaTime, float accelX, float accelY) {
		bob.update(deltaTime, accelX, accelY);
	}

	private void updateZombies(float deltaTime) {
		if (zombies.size() == 0) {
			if (zombieCount >= LEVEL_NUM_ZOMBIE && isPrincessShow)
				state = WORLD_STATE_NEXT_LEVEL;
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
					y = 1.5f;
				else
					y = (bob.position.y - z.position.y)
							/ (bob.position.x - z.position.x) * x;
				if (y > 1.5f)
					y = 1.5f;
				else if (y < -1.5f)
					y = -1.5f;
				z.update(deltaTime, x, y);
			} else if (z instanceof Princess) {
				z.update(deltaTime, x, y);
				Vector2 vec = new Vector2(0, 0);
				vec.x = z.isRight ? Magic.VELOCITY : -Magic.VELOCITY;
				int move = ((Princess) z).getMove();
				if (move == Princess.STAB) {
					if (((Princess) z).getHurricaneNum() <= 2) {
						addMagic(0, z.position.x, z.position.y, deltaTime, vec);
						((Princess) z).addHurricaneNum(1);
					}
				} else if (move == Princess.HACK) {
					if (((Princess) z).getFireNum() == 0) {
						addMagic(1, z.position.x, z.position.y, deltaTime, vec);
						vec.y += 2;
						addMagic(1, z.position.x, z.position.y, deltaTime, vec);
						vec.y = -vec.y;
						addMagic(1, z.position.x, z.position.y, deltaTime, vec);
						((Princess) z).addFireNum(3);
					}
				} else if (move == Princess.WALK) {
					((Princess) z).addHurricaneNum(-2);
					((Princess) z).addFireNum(-3);
				}
			}
		}
	}

	private void updateMagic(float deltaTime) {
		for (int i = 0; i < magics.size(); i++) {
			Magic magic = magics.get(i);
			if (magic.getState() == DyObjectState.DEAD)
				magics.remove(i);
			else if (magic.getState() == DyObjectState.MOVING)
				magic.update(deltaTime, magic.velocity.x, magic.velocity.y);
		}
	}

	private void addMagic(int type, float x, float y, float deltaTime,
			Vector2 vec) {
		Magic magic;
		if (type == 0) {
			magic = new Hurricane(x, y);
			magic.update(deltaTime, vec.x, vec.y);
			magics.add(magic);
		} else if (type == 1) {
			magic = new Fire(x, y);
			magic.update(deltaTime, vec.x, vec.y);
			magics.add(magic);
		}
	}

	public void addNewObject() {
		if (!isPrincessShow && zombieCount >= LEVEL_NUM_ZOMBIE - 4) {
			addZombie(2, rand.nextInt(4));
		}
		int nextObject = rand.nextInt() % 50;
		if (nextObject < SOCCER_TYPE_NUM)
			addSoccer(nextObject);
		else if (nextObject == 48)
			addZombie(0, rand.nextInt(4));
		else if (nextObject == 49)
			addZombie(1, rand.nextInt(4));
	}

	private void checkCollision(float deltaTime) {
		collisionZombie();
		collisionSoccer(deltaTime);
		collisionMagic();
	}

	private void collisionZombie() {
		for (Zombie z : zombies) {
			if (OverlapTester.overlapRectangles(bob.bounds, z.bounds)) {
				state = WORLD_STATE_GAME_OVER;
				break;
			}
		}
	}

	private void collisionMagic() {
		for (Magic m : magics) {
			if (OverlapTester.overlapRectangles(bob.bounds, m.bounds)) {
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
					if (z instanceof Princess
							&& ((Princess) z).getState() == DyObjectState.DEAD) {
						zombies.remove(j);
						continue;
					}
					if (OverlapTester.overlapRectangles(s.bounds, z.bounds)) {
						if (z instanceof Princess)
							((Princess) z).blood--;
						else
							zombies.remove(j);
						score++;
					}
				}
				for (int k = 0; k < magics.size(); k++) {
					Magic m = magics.get(k);
					if (OverlapTester.overlapRectangles(s.bounds, m.bounds))
						magics.remove(k);
				}
				s.roll(deltaTime);
			} else if (s.getState() == DyObjectState.DEAD) {
				soccers.remove(i);
			} else if (OverlapTester.overlapRectangles(bob.bounds, s.bounds)) {
				float accelX = bob.velocity.x;
				float accelY = bob.velocity.y;
				if (s instanceof RoundSoccer) {
					if (Math.abs(accelX) > Math.abs(accelY))
						accelY = 0;
					else if (Math.abs(accelX) < Math.abs(accelY))
						accelX = 0;
				}
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

	public List<Magic> getMagics() {
		return magics;
	}

	public int getState() {
		return state;
	}

	public int getScore() {
		return score;
	}
}
