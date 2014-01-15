package com.ganzhiruyi.soccernight.world;

import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.soccer.LineSoccer;
import com.ganzhiruyi.soccernight.soccer.Soccer;
import com.ganzhiruyi.soccernight.utils.Animation;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.zombie.Zombie;

public class WorldRenderer {
	public static final float FRUSTUM_WIDTH = 480;
	public static final float FRUSTUM_HEIGHT = 320;
	private World world;
	private OrthographicCamera cam;
	private SpriteBatch batch;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.batch = batch;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}

	public void render() {
		/*
		 * if(world.getBob().position.x != cam.position.x ||
		 * world.getBob().position.y != cam.position.y){ //make sure the camera
		 * is always on bob, he is the star, haha cam.position.x =
		 * world.getBob().position.x; cam.position.y =
		 * world.getBob().position.y; }
		 */
		if (world.getState() == World.WORLD_STATE_GAME_OVER)
			return;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	private void renderBackground() {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.level_1_bg, 0, 0, Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		batch.end();
	}

	private void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderBob();
		renderSoccers();
		renderZombies();
		batch.end();
	}

	private void renderBob() {
		Bob bob = world.getBob();
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
				region = Assets.aniBobL.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING);
				batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			} else if (vx > 0) {
				region = Assets.aniBobR.getKeyFrame(stateTime,
						Animation.ANIMATION_LOOPING);
				batch.draw(region, x, y, Bob.BOB_WIDTH, Bob.BOB_HEIGHT);
			}
			break;
		default:
			break;
		}
	}

	private void renderZombies() {
		List<Zombie> zombies = world.getZombies();
		if (zombies == null)
			return;
		for (Zombie z : zombies) {
			TextureRegion region;
			float stateTime = z.getStateTime();
			float x = z.position.x;
			float y = z.position.y;
			switch (z.getState()) {
			case IDLE:
				region = z.isRight ? Assets.aniZombieIdleR
						: Assets.aniZombieIdleL;
				batch.draw(region, x, y, Zombie.ZOMBIE_WIDTH,
						Zombie.ZOMBIE_HEIGHT);
				break;
			case MOVING:
				float vx = z.velocity.x;
				if (vx < 0) {
					region = Assets.aniZombieL.getKeyFrame(stateTime,
							Animation.ANIMATION_LOOPING);
					batch.draw(region, x, y, Zombie.ZOMBIE_WIDTH,
							Zombie.ZOMBIE_HEIGHT);
				} else if (vx > 0) {
					region = Assets.aniZombieR.getKeyFrame(stateTime,
							Animation.ANIMATION_LOOPING);
					batch.draw(region, x, y, Zombie.ZOMBIE_WIDTH,
							Zombie.ZOMBIE_HEIGHT);
				}
				break;
			default:
				break;
			}

		}
	}

	private void renderSoccers() {
		List<Soccer> soccers = world.getSoccers();
		if (soccers == null)
			return;
		for (Soccer s : soccers) {
			TextureRegion region;
			float stateTime = s.getStateTime();
			float x = s.position.x;
			float y = s.position.y;
			switch (s.getState()) {
			case IDLE:
				region = s instanceof LineSoccer ? Assets.aniSoccerIdle
						: Assets.aniBlueSocIdle;
				batch.draw(region, x, y, Soccer.SOCCER_WIDTH,
						Soccer.SOCCER_HEIGHT);
				break;
			case MOVING:
				region = s instanceof LineSoccer ? Assets.aniSoccer
						.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING)
						: Assets.aniBlueSoc.getKeyFrame(stateTime,
								Animation.ANIMATION_LOOPING);
				batch.draw(region, x, y, Soccer.SOCCER_WIDTH,
						Soccer.SOCCER_HEIGHT);
				break;
			default:
				break;
			}
		}
	}
}
