package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MainScreen implements Screen {
	public static final float SCREEN_HIGHT = 480;
	public static final float SCREEN_WIDTH = 320;
	Game game;
	OrthographicCamera camera; 
	SpriteBatch batch;
	Vector3 touchPoint;

	public MainScreen(Game game) {
		// init the main screen
		this.game = game;
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HIGHT);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HIGHT / 2, 0);
		batch = new SpriteBatch();
	}
	private void update(float delta){
		//define the mainscreen to other screen logic, deal with touch events
		if(Gdx.input.justTouched()){
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
		}
	}
	private void draw(float delta){
		//draw the mainScreen UI
		
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
