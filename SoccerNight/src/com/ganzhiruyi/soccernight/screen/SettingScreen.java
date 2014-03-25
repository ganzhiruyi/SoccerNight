package com.ganzhiruyi.soccernight.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ganzhiruyi.soccernight.SoccerNight;
import com.ganzhiruyi.soccernight.utils.Assets;
import com.ganzhiruyi.soccernight.utils.Config;
import com.ganzhiruyi.soccernight.utils.Settings;

public class SettingScreen implements Screen {
	private SoccerNight game;
	private Stage stage;
	private OrthographicCamera camera;
	private SelectBox diffBox;

	public SettingScreen(SoccerNight game) {
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
		stage.setViewport(width, height, false);

	}

	@Override
	public void show() {
		stage = new Stage();
		String[] items = new String[] { "Normal", "Difficult", "Nightmare" };
		diffBox = new SelectBox(items, Assets.skin);
		Table table = new Table();
		table.setFillParent(true);
		int diff = Settings.getInstance().getDifficulty();
		diffBox.setSelection(diff);
		Image back = new Image(SoccerNight.mAltas.findRegion("soccer"));
		back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setMainScreen();
				return false;
			}
		});
		diffBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			}
			
		});
		table.add(back).bottom().left().width(80).height(80).pad(10);
		table.add(diffBox).center().width(200).height(80).expand();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		Settings.getInstance().setDifficulty(diffBox.getSelectionIndex());
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
