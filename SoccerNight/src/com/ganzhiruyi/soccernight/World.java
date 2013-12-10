package com.ganzhiruyi.soccernight;
/**
 * 
 * @author ganzhiruyi
 * World is a class manager the objects in the game,
 * such as check collision, update the object and so on.
 *
 */
public class World {
	public interface WorldListener{
		public void hit();
		public void getCoins();
		public void getSoccer(int type);
	}
	WorldListener listener;
	public World(WorldListener listener){
		this.listener = listener;
		
	}

}
