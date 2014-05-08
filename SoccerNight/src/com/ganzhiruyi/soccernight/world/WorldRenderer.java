package com.ganzhiruyi.soccernight.world;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ganzhiruyi.soccernight.magic.Fire;
import com.ganzhiruyi.soccernight.magic.Hurricane;
import com.ganzhiruyi.soccernight.magic.Magic;
import com.ganzhiruyi.soccernight.magic.Pumpkin;
import com.ganzhiruyi.soccernight.object.Bob;
import com.ganzhiruyi.soccernight.soccer.BombSoccer;
import com.ganzhiruyi.soccernight.soccer.LineSoccer;
import com.ganzhiruyi.soccernight.soccer.PaddySoccer;
import com.ganzhiruyi.soccernight.soccer.RoundSoccer;
import com.ganzhiruyi.soccernight.soccer.Soccer;
import com.ganzhiruyi.soccernight.soccer.WaveSoccer;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.zombie.Eater;
import com.ganzhiruyi.soccernight.zombie.Knight;
import com.ganzhiruyi.soccernight.zombie.Player;
import com.ganzhiruyi.soccernight.zombie.Princess;
import com.ganzhiruyi.soccernight.zombie.Tracker;
import com.ganzhiruyi.soccernight.zombie.Zombie;

public class WorldRenderer {
	public static final float FRUSTUM_WIDTH = 480;
	public static final float FRUSTUM_HEIGHT = 320;
	private World world;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private ParticleEffect starEffect;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.batch = batch;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		starEffect = new ParticleEffect();
		starEffect.load(Gdx.files.internal("particles/star.p"),
				Gdx.files.internal("particles"));
	}

	public void render() {
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
		renderMagic();
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
		starEffect.setPosition(x, y);
		starEffect.draw(batch, Gdx.graphics.getDeltaTime());
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
				if (z instanceof Tracker)
					region = z.isRight ? Assets.aniTracker.idleR
							: Assets.aniTracker.idleL;
				else if (z instanceof Knight)
					region = z.isRight ? Assets.aniKnight.idleR
							: Assets.aniKnight.idleL;
				else if (z instanceof Player)
					region = z.isRight ? Assets.aniPlayer.idleR
							: Assets.aniPlayer.idleL;
				else if (z instanceof Eater)
					region = z.isRight ? Assets.aniEater.idleR
							: Assets.aniEater.idleL;
				else {
					int move = ((Princess) z).getMove();
					if (move == Princess.HACK)
						region = z.isRight ? Assets.aniPriHackR
								.getKeyFrame(stateTime) : Assets.aniPriHackL
								.getKeyFrame(stateTime);
					else if (move == Princess.STAB)
						region = z.isRight ? Assets.aniPriStabR
								.getKeyFrame(stateTime) : Assets.aniPriStabL
								.getKeyFrame(stateTime);
					else
						region = z.isRight ? Assets.aniPriDeadR
								.getKeyFrame(stateTime) : Assets.aniPriDeadL
								.getKeyFrame(stateTime);
				}
				batch.draw(region, x, y, z.width, z.height);
				break;
			case MOVING:
				float vx = z.velocity.x;
				if (vx < 0) {
					if (z instanceof Tracker)
						region = Assets.aniTracker.aniL.getKeyFrame(stateTime);
					else if (z instanceof Knight)
						region = Assets.aniKnight.aniL.getKeyFrame(stateTime);
					else if (z instanceof Player)
						region = Assets.aniPlayer.aniL.getKeyFrame(stateTime);
					else if (z instanceof Eater)
						region = Assets.aniEater.aniL.getKeyFrame(stateTime);
					else
						region = Assets.aniPriWalkL.getKeyFrame(stateTime);
					batch.draw(region, x, y, z.width, z.height);
				} else if (vx > 0) {
					if (z instanceof Tracker)
						region = Assets.aniTracker.aniR.getKeyFrame(stateTime);
					else if (z instanceof Knight)
						region = Assets.aniKnight.aniR.getKeyFrame(stateTime);
					else if (z instanceof Player)
						region = Assets.aniPlayer.aniR.getKeyFrame(stateTime);
					else if (z instanceof Eater)
						region = Assets.aniEater.aniR.getKeyFrame(stateTime);
					else
						region = Assets.aniPriWalkR.getKeyFrame(stateTime);
					batch.draw(region, x, y, z.width, z.height);
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

	private void renderMagic() {
		List<Magic> magics = world.getMagics();
		if (magics == null)
			return;
		for (Magic magic : magics) {
			TextureRegion region = null;
			float stateTime = magic.getStateTime();
			float x = magic.position.x;
			float y = magic.position.y;
			switch (magic.getState()) {
			case IDLE:
				break;
			case MOVING:
				if (magic instanceof Hurricane)
					region = Assets.aniHurricane.getKeyFrame(stateTime);
				else if (magic instanceof Fire)
					region = Assets.aniFire.getKeyFrame(stateTime);
				else if (magic instanceof Pumpkin)
					region = magic.isRight ? Assets.pumpkinL : Assets.pumpkinR;
				batch.draw(region, x, y, Magic.WIDTH, Magic.HEIGHT);
				break;
			default:
				break;
			}
		}
	}
}
