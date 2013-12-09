package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static Texture items;
	public static TextureRegion backgroundRegion;
	public static TextureRegion messiRegion;
	public static BitmapFont font;

	public static void load() {
		// load the source
		background = loadTexture("libgdx.png");
		backgroundRegion = new TextureRegion(background);
		//items = loadTexture("item");
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"),false);
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
