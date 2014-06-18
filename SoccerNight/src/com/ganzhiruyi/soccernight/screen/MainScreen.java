package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.object.ParticleEffectActor;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.CustomButton;
import com.ganzhiruyi.soccernight.utils.CustomDialog;
import com.ganzhiruyi.soccernight.utils.Settings;

public class MainScreen implements Screen {
	public static final String STR_SOCCERNIGHT = "Soccer Night";
	public static final String STR_START = "Start";
	public static final String STR_SCORE = "Score";
	public static final String STR_SETTING = "Setting";
	public static final String STR_STORY = "Bob is a football fan."
			+ "one day when Bob came to find a witch occupy the stadium,"
			+ "the stadium as she places a magical experiment, various evil experiments."
			+ "In order to regain the stadium, stop the witch experiment, Bob challenged the witch. "
			+ "But a witch with a powerful magic to help, as well as other witches."
			+ "Bob extraordinary difficult to win."
			+ "Fortunately, the soccer also has the magic because the experiment,"
			+ "Bob can use these soccers to defeat the witch.";

	private SoccerNight game;
	private OrthographicCamera camera;
	private CustomButton btnStart, btnScore, btnSetting;
	float stateTime = 0;
	private Stage stage;
	private ParticleEffectActor starEffect;
	private Sound buttonSound;
	private Music mainBgm;
	private CheckBox cbSound;
	private CustomDialog mDialog = null;
	private boolean isDialogShow = false;

	public MainScreen(SoccerNight game) {
		// init the main screen
		this.game = game;
		camera = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				10);
		initParticleEffect();
	}

	private void initParticleEffect() {
		starEffect = new ParticleEffectActor("particles/star.p", "particles");
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
		final int W = width,H = height;
		stage = new Stage();
		Image bg = new Image(Assets.backgroundRegion);
		Image logo = new Image(SoccerNight.mAltas.findRegion("soccernight"));
		Image moon = new Image(SoccerNight.mAltas.findRegion("moon"));
		Image book = new Image(SoccerNight.mAltas.findRegion("book"));
		Image wand = new Image(SoccerNight.mAltas.findRegion("wand"));
		Image record = new Image(SoccerNight.mAltas.findRegion("record"));
		Image clound = new Image(SoccerNight.mAltas.findRegion("clound"));
		clound.setSize(100, 80);
		int offsetY = height / 12, offsetX = width / 30;
		float distY = height - clound.getHeight() - offsetY;
		record.setSize(80, 80);
		float distX = width - record.getWidth() - offsetX;
		record.setPosition(distX, distY);
		/*
		book.setSize(80, 80);
		distX -= (book.getWidth() + offsetX);
		book.setPosition(distX, distY);
		*/
		wand.setSize(80, 80);
		distX -= (wand.getWidth() + offsetX);
		wand.setPosition(distX, distY);

		moon.setSize(200, 200);
		moon.setPosition(offsetX, height - moon.getHeight());
		clound.setPosition(0, distY);
		clound.addAction(Actions.repeat(-1, Actions.sequence(Actions.moveTo(
				distX - clound.getWidth(), distY, 6, Interpolation.linear),
				Actions.moveTo(0, distY, 6, Interpolation.linear))));
		logo.setOrigin(logo.getOriginX() + logo.getWidth() / 2,
				logo.getOriginY() + logo.getHeight() / 2);
		logo.addAction(Actions.repeat(1, Actions.sequence(
				Actions.rotateTo(20, 0.5f), Actions.rotateTo(0, 0.5f),
				Actions.rotateTo(-20, 0.5f), Actions.rotateTo(0, 0.5f))));
		bg.setFillParent(true);
		stage.addActor(bg);
		Table table = new Table();
		table.setFillParent(true);
		btnStart = new CustomButton(STR_START, Assets.skin);
		btnScore = new CustomButton(STR_SCORE, Assets.skin);
		btnSetting = new CustomButton(STR_SETTING, Assets.skin);
		CheckBoxStyle style = new CheckBoxStyle();
		style.checkboxOn = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("sound_on"));
		style.checkboxOff = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("sound_off"));
		style.font = Assets.font;
		cbSound = new CheckBox("", style);
		cbSound.setChecked(Settings.getInstance().isSoundEnable());
		wand.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setTutorialScreen();
				return false;
			}
		});
		record.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				final Window w = new Window("STORY", Assets.skin);
				w.setSize(600, 400);
				Label l = new Label(STR_STORY, Assets.skin);
				l.setWrap(true);
				Table container = new Table();
				container.setFillParent(true);
				Table t = new Table();
				ScrollPane scroll = new ScrollPane(t, Assets.skin);
				scroll.setScrollingDisabled(false, false);
				t.add(l).center().width(400);
				container.add(scroll).fill().expand();
				container.row();
				container.defaults().center().pad(20);
				CustomButton close = new CustomButton("close", Assets.skin);
				close.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						return true;
					}
					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						stage.getRoot().removeActor(w);
					}
				});
				container.add(close);
				w.addActor(container);
				w.setPosition((W - w.getWidth())/2,  (H-w.getHeight())/2);
				stage.addActor(w);
				return false;
			}
		});
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
		bg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				starEffect.setEffectPosition(x, y);
				return false;
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
		stage.addActor(moon);
		stage.addActor(clound);
		stage.addActor(wand);
		//stage.addActor(book);
		stage.addActor(record);
		stage.addActor(table);
		stage.addActor(starEffect);
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
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(new InputMultiplexer(createBackProcessor(),
				stage));
	}

	@Override
	public void show() {
	}

	private InputProcessor createBackProcessor() {
		InputProcessor backProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK) {
					if (!isDialogShow) {
						createDialog();
						mDialog.show(stage);
						isDialogShow = true;
					}
				}
				return false;
			}
		};
		return backProcessor;
	}

	private void createDialog() {
		mDialog = new CustomDialog("Exit game", Assets.skin);
		mDialog.text("You want to leave?").button("YES", new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			};

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				isDialogShow = false;
				Gdx.app.exit();
			}
		}).button("NO", new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				isDialogShow = false;
			}
		});

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
