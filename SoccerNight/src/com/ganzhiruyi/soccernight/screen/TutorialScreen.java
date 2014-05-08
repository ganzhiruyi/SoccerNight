package com.ganzhiruyi.soccernight.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.object.DynamicObject.DyObjectState;
import com.ganzhiruyi.soccernight.soccer.BombSoccer;
import com.ganzhiruyi.soccernight.soccer.LineSoccer;
import com.ganzhiruyi.soccernight.soccer.PaddySoccer;
import com.ganzhiruyi.soccernight.soccer.RoundSoccer;
import com.ganzhiruyi.soccernight.soccer.Soccer;
import com.ganzhiruyi.soccernight.soccer.WaveSoccer;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.CustomDialog;
import com.ganzhiruyi.soccernight.world.OverlapTester;
import com.ganzhiruyi.soccernight.zombie.Tracker;
import com.ganzhiruyi.soccernight.zombie.Zombie;

public class TutorialScreen implements Screen {
	public static final int TUTORIAL_PAUSED = 0;
	public static final int TUTORIAL_MOVING = 1;
	public static final int TUTORIAL_OVER = 2;
	private static final int INTERVAL = 3;
	private Bob bob;
	private List<Soccer> soccers;
	private List<Zombie> zombies;
	private SoccerNight game;
	private OrthographicCamera guiCam;
	private SpriteBatch batch;
	private int state;
	private float stateTime, preStateTime;
	private TextureRegion phoneRegion;
	private int scene;
	private CustomDialog mDialog;
	private boolean isDialogShow;
	private Stage stage;
	private Label l0, l1, l2, l3;

	public TutorialScreen(SoccerNight game) {
		this.game = game;
	}

	private void init() {
		guiCam = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		guiCam.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				0);
		if (batch != null)
			batch.dispose();
		if (stage != null)
			stage.dispose();
		l0 = l1 = l2 = l3 = null;
		batch = new SpriteBatch();
		state = TUTORIAL_PAUSED;
		stateTime = preStateTime = 0f;
		scene = 0;
		phoneRegion = SoccerNight.mAltas.findRegion("phone");
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		stage = new Stage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		isDialogShow = false;
		createDialog();
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
		stateTime += delta;
	}

	private void update(float delta) {
		switch (state) {
		case TUTORIAL_PAUSED:
			if (scene == 0 && stateTime - preStateTime >= INTERVAL)
				toMoving();
			else if (scene == 1 && stateTime - preStateTime >= INTERVAL)
				toMoving();
			else if (scene == 2 && stateTime - preStateTime >= INTERVAL)
				toMoving();
			else if (scene == 3 && stateTime - preStateTime >= INTERVAL)
				state = TUTORIAL_OVER;
			break;
		case TUTORIAL_MOVING:
			updateRunning(delta);
			break;
		case TUTORIAL_OVER:
			break;
		default:
			break;
		}
	}

	private void toMoving() {
		state = TUTORIAL_MOVING;
		preStateTime = stateTime;
	}

	private void updateRunning(float delta) {
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
		bob.update(delta, accelX, accelY);
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			float x = bob.position.x > z.position.x ? 1 : -1;
			float y = bob.position.y > z.position.y ? 1 : -1;
			if (z instanceof Tracker)
				z.update(delta, x, y);
		}
		if (scene == 0 && isMoving(bob.position)) {
			state = TUTORIAL_PAUSED;
			preStateTime = stateTime;
			scene = 1;
			zombies.add(new Tracker(Config.SCREEN_WIDTH - Zombie.ZOMBIE_WIDTH,
					Config.SCREEN_HEIGHT / 2));
		} else if (scene == 1) {
			if (collisionZombie())
				state = TUTORIAL_OVER;
			else if (stateTime - preStateTime >= 4) {
				state = TUTORIAL_PAUSED;
				preStateTime = stateTime;
				scene = 2;
				soccers.add(new RoundSoccer(Config.SCREEN_WIDTH / 2,
						Config.SCREEN_HEIGHT / 2));
			}
		} else if (scene == 2) {
			if (soccers.size() <= 0 && zombies.size() > 0)
				soccers.add(new RoundSoccer(Config.SCREEN_WIDTH / 2,
						Config.SCREEN_HEIGHT / 2));
			collisionSoccer(delta);
			if (collisionZombie())
				state = TUTORIAL_OVER;
			else if (Soccer2Zombie()) {
				state = TUTORIAL_PAUSED;
				preStateTime = stateTime;
				scene = 3;
			}
		}

	}

	private boolean collisionZombie() {
		for (Zombie z : zombies)
			if (OverlapTester.overlapRectangles(bob.bounds, z.bounds))
				return true;
		return false;
	}

	private void collisionSoccer(float deltaTime) {
		for (int i = 0; i < soccers.size(); i++) {
			Soccer s = soccers.get(i);
			if (s.getState() == DyObjectState.MOVING) {
				s.roll(deltaTime);
			} else if (s.getState() == DyObjectState.DEAD) {
				soccers.remove(i);
			} else if (OverlapTester.overlapRectangles(bob.bounds, s.bounds)) {
				float accelX = bob.velocity.x;
				float accelY = bob.velocity.y;
				if (s instanceof RoundSoccer) {
					if (Math.abs(accelX) > Math.abs(accelY))
						accelY = 0;
					else if (Math.abs(accelX) < Math.abs(accelY))
						accelX = 0;
				}
				s.update(deltaTime, accelX, accelY);
			}
		}
	}

	private boolean Soccer2Zombie() {
		for (Soccer s : soccers)
			if (s.getState() == DyObjectState.MOVING)
				for (Zombie z : zombies)
					if (OverlapTester.overlapRectangles(s.bounds, z.bounds)) {
						zombies.remove(z);
						return true;
					}
		return false;
	}

	private boolean isMoving(Vector2 v) {
		return Math.abs(v.x - Config.SCREEN_WIDTH / 2) >= 100
				|| Math.abs(v.y - Config.SCREEN_HEIGHT / 2) >= 100;
	}

	private void draw(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		batch.enableBlending();
		batch.begin();
		batch.draw(Assets.level_1_bg, 0, 0, Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		drawBob();
		drawZombies();
		drawSoccers();
		switch (state) {
		case TUTORIAL_MOVING:
			break;
		case TUTORIAL_PAUSED:
			if (scene == 0) {
				if (l0 == null) {
					stage.clear();
					Image phone = new Image(phoneRegion);
					phone.setOrigin(phone.getOriginX() + phone.getWidth() / 2,
							phone.getOriginY() + phone.getHeight() / 2);
					phone.addAction(Actions.sequence(
							Actions.rotateTo(20, 0.5f),
							Actions.rotateTo(0, 0.5f),
							Actions.rotateTo(-20, 0.5f),
							Actions.rotateTo(0, 0.5f)));
					Table t = new Table();
					t.setFillParent(true);
					t.add(phone).center();
					t.row();
					l0 = new Label("Control the role to move!", Assets.skin,
							"title-text");
					t.add(l0);
					stage.addActor(t);
				}
			} else if (scene == 1) {
				if (l1 == null)
					drawLable(l1,
							"OK! Now control the role, \n avoid the girls !");
			} else if (scene == 2) {
				if (l2 == null)
					drawLable(l2,
							"Good! Control the role, \nget the soccer and destroy girls !");
			} else if (scene == 3) {
				if (l3 == null)
					drawLable(l3, "Perfect!\nWish you get the playgound back !");
			}
			stage.act();
			stage.draw();
			break;
		case TUTORIAL_OVER:
			if (!isDialogShow) {
				stage.clear();
				Table t = new Table();
				t.setFillParent(true);
				t.add(mDialog).center();
				stage.addActor(t);
				isDialogShow = true;
				Gdx.input.setInputProcessor(stage);
			}
			stage.act();
			stage.draw();
			break;
		default:
			break;
		}
		batch.end();

	}

	private void drawLable(Label l, String str) {
		stage.clear();
		Table t = new Table();
		t.setFillParent(true);
		l = new Label(str, Assets.skin, "title-text");
		t.add(l).center();
		stage.addActor(t);
	}

	private void drawZombies() {
		if (zombies == null)
			return;
		for (Zombie z : zombies) {
			TextureRegion region;
			float stateTime = z.getStateTime();
			float x = z.position.x;
			float y = z.position.y;
			switch (z.getState()) {
			case IDLE:
				region = z.isRight ? Assets.aniTracker.idleR
						: Assets.aniTracker.idleL;
				batch.draw(region, x, y, z.width, z.height);
				break;
			case MOVING:
				float vx = z.velocity.x;
				if (vx < 0) {
					region = Assets.aniTracker.aniL.getKeyFrame(stateTime);
					batch.draw(region, x, y, z.width, z.height);
				} else if (vx > 0) {
					region = Assets.aniTracker.aniR.getKeyFrame(stateTime);
					batch.draw(region, x, y, z.width, z.height);
				}
				break;
			default:
				break;
			}
		}
	}

	private void drawBob() {
		TextureRegion region;
		float stateTime = bob.getStateTime();
		float x = bob.position.x;
		float y = bob.position.y;
		switch (bob.getState()) {
		case IDLE:
			region = bob.isRight ? Assets.aniBobIdleR : Assets.aniBobIdleL;
			batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			break;
		case MOVING:
			float vx = bob.velocity.x;
			if (vx < 0) {
				region = Assets.aniBobL.getKeyFrame(stateTime);
				batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			} else {
				region = Assets.aniBobR.getKeyFrame(stateTime);
				batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			}
			break;
		default:
			break;
		}
	}

	private void drawSoccers() {
		if (soccers == null)
			return;
		for (Soccer s : soccers) {
			TextureRegion region = null;
			float stateTime = s.getStateTime();
			float x = s.position.x;
			float y = s.position.y;
			switch (s.getState()) {
			case IDLE:
				if (s instanceof LineSoccer)
					region = Assets.lineSocIdle;
				else if (s instanceof PaddySoccer)
					region = Assets.paddySocIdle;
				else if (s instanceof RoundSoccer)
					region = Assets.roundSocIdle;
				else if (s instanceof WaveSoccer)
					region = Assets.waveSocIdle;
				else if (s instanceof BombSoccer)
					region = Assets.bombSocIdle;
				batch.draw(region, x, y, s.width, s.height);
				break;
			case MOVING:
				if (s instanceof LineSoccer)
					region = Assets.aniRedSoc.getKeyFrame(stateTime);
				else if (s instanceof PaddySoccer)
					region = Assets.aniBlueSoc.getKeyFrame(stateTime);
				else if (s instanceof RoundSoccer)
					region = Assets.aniRoundSoc.getKeyFrame(stateTime);
				else if (s instanceof WaveSoccer)
					region = Assets.aniWaveSoc.getKeyFrame(stateTime);
				else if (s instanceof BombSoccer)
					region = Assets.aniBombSoc.getKeyFrame(stateTime);
				batch.draw(region, x, y, s.width, s.height);
				break;
			default:
				break;
			}
		}
	}

	private void createDialog() {
		mDialog = new CustomDialog("TUTORIAL IS OVER!", Assets.skin);
		mDialog.text("CONTINUE TUTORIAL?").button("YES", new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			};

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mDialog.cancel();
				init();
			}
		}).button("NO", new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mDialog.cancel();
				game.setMainScreen();
			}
		});

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
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
