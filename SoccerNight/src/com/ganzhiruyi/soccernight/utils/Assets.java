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
	
	public static Animation aniBobL;
	public static Animation aniBobR;
	public static TextureRegion aniBobIdleL;
	public static TextureRegion aniBobIdleR;
	public static Animation aniZombieL;
	public static Animation aniZombieR;
	public static TextureRegion aniZombieIdleL;
	public static TextureRegion aniZombieIdleR;
	public static Texture level_1_bg;

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
		
		TextureRegion[][] zombieRegions = new TextureRegion(loadTexture("zombie.png")).split(97, 99);
		aniZombieIdleL = zombieRegions[0][0];
		aniZombieIdleR = zombieRegions[1][0];
		aniZombieL = new Animation(0.1f, zombieRegions[0]);
		aniZombieR = new Animation(0.1f, zombieRegions[1]);
	}

	public static TextureRegion[] reversePicture(TextureRegion[] region){
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
