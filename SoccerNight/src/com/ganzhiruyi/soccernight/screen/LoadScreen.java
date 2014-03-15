package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;

public class LoadScreen implements Screen {
	public static final float MIN_LOADING_SPAN = 1;
	private SoccerNight game;
	private Stage stage;
	private float stateTime = 0;
	private TextureAtlas atlas;
	public LoadScreen(SoccerNight game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        if (SoccerNight.getAssetManager().update() && stateTime >= 2) {
        	Sound open = SoccerNight.getAssetManager().get("media/open.wav", Sound.class);
        	open.play(1);
            game.setMainScreen();
        }
        stage.act();
        stage.draw();
        stateTime += delta;
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		loadAsset();
		TextureRegion loadRegion = SoccerNight.mAltas.findRegion("loading");
		stage = new Stage();
		Table loadTable = new Table();
		loadTable.setFillParent(true);
		Label loadLabel = new Label("Loading...", Assets.skin, "title-text");
		loadTable.add(loadLabel).center().width(400).height(200);
		loadTable.row();
		Image loadImage = new Image(loadRegion);
		loadTable.add(loadImage).center().height(60).width(60);
		loadImage.setOrigin(loadImage.getOriginX() + 30, loadImage.getImageY() + 30);
		loadImage.addAction(Actions.repeat(100, Actions.rotateBy(-360, 1f)));
		stage.addActor(loadTable);
		Gdx.input.setInputProcessor(stage);
	}
	private void loadAsset(){
		AssetManager manager = SoccerNight.getAssetManager();
		manager.load("media/main_bgm.wav", Music.class);
		manager.load("media/game_bgm.mp3", Music.class);
		manager.load("media/appear.wav", Sound.class);
		manager.load("media/button.wav", Sound.class);
		manager.load("media/open.wav", Sound.class);
		manager.load("media/pause.wav", Sound.class);
		manager.load("media/success.wav", Sound.class);
		manager.load("media/gameover.wav", Sound.class);
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
