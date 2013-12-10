package com.ganzhiruyi.soccernight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class Settings {
	public static final int HIGHSCORE_NUM = 5;
	public static final String HIGHSCORE_RECORDE = ".scoccernight";
	public static List<Integer> highscores = new ArrayList<Integer>();
	public static boolean soundEnable = true;

	public static void load() {
		// load the highscores
		BufferedReader in = null;
		try {
			File file = new File(HIGHSCORE_RECORDE);
			if(!file.exists()){
				file.createNewFile();
			}
			in = new BufferedReader(new InputStreamReader(Gdx.files.internal(
					HIGHSCORE_RECORDE).read()));
			soundEnable = Boolean.parseBoolean(in.readLine());
			int highscore;
			String str = null;
			while ((str = in.readLine()) != null) {
				highscore = Integer.parseInt(str);
				highscores.add(highscore);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void save(int score) {
		// save the highscores
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.internal(
					HIGHSCORE_RECORDE).write(false)));
			out.write(String.valueOf(soundEnable) + "\n");
			for (int hScore : highscores) {
				out.write(hScore + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void addScore(int score) {
		for (int i = 0; i < highscores.size(); i++) {
			if (score > highscores.get(i)) {
				highscores.add(i, score);
				break;
			}
		}
		if (highscores.size() > HIGHSCORE_NUM)
			highscores.remove(HIGHSCORE_NUM);
	}
}
