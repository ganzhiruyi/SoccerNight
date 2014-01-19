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
	
	public static Animation aniBobL,aniBobR;
	public static TextureRegion aniBobIdleL,aniBobIdleR;
	public static Animation aniTrackerL,aniTrackerR;
	public static TextureRegion aniTrackerIdleL,aniTrackerIdleR;
	public static TextureRegion aniRedSocIdle;
	public static Animation aniRedSoc;
	public static TextureRegion aniBlueSocIdle;
	public static Animation aniBlueSoc;
	public static Texture level_1_bg;
	
	public static Animation aniKnightIdleL,aniKnightIdleR,aniKnightL,aniKnightR; 

	public static void load() {
		// load the source
		background = loadTexture("main_bg.jpg");
		backgroundRegion = new TextureRegion(background);
		level_1_bg = loadTexture("bg_level1.jpg");
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"),false);
		
		TextureRegion[][] BobRegions = new TextureRegion(loadTexture("bob.png")).split(32, 48);
		TextureRegion[] BobRightRegions = BobRegions[2];
		TextureRegion[] BobLeftRegions = BobRegions[1];
		aniBobL = new Animation(0.1f, BobLeftRegions);
		aniBobR = new Animation(0.1f, BobRightRegions);
		aniBobIdleL = BobLeftRegions[0];
		aniBobIdleR = BobRightRegions[0];
		
		TextureRegion[][] zombieRegions = new TextureRegion(loadTexture("tracker.png")).split(97, 99);
		aniTrackerIdleL = zombieRegions[0][0];
		aniTrackerIdleR = zombieRegions[1][0];
		aniTrackerL = new Animation(0.1f, zombieRegions[0]);
		aniTrackerR = new Animation(0.1f, zombieRegions[1]);
		
		TextureRegion[][] redSocRegions = new TextureRegion(loadTexture("red_soccer.png")).split(241, 241);
		aniRedSocIdle = redSocRegions[0][0];
		aniRedSoc = new Animation(0.1f, redSocRegions[0]);
		
		TextureRegion[][] blueSocRegions = new TextureRegion(loadTexture("blue_soccer.png")).split(241, 241);
		aniBlueSocIdle = blueSocRegions[0][0];
		aniBlueSoc = new Animation(0.1f, blueSocRegions[0]);
		
		TextureRegion[][] knightRegions = new TextureRegion(loadTexture("knight.png")).split(166, 131);
		TextureRegion[][] tmp = new TextureRegion(loadTexture("knight.png")).split(166, 131);
		aniKnightR = new Animation(0.1f, knightRegions[0]);
		aniKnightL = new Animation(0.1f, reversePicture(tmp[0]));
		TextureRegion[][] knightIdleRegions = new TextureRegion(loadTexture("knight_idle.png")).split(162, 134);
		tmp = new TextureRegion(loadTexture("knight_idle.png")).split(162, 134);
		aniKnightIdleR =  new Animation(0.1f, knightIdleRegions[0]);
		aniKnightIdleL = new Animation(0.1f, reversePicture(tmp[0]));
	}

	private static TextureRegion[] reversePicture(TextureRegion[] region){
		TextureRegion[] miror = region;
		for(TextureRegion r : miror){
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
