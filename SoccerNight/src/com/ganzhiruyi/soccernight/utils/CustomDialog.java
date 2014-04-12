package com.ganzhiruyi.soccernight.utils;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ganzhiruyi.soccernight.SoccerNight;

public class CustomDialog extends Dialog {

	public CustomDialog(String title, Skin skin) {
		super(title, skin);
	}

	@Override
	public Dialog button(String text, Object object) {
		Drawable up, down;
		up = new TextureRegionDrawable(SoccerNight.mAltas.findRegion("btn"));
		down = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("btn_press"));
		TextButtonStyle btnStyle = new TextButtonStyle(up, down, down,
				Assets.font);
		TextButton btn = new TextButton(text, btnStyle);
		btn.addListener((InputListener) object);
		button(btn);
		return this;
	}

}
