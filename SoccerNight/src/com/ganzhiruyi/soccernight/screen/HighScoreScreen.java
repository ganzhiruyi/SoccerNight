package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.Settings;

public class HighScoreScreen implements Screen {
	private SoccerNight game;
	private Stage stage;
	private List list;
	private OrthographicCamera camera;

	public HighScoreScreen(SoccerNight game) {
		this.game = game;
		camera = new OrthographicCamera(Config.SCREEN_WIDTH,
				Config.SCREEN_HEIGHT);
		camera.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2,
				10);
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
		stage = new Stage();
		Image bg = new Image(Assets.backgroundRegion);
		Image trophy = new Image(SoccerNight.mAltas.findRegion("trophy"));
		bg.setFillParent(true);
		stage.addActor(bg);
		
		list = new List(Settings.getInstance().getScores().toArray(), Assets.skin);
		Image back = new Image(SoccerNight.mAltas.findRegion("soccer"));
		back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setMainScreen();
				return false;
			}
		});
		Table table = new Table();
		table.setFillParent(true);
		table.add(back).bottom().left().width(80).height(80).pad(10).expand();
		table.add(trophy).pad(20);
		table.add(list).width(200).center().expandX();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
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
		if (stage != null)
			stage.dispose();
	}
}
