package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;

public class MainScreen implements Screen {
	public static final String STR_SOCCERNIGHT = "Soccer Night";
	public static final String STR_START = "Start";
	public static final String STR_SCORE = "Score";
	public static final String STR_SETTING = "Setting";

	private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextButton btnStart, btnScore, btnSetting;
	float stateTime = 0;
	private Stage stage;

	public MainScreen(Game game) {
		// init the main screen
		this.game = game;
		camera = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				10);
		batch = new SpriteBatch();
	}

	private void initStage() {
		stage = new Stage();
		Image bg = new Image(Assets.backgroundRegion);
		bg.setFillParent(true);
		stage.addActor(bg);
		Table table = new Table();
		table.setFillParent(true);
		table.center();
		Label title = new Label(STR_SOCCERNIGHT, Assets.skin, "normal-text");
		btnStart = new TextButton(STR_START, Assets.skin);
		btnScore = new TextButton(STR_SCORE, Assets.skin);
		btnSetting = new TextButton(STR_SETTING, Assets.skin);
		btnStart.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new GameScreen(game));
			}
		});
		btnScore.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HighScoreScreen(game));
			}
		});
		btnSetting.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		
		table.add(title);
		table.row();
		table.add(btnStart);
		table.row();
		table.add(btnScore);
		table.row();
		table.add(btnSetting);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	private void update(float delta) {
		// define the mainscreen to other screen logic, deal with touch events
	}

	private void draw(float delta) {
		// draw the mainScreen UI
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		stage.act();
		stage.draw();
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		initStage();
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
		stage.dispose();
	}

}
