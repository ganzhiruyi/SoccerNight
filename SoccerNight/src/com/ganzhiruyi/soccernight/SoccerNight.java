package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
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
	private static AssetManager mAssetManager;

	@Override
	public void create() {
		mAssetManager = new AssetManager();
		Assets.load();
		Settings.load();
		initScreens();
		setLoadScreen();
		fps = new FPSLogger();
	}
	public static AssetManager getAssetManager(){
		if(mAssetManager == null){
			mAssetManager = new AssetManager();
		}
		return mAssetManager;
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
		if (mGameScreen != null)
			mGameScreen.dispose();
		mGameScreen = new GameScreen(this);
		setScreen(mGameScreen);
	}

	public void setLoadScreen() {
		setScreen(mLoadScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (mGameScreen != null)
			mGameScreen.dispose();
		if (mMainScreen != null)
			mMainScreen.dispose();
		if (mHighScoreScreen != null)
			mHighScoreScreen.dispose();
		if (mLoadScreen != null)
			mLoadScreen.dispose();
		if(mAssetManager != null)
			mAssetManager.dispose();
	}
}
