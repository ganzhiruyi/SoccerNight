package com.ganzhiruyi.soccernight.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static Texture background;
	public static Texture items;
	public static TextureRegion backgroundRegion;
	public static BitmapFont font;
	public static Skin skin;

	public static Animation aniBobL, aniBobR;
	public static TextureRegion aniBobIdleL, aniBobIdleR;
	public static TextureRegion lineSocIdle, paddySocIdle, roundSocIdle,
			waveSocIdle, bombSocIdle;
	public static Animation aniRedSoc, aniBlueSoc, aniRoundSoc, aniWaveSoc,
			aniBombSoc;
	public static Animation aniPriWalkL, aniPriWalkR, aniPriDeadL, aniPriDeadR,
			aniPriStabL, aniPriStabR, aniPriHackL, aniPriHackR;
	public static Texture level_1_bg;
	public static MoveAnimation aniKnight, aniTracker;
	public static Animation aniHurricane, aniFire;

	public static void load() {
		// load the source
		background = loadTexture("main_bg.jpg");
		backgroundRegion = new TextureRegion(background);
		level_1_bg = loadTexture("bg_level1.jpg");
		font = new BitmapFont(Gdx.files.internal("skin/ravie-small.fnt"),
				Gdx.files.internal("skin/ravie-small.png"), false);
		loadRole();
		loadSoccer();
		loadMagic();
		loadSkin();
	}

	private static void loadSkin() {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}

	private static void loadSoccer() {
		TextureRegion[][] regions = new TextureRegion(
				loadTexture("line_soccer.png")).split(241, 241);
		lineSocIdle = regions[0][0];
		aniRedSoc = new Animation(0.2f, regions[0]);

		regions = new TextureRegion(loadTexture("paddy_soccer.png")).split(241,
				241);
		paddySocIdle = regions[0][0];
		aniBlueSoc = new Animation(0.2f, regions[0]);

		regions = new TextureRegion(loadTexture("round_soccer.png")).split(241,
				241);
		roundSocIdle = regions[0][0];
		aniRoundSoc = new Animation(0.2f, regions[0]);

		regions = new TextureRegion(loadTexture("wave_soccer.png")).split(241,
				241);
		waveSocIdle = regions[0][0];
		aniWaveSoc = new Animation(0.2f, regions[0]);
		
		regions = new TextureRegion(loadTexture("bomb_soccer.png")).split(241,
				241);
		bombSocIdle = regions[0][0];
		aniBombSoc = new Animation(0.2f, regions[0]);
	}

	private static void loadRole() {
		// load bob
		TextureRegion[][] BobRegions = new TextureRegion(loadTexture("bob.png"))
				.split(32, 48);
		TextureRegion[] BobRightRegions = BobRegions[2];
		TextureRegion[] BobLeftRegions = BobRegions[1];
		aniBobL = new Animation(0.2f, BobLeftRegions);
		aniBobR = new Animation(0.2f, BobRightRegions);
		aniBobIdleL = BobLeftRegions[0];
		aniBobIdleR = BobRightRegions[0];

		// load princess
		// dead
		TextureRegion[][] priRegions = new TextureRegion(
				loadTexture("dead.png")).split(120, 120);
		TextureRegion[][] tmp = new TextureRegion(loadTexture("dead.png"))
				.split(120, 120);
		aniPriDeadR = new Animation(1f, priRegions[0]);
		aniPriDeadL = new Animation(1f, reversePicture(tmp[0]));
		// stab
		priRegions = new TextureRegion(loadTexture("stab.png")).split(120, 120);
		tmp = new TextureRegion(loadTexture("stab.png")).split(120, 120);
		aniPriStabR = new Animation(0.2f, priRegions[0]);
		aniPriStabL = new Animation(0.2f, reversePicture(tmp[0]));
		// hack
		priRegions = new TextureRegion(loadTexture("hack.png")).split(120, 120);
		tmp = new TextureRegion(loadTexture("hack.png")).split(120, 120);
		aniPriHackR = new Animation(0.2f, priRegions[0]);
		aniPriHackL = new Animation(0.2f, reversePicture(tmp[0]));
		// walk
		priRegions = new TextureRegion(loadTexture("walk.png")).split(120, 120);
		tmp = new TextureRegion(loadTexture("walk.png")).split(120, 120);
		aniPriWalkR = new Animation(0.2f, priRegions[0]);
		aniPriWalkL = new Animation(0.2f, reversePicture(tmp[0]));
		// load zombie
		aniTracker = loadAnimationOfZombie("tracker.png");
		aniKnight = loadAnimationOfZombie("knight.png");
	}

	private static void loadMagic() {
		TextureRegion[][] regions = new TextureRegion(
				loadTexture("hurricane.png")).split(120, 120);
		aniHurricane = new Animation(0.4f, regions[0]);
		regions = new TextureRegion(loadTexture("fire.png")).split(30, 29);
		aniFire = new Animation(0.4f, regions[0]);
	}

	private static MoveAnimation loadAnimationOfZombie(String name) {
		MoveAnimation move = new MoveAnimation();
		TextureRegion[][] regions = new TextureRegion(loadTexture(name)).split(
				120, 120);
		move.idleL = regions[4][0];
		move.idleR = regions[5][0];
		move.aniL = new Animation(0.2f, regions[4]);
		move.aniR = new Animation(0.2f, regions[5]);
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
}
