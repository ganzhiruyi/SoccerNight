package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector3;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.world.World;
import com.ganzhiruyi.soccernight.world.World.WorldListener;
import com.ganzhiruyi.soccernight.world.WorldRenderer;

public class GameScreen implements Screen {
	public static final int GAME_READY = 0;
	public static final int GAME_PAUSED = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_LEVEL_END = 4;
	Game game;
	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batch;
	WorldListener listener;
	World world;
	WorldRenderer renderer;

	public GameScreen(Game game) {
		this.game = game;
		guiCam = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		guiCam.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				0);
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
		state = GAME_RUNNING;
	}

	public void update(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;
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
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainScreen(game));
		}
	}

	private void updateLevelEnd() {

	}

	private void updatePaused() {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning(float delta) {
		if (Gdx.input.justTouched()) {
			state = GAME_PAUSED;
			return;
		}
		ApplicationType appType = Gdx.app.getType();
		// should work also with
		// Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
		float accelX = 0, accelY = 0;
		if (appType == ApplicationType.Android
				|| appType == ApplicationType.iOS) {
			// because the screen is landscape
			accelX = Gdx.input.getAccelerometerY();
			accelY = -Gdx.input.getAccelerometerX();
		} else {
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
				accelX = -5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
				accelX = 5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
				accelY = -5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_UP))
				accelY = 5f;

		}
		if (world.getState() == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			return;
		}
		world.update(delta, accelX, accelY);
	}

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void draw(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render();

		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		batch.enableBlending();
		batch.begin();
		switch (state) {
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
		Assets.font.drawMultiLine(batch, "Game over!", Config.SCREEN_WIDTH / 2,
				Config.SCREEN_HEIGHT / 2, 20, HAlignment.CENTER);
	}

	private void drawPaused() {

	}

	private void drawRunning(float delta) {

	}

	private void drawReady() {

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
