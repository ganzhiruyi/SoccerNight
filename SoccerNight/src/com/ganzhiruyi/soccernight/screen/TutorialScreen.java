package com.ganzhiruyi.soccernight.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.soccer.Soccer;
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
	private Bob bob;
	private List<Soccer> soccers;
	private List<Zombie> zombies;
	private SoccerNight game;
	private OrthographicCamera guiCam;
	private SpriteBatch batch;
	private int state;
	private float stateTime, preStateTime;
	private TextureRegion phoneRegion;
	private float phoneX, phoneY;
	private int scene;
	private Random rand;
	private CustomDialog mDialog;

	public TutorialScreen(SoccerNight game) {
		this.game = game;
		init();
	}

	private void init() {
		guiCam = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		guiCam.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				0);
		batch = new SpriteBatch();
		state = TUTORIAL_PAUSED;
		stateTime = preStateTime = 0f;
		scene = 0;
		phoneRegion = SoccerNight.mAltas.findRegion("phone");
		phoneX = Config.SCREEN_WIDTH / 2 - phoneRegion.getRegionWidth() / 2;
		phoneY = Config.SCREEN_HEIGHT / 2 - phoneRegion.getRegionHeight() / 2;
		bob = new Bob(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2);
		soccers = new ArrayList<Soccer>();
		zombies = new ArrayList<Zombie>();
		rand = new Random();
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
			if (scene == 0 && stateTime - preStateTime >= 2) {
				state = TUTORIAL_MOVING;
				preStateTime = stateTime;
				scene = 1;
				zombies.add(new Tracker(Config.SCREEN_WIDTH
						- Zombie.ZOMBIE_WIDTH, Config.SCREEN_HEIGHT / 2));
			} else if (scene == 1 && stateTime - preStateTime >= 2) {
				state = TUTORIAL_MOVING;
				preStateTime = stateTime;
				scene = 2;
			}
			break;
		case TUTORIAL_MOVING:
			updateRunning(delta);
			break;
		case TUTORIAL_OVER:
			createDialog();
			break;
		default:
			break;
		}
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
		int accelZombie = rand.nextInt(zombies.size());
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			float x = bob.position.x > z.position.x ? 1 : -1;
			float y = bob.position.y > z.position.y ? 1 : -1;
			if (z instanceof Tracker) {
				if (i == accelZombie) {
					x += x > 0 ? 1 : -1;
					y += y > 0 ? 1 : -1;
				}
				z.update(delta, x, y);
			}
		}
		if (scene == 0 && isMoving(bob.position)) {
			state = TUTORIAL_PAUSED;
		} else if (scene == 1) {
			if (collisionZombie())
				state = TUTORIAL_OVER;
			else if (stateTime - preStateTime >= 6)
				state = TUTORIAL_PAUSED;
		}
	}

	private boolean collisionZombie() {
		for (Zombie z : zombies)
			if (OverlapTester.overlapRectangles(bob.bounds, z.bounds))
				return true;
		return false;
	}

	private boolean isMoving(Vector2 v) {
		return Math.abs(v.x - Config.SCREEN_WIDTH / 2) >= 30
				|| Math.abs(v.y - Config.SCREEN_HEIGHT / 2) >= 30;
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
		drawBob(delta);
		drawZombies(delta);
		switch (state) {
		case TUTORIAL_MOVING:
			break;
		case TUTORIAL_PAUSED:
			if (scene == 0) {
				batch.draw(phoneRegion, phoneX, phoneY, 30, 30);
				Assets.font.drawMultiLine(batch, "Control the role to move!",
						phoneX, phoneY - 20, 20, HAlignment.CENTER);
			} else if (scene == 1) {
				Assets.font.drawMultiLine(batch,
						"Perfect! Now move the role to avoid the magic girl!",
						Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2, 20,
						HAlignment.CENTER);
			}
			break;
		case TUTORIAL_OVER:
			break;
		default:
			break;
		}
		batch.end();

	}

	private void drawZombies(float delta) {
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

	private void drawBob(float delta) {
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
			} else if (vx > 0) {
				region = Assets.aniBobR.getKeyFrame(stateTime);
				batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			}
			break;
		default:
			break;
		}
	}

	private void createDialog() {
		mDialog = new CustomDialog("YOU DIE!", Assets.skin);
		mDialog.text("CONSUME TUTORIAL?").button("YES", new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			};

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
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
				game.setMainScreen();
			}
		});

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
