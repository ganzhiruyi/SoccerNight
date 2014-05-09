package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.Settings;
import com.ganzhiruyi.soccernight.world.World;
import com.ganzhiruyi.soccernight.world.World.WorldListener;
import com.ganzhiruyi.soccernight.world.WorldRenderer;

public class GameScreen implements Screen {
	public static final int GAME_READY = 0;
	public static final int GAME_PAUSED = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_LEVEL_END = 4;
	private SoccerNight game;
	private int state;
	private OrthographicCamera guiCam;
	private SpriteBatch batch;
	private WorldListener listener;
	private World world;
	private WorldRenderer renderer;
	private Music bgMusic;
	private Sound bossAppearSound, pauseSound, successSound, gameoverSound;
	private Stage stage;
	private boolean isShowMenu, isNeedStage;
	private Table mMenu;

	public GameScreen(SoccerNight game) {
		this.game = game;
	}

	private void init() {
		guiCam = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		guiCam.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				0);
		stage = new Stage();
		mMenu = new Table();
		mMenu.setFillParent(true);
		/*Image bg = new Image(Assets.level_1_bg);
		bg.setFillParent(true);
		bg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				state = GAME_PAUSED;
				bgMusic.pause();
				Settings.getInstance().playSound(pauseSound);
				return false;
			}
		});*/
		batch = stage.getSpriteBatch();
		//stage.addActor(bg);
		listener = new WorldListener() {

			@Override
			public void getSoccer(int type) {

			}

			@Override
			public void getCoins() {
			}

			@Override
			public void bossAppear() {
				bossAppearSound.play(1f);
			}
		};
		world = new World(listener);
		renderer = new WorldRenderer(batch, world);
		state = GAME_RUNNING;
		isShowMenu = isNeedStage = false;
		initAssets();
		Gdx.input.setInputProcessor(stage);
	}

	private void initAssets() {
		AssetManager manager = SoccerNight.getAssetManager();
		bossAppearSound = manager.get("media/appear.wav", Sound.class);
		pauseSound = manager.get("media/pause.wav", Sound.class);
		gameoverSound = manager.get("media/gameover.wav", Sound.class);
		successSound = manager.get("media/success.wav", Sound.class);
		bgMusic = manager.get("media/game_bgm.mp3", Music.class);
		bgMusic.setLooping(true);
		bgMusic.setVolume(0.6f);
		Settings.getInstance().playMusic(bgMusic);
	}

	public void update(float delta) {
		world.addNewObject();
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
			game.setMainScreen();
		}
	}

	private void updateLevelEnd() {
		if (Gdx.input.justTouched()) {
			game.setMainScreen();
		}
	}

	private void updatePaused() {
		/*
		 * if (Gdx.input.justTouched()) { state = GAME_RUNNING;
		 * Settings.getInstance().playMusic(bgMusic); pauseSound.stop(); }
		 */
	}

	private void updateRunning(float delta) {
		/*
		 * if (Gdx.input.isTouched()) { state = GAME_PAUSED; bgMusic.pause();
		 * Settings.getInstance().playSound(pauseSound); return; }
		 */
		ApplicationType appType = Gdx.app.getType();
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
			Settings.getInstance().save(world.getScore());
			bgMusic.stop();
			Settings.getInstance().playSound(gameoverSound);
			return;
		} else if (world.getState() == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
			Settings.getInstance().save(world.getScore());
			bgMusic.stop();
			Settings.getInstance().playSound(successSound);
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
		stage.act();
		stage.draw();
	}

	private void drawLevelEnd() {
		String text = "You win!\n Score: " + world.getScore();
		Assets.font.drawMultiLine(batch, text, Config.SCREEN_WIDTH / 2,
				Config.SCREEN_HEIGHT / 2, 20, HAlignment.CENTER);
	}

	private void drawOver() {
		String text = "Game over!\n Score: " + world.getScore();
		Assets.font.drawMultiLine(batch, text, Config.SCREEN_WIDTH / 2,
				Config.SCREEN_HEIGHT / 2, 20, HAlignment.CENTER);
	}

	private void drawPaused() {
		if (!isShowMenu) {
			Image home = new Image(SoccerNight.mAltas.findRegion("home"));
			Image resume = new Image(SoccerNight.mAltas.findRegion("resume"));
			Image refresh = new Image(SoccerNight.mAltas.findRegion("refresh"));
			home.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out
							.println("------------------click home :" + state);
					game.setMainScreen();
					isShowMenu = isNeedStage = false;
					mMenu.clearChildren();
					return false;
				}
			});
			resume.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					state = GAME_RUNNING;
					System.out.println("------------------click resume :"
							+ state);
					Settings.getInstance().playMusic(bgMusic);
					pauseSound.stop();
					isShowMenu = isNeedStage = false;
					mMenu.clearChildren();
					return false;
				}
			});
			refresh.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("------------------click refresh :"
							+ state);
					game.setGameScreen();
					isShowMenu = isNeedStage = false;
					mMenu.clearChildren();
					return false;
				}
			});
			mMenu.add(home).pad(20);
			mMenu.add(refresh).pad(20);
			mMenu.add(resume).center().pad(20);
			stage.addActor(mMenu);
			isShowMenu = isNeedStage = true;
		}
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
		stage.setViewport(width, height);
	}

	@Override
	public void show() {
		init();
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
