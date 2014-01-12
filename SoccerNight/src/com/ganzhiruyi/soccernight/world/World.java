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
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		rand = new Random();
		state = WORLD_STATE_RUNNING;
	}
	private void initZombies(){
		for(int i = 0;i < 4;i++){
			int edge = rand.nextInt(4);
			addZombie(edge);
		}
	}
	private void addZombie(int edge){
		if(zombies.size() >= 10)
			return;
		float x = 0,y = 0;
		if(edge == 0)
			x = rand.nextInt((int) Config.SCREEN_WIDTH);
		else if(edge == 1)
			y = rand.nextInt((int) Config.SCREEN_HEIGHT); 
		else if(edge == 2){
			x = Config.SCREEN_WIDTH;
			y = rand.nextInt((int) Config.SCREEN_HEIGHT); 
		}
		else{
			x = rand.nextInt((int) Config.SCREEN_WIDTH);
			y = Config.SCREEN_HEIGHT;
		}
		Zombie z = new Zombie(x, y);
		zombies.add(z);
	}

	private void generateLevel() {
		/*
		 * generate the zombies, soccers and coins.
		 */
		initZombies();
	}

	public void update(float deltaTime, float accelX, float accelY) {
		if(state == WORLD_STATE_GAME_OVER)
			return;
		updateBob(deltaTime, accelX, accelY);
		updateZombies(deltaTime);
		checkCollision();
	}

	private void updateBob(float deltaTime, float accelX, float accelY) {
		bob.update(deltaTime, accelX, accelY);
	}
	private void updateZombies(float deltaTime){
		int accelZombie = rand.nextInt(zombies.size());
		for(int i = 0;i < zombies.size();i++){
			Zombie z = zombies.get(i);
			float x = bob.position.x > z.position.x ? 1 : -1;
			float y = bob.position.y > z.position.y ? 1 : -1;
			if(i == accelZombie){
				x += x > 0 ? 3 : -3;
				y += y > 0 ? 3 : -3;
			}
			z.update(deltaTime, x, y);
		}
		if(rand.nextInt() % 50 == 0)
			addZombie(rand.nextInt(4));
	}
	private void checkCollision(){
		for(Zombie z : zombies){
			if(OverlapTester.overlapRectangles(bob.bounds, z.bounds)){
				state = WORLD_STATE_GAME_OVER;
				break;
			}
		}
	}

	public Bob getBob() {
		return bob;
	}
	public List<Zombie> getZombies(){
		return zombies;
	}
	public int getState() {
		return state;
	}
}
