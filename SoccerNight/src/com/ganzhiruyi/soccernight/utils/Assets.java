package com.ganzhiruyi.soccernight.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static Texture items;
	public static TextureRegion backgroundRegion;
	public static BitmapFont font;

	public static Animation aniBobL, aniBobR;
	public static TextureRegion aniBobIdleL, aniBobIdleR;
	public static TextureRegion aniRedSocIdle;
	public static Animation aniRedSoc;
	public static TextureRegion aniBlueSocIdle;
	public static Animation aniBlueSoc;
	public static Animation aniPriWalkL, aniPriWalkR, aniPriDeadL, aniPriDeadR,
			aniPriStabL, aniPriStabR, aniPriHackL, aniPriHackR;
	public static Texture level_1_bg;
	public static MoveAnimation aniKnight, aniTracker;

	public static void load() {
		// load the source
		background = loadTexture("main_bg.jpg");
		backgroundRegion = new TextureRegion(background);
		level_1_bg = loadTexture("bg_level1.jpg");
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
				Gdx.files.internal("data/font.png"), false);
		loadRole();
		loadSoccer();
	}

	private static void loadSoccer() {
		TextureRegion[][] redSocRegions = new TextureRegion(
				loadTexture("red_soccer.png")).split(241, 241);
		aniRedSocIdle = redSocRegions[0][0];
		aniRedSoc = new Animation(0.1f, redSocRegions[0]);

		TextureRegion[][] blueSocRegions = new TextureRegion(
				loadTexture("blue_soccer.png")).split(241, 241);
		aniBlueSocIdle = blueSocRegions[0][0];
		aniBlueSoc = new Animation(0.1f, blueSocRegions[0]);
	}

	private static void loadRole() {
		// load bob
		TextureRegion[][] BobRegions = new TextureRegion(loadTexture("bob.png"))
				.split(32, 48);
		TextureRegion[] BobRightRegions = BobRegions[2];
		TextureRegion[] BobLeftRegions = BobRegions[1];
		aniBobL = new Animation(0.1f, BobLeftRegions);
		aniBobR = new Animation(0.1f, BobRightRegions);
		aniBobIdleL = BobLeftRegions[0];
		aniBobIdleR = BobRightRegions[0];

		// load princess
		//dead
		TextureRegion[][] priRegions = new TextureRegion(
				loadTexture("dead.png")).split(95, 81);
		TextureRegion[][] tmp = new TextureRegion(
				loadTexture("dead.png")).split(95, 81);
		aniPriDeadR = new Animation(0.5f, priRegions[0]);
		aniPriDeadL = new Animation(0.5f, reversePicture(tmp[0]));
		//stab
		priRegions = new TextureRegion(loadTexture("stab.png")).split(134, 84);
		tmp = new TextureRegion(loadTexture("stab.png")).split(134, 84);
		aniPriStabR = new Animation(0.5f, priRegions[0]);
		aniPriStabL = new Animation(0.5f, reversePicture(tmp[0]));
		//hack
		priRegions = new TextureRegion(loadTexture("hack.png")).split(109, 106);
		tmp = new TextureRegion(loadTexture("hack.png")).split(109, 106);
		aniPriHackR = new Animation(0.5f, priRegions[0]);
		aniPriHackL = new Animation(0.5f, reversePicture(tmp[0]));
		//walk
		priRegions = new TextureRegion(loadTexture("walk.png")).split(89, 83);
		tmp = new TextureRegion(loadTexture("walk.png")).split(89, 83);
		aniPriWalkR = new Animation(0.5f, priRegions[0]);
		aniPriWalkL = new Animation(0.5f, reversePicture(tmp[0]));	
		// load zombie
		aniTracker = loadAnimationOfZombie("tracker.png");
		aniKnight = loadAnimationOfZombie("knight.png");
	}

	private static MoveAnimation loadAnimationOfZombie(String name) {
		MoveAnimation move = new MoveAnimation();
		TextureRegion[][] regions = new TextureRegion(loadTexture(name)).split(
				120, 120);
		move.idleL = regions[4][0];
		move.idleR = regions[5][0];
		move.aniL = new Animation(0.1f, regions[4]);
		move.aniR = new Animation(0.1f, regions[5]);
		return move;
	}

	private static TextureRegion[] reversePicture(TextureRegion[] region) {
		TextureRegion[] miror = region;
		for (TextureRegion r : miror) {
			r.flip(true, false);
		}
		return miror;
	}

	public static Texture loadTexture(String filename) {
		String path = "data/" + filename;
		return new Texture(Gdx.files.internal(path));
	}

	public static void playSound(Sound sound) {
		// set the sound play model and set the volume
		if (Settings.soundEnable)
			sound.play(1);
	}
}
