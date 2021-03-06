package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ganzhiruyi.soccernight.screen.GameScreen;
import com.ganzhiruyi.soccernight.screen.HighScoreScreen;
import com.ganzhiruyi.soccernight.screen.LoadScreen;
import com.ganzhiruyi.soccernight.screen.MainScreen;
import com.ganzhiruyi.soccernight.screen.SettingScreen;
import com.ganzhiruyi.soccernight.screen.TutorialScreen;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Settings;

public class SoccerNight extends Game {
	boolean firstTimeCreate = true;
	FPSLogger fps;
	private MainScreen mMainScreen;
	private HighScoreScreen mHighScoreScreen;
	private GameScreen mGameScreen;
	private LoadScreen mLoadScreen;
	private SettingScreen mSettingScreen;
	private TutorialScreen mTutorialScreen;
	private static AssetManager mAssetManager;
	public static TextureAtlas mAltas;

	@Override
	public void create() {
		mAssetManager = new AssetManager();
		mAltas = new TextureAtlas(Gdx.files.internal("skin/skin.pack"));
		Assets.load();
		Settings.getInstance().load();
		initScreens();
		setLoadScreen();
		fps = new FPSLogger();
	}

	public static AssetManager getAssetManager() {
		if (mAssetManager == null) {
			mAssetManager = new AssetManager();
		}
		return mAssetManager;
	}

	private void initScreens() {
		mMainScreen = new MainScreen(this);
		mHighScoreScreen = new HighScoreScreen(this);
		mGameScreen = new GameScreen(this);
		mLoadScreen = new LoadScreen(this);
		mSettingScreen = new SettingScreen(this);
		mTutorialScreen = new TutorialScreen(this);
	}

	public void setMainScreen() {
		setScreen(mMainScreen);
	}

	public void setHighScoreScreen() {
		setScreen(mHighScoreScreen);
	}

	public void setSettingScreen() {
		setScreen(mSettingScreen);
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

	public void setTutorialScreen() {
		setScreen(mTutorialScreen);
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
		if (mSettingScreen != null)
			mSettingScreen.dispose();
		if (mTutorialScreen != null)
			mTutorialScreen.dispose();
		if (mAssetManager != null)
			mAssetManager.dispose();
	}
}
