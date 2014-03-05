package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.Settings;

public class HighScoreScreen implements Screen {
	private Game game;
	private Stage stage;
	private List list;
	private OrthographicCamera camera;
	public HighScoreScreen(Game game) {
		this.game = game;
		camera = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				10);
	}
	private void initStage(){
		stage = new Stage();
		Image bg = new Image(Assets.backgroundRegion);
		bg.setFillParent(true);
		stage.addActor(bg);
		list = new List(Settings.getScores().toArray(), Assets.skin);
		Table table = new Table();
		table.setFillParent(true);
		table.center();
		table.add(list);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
//		Gdx.input.setCatchBackKey(true);
	}
	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		initStage();
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
		stage.dispose();
	}

}
