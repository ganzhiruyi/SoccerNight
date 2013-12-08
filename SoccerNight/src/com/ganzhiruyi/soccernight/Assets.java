package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static Texture items;
	public static TextureRegion backgroundRegion;
	public static TextureRegion messiRegion;

	public static void load() {
		// load the source
		background = loadTexture("background");
		items = loadTexture("item");
	}

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void playSound(Sound sound) {
		// set the sound play model and set the volume
		if (Settings.soundEnable)
			sound.play(1);

	}
}
