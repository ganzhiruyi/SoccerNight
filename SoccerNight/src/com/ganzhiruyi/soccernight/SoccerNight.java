package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.ganzhiruyi.soccernight.screen.GameScreen;
import com.ganzhiruyi.soccernight.screen.HighScoreScreen;
import com.ganzhiruyi.soccernight.screen.LoadScreen;
import com.ganzhiruyi.soccernight.screen.MainScreen;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Settings;

public class SoccerNight extends Game {
	boolean firstTimeCreate = true;
	FPSLogger fps;
	private MainScreen mMainScreen;
	private HighScoreScreen mHighScoreScreen;
	private GameScreen mGameScreen;
	private LoadScreen mLoadScreen;

	@Override
	public void create() {
		Assets.load();
		Settings.load();
		initScreens();
		setMainScreen();
		fps = new FPSLogger();
	}

	private void initScreens() {
		mMainScreen = new MainScreen(this);
		mHighScoreScreen = new HighScoreScreen(this);
		mGameScreen = new GameScreen(this);
		mLoadScreen = new LoadScreen(this);
	}

	public void setMainScreen() {
		setScreen(mMainScreen);
	}

	public void setHighScoreScreen() {
		setScreen(mHighScoreScreen);
	}

	public void setGameScreen() {
		if(mGameScreen != null)
			mGameScreen.dispose();
		mGameScreen = new GameScreen(this);
		setScreen(mGameScreen);
	}
	public void setLoadScreen(){
		setScreen(mLoadScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		mGameScreen.dispose();
		mMainScreen.dispose();
		mHighScoreScreen.dispose();
	}
}
