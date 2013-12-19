package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.world.OverlapTester;

public class MainScreen implements Screen {
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
		camera = new OrthographicCamera(Config.SCREEN_WIDTH, Config.SCREEN_HIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HIGHT / 2, 10);
		batch = new SpriteBatch();
		touchPoint = new Vector3();
	}
	private void update(float delta){
		//define the mainscreen to other screen logic, deal with touch events
		if(Gdx.input.justTouched()){
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			Rectangle startRec = new Rectangle(Config.SCREEN_WIDTH/2-40, Config.SCREEN_HIGHT/2-40, 80, 80);
			if(OverlapTester.pointInRectangle(startRec, touchPoint.x, touchPoint.y)){
				game.setScreen(new GameScreen(game));
				return;
			}
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
		batch.draw(Assets.backgroundRegion, Config.SCREEN_WIDTH/3, Config.SCREEN_HIGHT/3*2, Config.SCREEN_WIDTH/3,Config.SCREEN_HIGHT/3);
		batch.end();
		
		//draw the words in main screen
		batch.enableBlending();
		batch.begin();
		Assets.font.drawMultiLine(batch, STR_SOCCERNight, Config.SCREEN_WIDTH/2, Config.SCREEN_HIGHT-10, 20, HAlignment.CENTER);
		Assets.font.drawMultiLine(batch, STR_START, Config.SCREEN_WIDTH/2, Config.SCREEN_HIGHT/2, 20, HAlignment.CENTER);
		Assets.font.drawMultiLine(batch, STR_QUIT, Config.SCREEN_WIDTH/2, Config.SCREEN_HIGHT/3, 20, HAlignment.CENTER);
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
