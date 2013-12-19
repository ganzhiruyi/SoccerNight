package com.ganzhiruyi.soccernight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.ganzhiruyi.soccernight.screen.MainScreen;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Settings;

public class SoccerNight extends Game {
	boolean firstTimeCreate = true;
	FPSLogger fps;
	@Override
	public void create() {
		Assets.load();
		Settings.load();
		setScreen(new MainScreen(this));
		fps = new FPSLogger();
	}
	@Override
	public void render() {
		super.render();
		//fps.log();
	}

	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
	
	
}
