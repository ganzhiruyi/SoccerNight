package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.Settings;

public class MainScreen implements Screen {
	public static final String STR_SOCCERNIGHT = "Soccer Night";
	public static final String STR_START = "Start";
	public static final String STR_SCORE = "Score";
	public static final String STR_SETTING = "Setting";

	private SoccerNight game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextButton btnStart, btnScore, btnSetting;
	float stateTime = 0;
	private Stage stage;
	private ParticleEffect starEffect;
	private Sound buttonSound;
	private Music mainBgm;
	private CheckBox cbSound;

	public MainScreen(SoccerNight game) {
		// init the main screen
		this.game = game;
		camera = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				10);
		batch = new SpriteBatch();
	}

	private void initParticleEffect() {
		starEffect = new ParticleEffect();
		starEffect.load(Gdx.files.internal("particles/star.p"),
				Gdx.files.internal("particles"));
	}

	private void initMusic() {
		buttonSound = SoccerNight.getAssetManager().get("media/button.wav",
				Sound.class);
		mainBgm = SoccerNight.getAssetManager().get("media/main_bgm.wav",
				Music.class);
		mainBgm.setLooping(true);
		mainBgm.setVolume(0.5f);
	}

	private void update(float delta) {
		// Settings.getInstance().setSoundEnable(cbSound.isChecked());
	}

	private void draw(float delta) {
		// draw the mainScreen UI
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
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
		stage = new Stage(480, 320);
		Image bg = new Image(Assets.backgroundRegion);
		Image logo = new Image(SoccerNight.mAltas.findRegion("soccernight"));
		bg.setFillParent(true);
		stage.addActor(bg);
		Table table = new Table();
		table.setFillParent(true);
		Drawable up, down;
		up = new TextureRegionDrawable(SoccerNight.mAltas.findRegion("btn"));
		down = new TextureRegionDrawable(SoccerNight.mAltas.findRegion("btn_press"));
		TextButtonStyle btnStyle = new TextButtonStyle(up, down, down, Assets.font);
		btnStart = new TextButton(STR_START, btnStyle);
		btnScore = new TextButton(STR_SCORE, btnStyle);
		btnSetting = new TextButton(STR_SETTING, btnStyle);
		CheckBoxStyle style = new CheckBoxStyle();
		style.checkboxOn = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("sound_on"));
		style.checkboxOff = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("sound_off"));
		style.font = Assets.font;
		cbSound = new CheckBox("", style);
		cbSound.setChecked(Settings.getInstance().isSoundEnable());
		btnStart.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Settings.getInstance().playSound(buttonSound);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mainBgm.stop();
				game.setGameScreen();
			}
		});
		btnScore.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Settings.getInstance().playSound(buttonSound);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mainBgm.stop();
				game.setHighScoreScreen();
			}
		});
		btnSetting.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Settings.getInstance().playSound(buttonSound);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mainBgm.stop();
				game.setSettingScreen();
			}
		});
		cbSound.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Settings.getInstance().setSoundEnable(cbSound.isChecked());
				if (cbSound.isChecked()) {
					mainBgm.play();
				} else {
					mainBgm.stop();
				}
			}
		});
		table.add(logo).top();
		table.row();
		table.defaults().width(200).height(80).pad(10).center();
		table.add(btnStart);
		table.row();
		table.add(btnScore);
		table.row();
		table.add(btnSetting);
		table.row();
		table.add(cbSound).bottom().right().expandX();
		table.row();
		stage.addActor(table);
		float x = 0;
		for (int i = 0; i < 30; i++) {
			Image grass = new Image(SoccerNight.mAltas.findRegion("grass"));
			grass.setPosition(x, 0);
			grass.setWidth(60);
			grass.setHeight(60);
			stage.addActor(grass);
			x += 55;
		}
		initMusic();
		Settings.getInstance().playMusic(mainBgm);
		Gdx.input.setInputProcessor(stage);
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
