package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector3;

public class MainScreen implements Screen {
	public static final float SCREEN_HIGHT = 480;
	public static final float SCREEN_WIDTH = 320;
	public static final String STR_SOCCERNight = "Soccer Night";
	public static final String STR_START = "Start";
	public static final String STR_QUIT = "Quit";
	
	Game game;
	OrthographicCamera camera; 
	SpriteBatch batch;
	Vector3 touchPoint;

	public MainScreen(Game game) {
		// init the main screen
		this.game = game;
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HIGHT);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HIGHT / 2, 10);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
	}
	private void update(float delta){
		//define the mainscreen to other screen logic, deal with touch events
		if(Gdx.input.justTouched()){
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		}
	}
	private void draw(float delta){
		//draw the mainScreen UI
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, 0, 0, SCREEN_WIDTH, SCREEN_HIGHT);
		batch.end();
		
		//draw the words in main screen
		batch.enableBlending();
		batch.begin();
		Assets.font.drawMultiLine(batch, STR_SOCCERNight, SCREEN_WIDTH/2, SCREEN_HIGHT-10, 20, HAlignment.CENTER);
		Assets.font.drawMultiLine(batch, STR_START, SCREEN_WIDTH/2, SCREEN_HIGHT/2, 20, HAlignment.CENTER);
		Assets.font.drawMultiLine(batch, STR_QUIT, SCREEN_WIDTH/2, SCREEN_HIGHT/3, 20, HAlignment.CENTER);
		batch.end();
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
