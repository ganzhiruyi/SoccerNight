package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.world.World;
import com.ganzhiruyi.soccernight.world.World.WorldListener;
import com.ganzhiruyi.soccernight.world.WorldRenderer;

public class GameScreen implements Screen {
	public static final	int GAME_READY = 0;
	public static final	int GAME_PAUSED = 1;
	public static final	int GAME_RUNNING = 2;
	public static final	int GAME_OVER = 3;
	public static final	int GAME_LEVEL_END = 4;
	Game game;
	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batch;
	WorldListener listener;
	World world;
	WorldRenderer renderer;
	
	public GameScreen(Game game){
		this.game = game;
		guiCam = new OrthographicCamera(Config.SCREEN_WIDTH, Config.SCREEN_HIGHT);
		guiCam.position.set(Config.SCREEN_WIDTH/2, Config.SCREEN_HIGHT/2, 0);
		touchPoint = new Vector3();
		batch = new SpriteBatch();
		listener = new WorldListener() {
			
			@Override
			public void hit() {
				
			}
			
			@Override
			public void getSoccer(int type) {
				
			}
			
			@Override
			public void getCoins() {
				
			}
		};
		world = new World(listener);
		renderer = new WorldRenderer(batch, world);
	}
	public void update (float delta) {
		if (delta > 0.1f) delta = 0.1f;
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(delta);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}
	
	private void updateGameOver() {
		
	}
	private void updateLevelEnd() {
		
	}
	private void updatePaused() {
		if(Gdx.input.justTouched()){
			state = GAME_RUNNING;
		}
	}
	private void updateRunning(float delta) {
		if(Gdx.input.justTouched()){
			state = GAME_PAUSED;
			return;
		}
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		System.out.println("accelx: " + accelX + ",accelY: " + accelY);
		if(Math.abs(accelX) < 0.01 && Math.abs(accelY) < 0.01) return;
		world.update(delta, accelX, accelY);
	}
	private void updateReady() {
		if(Gdx.input.justTouched()){
			state = GAME_RUNNING;
		}
	}
	private void draw(float delta){
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render();

		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		batch.enableBlending();
		batch.begin();
		switch (state){
		case GAME_READY:
			drawReady();
			break;
		case GAME_RUNNING:
			drawRunning(delta);
			break;
		case GAME_PAUSED:
			drawPaused();
			break;
		case GAME_OVER:
			drawOver();
			break;
		case GAME_LEVEL_END:
			drawLevelEnd();
			break;
		}
		batch.end();
	}
	private void drawLevelEnd() {
		
	}
	private void drawOver() {
		
	}
	private void drawPaused() {
		
	}
	private void drawRunning(float delta) {
	}
	private void drawReady(){
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}
	
	@Override 
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
	

}
