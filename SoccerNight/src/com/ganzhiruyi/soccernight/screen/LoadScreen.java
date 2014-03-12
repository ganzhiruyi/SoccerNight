package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;

public class LoadScreen implements Screen {
	public static final float MIN_LOADING_SPAN = 1;
	private SoccerNight game;
	private Stage stage;
	public LoadScreen(SoccerNight game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        if (SoccerNight.getAssetManager().update()) {
            game.setMainScreen();
        }
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		loadAsset();
		stage = new Stage();
		Table loadTable = new Table();
		loadTable.setFillParent(true);
		Label loadLabel = new Label("Loading...", Assets.skin, "title-text");
		loadTable.add(loadLabel).center().width(400).height(200);
		stage.addActor(loadTable);
		Gdx.input.setInputProcessor(stage);
	}
	private void loadAsset(){
		AssetManager manager = SoccerNight.getAssetManager();
		manager.load("data/game_bgm.mp3", Music.class);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
