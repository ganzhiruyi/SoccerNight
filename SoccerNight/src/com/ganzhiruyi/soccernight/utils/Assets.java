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
	public static Animation aniBobIdle;

	public static void load() {
		// load the source
		background = loadTexture("main_bg.jpg");
		backgroundRegion = new TextureRegion(background);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"),false);
		TextureRegion BobRightRegion = new TextureRegion(loadTexture("test.png"));
		TextureRegion[] BobRightRegions = BobRightRegion.split(32, 48)[1];
		TextureRegion[] tmp = BobRightRegion.split(32, 48)[1];
		System.out.println("************size: " + tmp.length);
		TextureRegion[] BobLeftRegions = reversePicture(tmp);
		aniBobL = new Animation(0.2f, BobLeftRegions);
		aniBobR = new Animation(0.2f, BobRightRegions);
		aniBobIdle = new Animation(0.2f, BobRightRegions[0]);
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
