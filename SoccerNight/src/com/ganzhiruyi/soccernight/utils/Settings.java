package com.ganzhiruyi.soccernight.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
	public static final int HIGHSCORE_NUM = 5;
	public static final String HIGHSCORE_RECORDE = "soccernight_highscore.txt";
	public static final String SOUND_ENABLE = "sound_enable";
	private static List<Integer> highscores = new ArrayList<Integer>();
	private boolean soundEnable;
	private static Settings instance;
	private Preferences mPrefs = Gdx.app.getPreferences("settings");
	public static Settings getInstance(){
		if(instance == null){
			instance = new Settings();
		}
		return instance;
	}
	public Settings(){
		soundEnable = true;
	}

	public static void load() {
		// load the highscores
		if (!Gdx.files.isLocalStorageAvailable())
			return;
		FileHandle file = Gdx.files.local(HIGHSCORE_RECORDE);
		String[] tmp = file.readString().split(",");
		if (tmp.length <= 0 || tmp[0].length() <= 0)
			return;
		highscores.clear();
		for (int i = 0; i < tmp.length; i++)
			highscores.add(Integer.parseInt(tmp[i]));
	}

	public static void save(int score) {
		// save the highscores
		if (!Gdx.files.isLocalStorageAvailable())
			return;
		addScore(score);
		FileHandle file = Gdx.files.local(HIGHSCORE_RECORDE);
		String tmp = "";
		for (int i = 0; i < highscores.size(); i++) {
			tmp = tmp + highscores.get(i) + ",";
		}
		file.writeString(tmp, false);
	}

	public static void addScore(int score) {
		if (highscores.size() <= 0) {
			highscores.add(score);
			return;
		}
		for (int i = 0; i < highscores.size(); i++) {
			if (score > highscores.get(i)) {
				highscores.add(i, score);
				break;
			}
		}
		if (highscores.size() > HIGHSCORE_NUM)
			highscores.remove(HIGHSCORE_NUM);
	}

	public static List<Integer> getScores() {
		return highscores;
	}

	public void playMusic(Music music) {
		if (!soundEnable)
			return;
		music.play();
	}

	public void playSound(Sound sound, float volume) {
		if (!soundEnable)
			return;
		sound.play(volume);
	}

	public void playSound(Sound sound) {
		playSound(sound, 1);
	}
	public void setSoundEnable(boolean enable){
		if(soundEnable == enable)
			return;
		soundEnable = enable;
		mPrefs.putBoolean(SOUND_ENABLE, enable);
		mPrefs.flush();
	}
	public boolean isSoundEnable(){
		soundEnable = mPrefs.getBoolean(SOUND_ENABLE, true);
		return soundEnable;
	}

}
