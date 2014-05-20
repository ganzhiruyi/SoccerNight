package com.ganzhiruyi.soccernight.utils;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ganzhiruyi.soccernight.SoccerNight;

public class CustomDialog extends Dialog {

	public CustomDialog(String title, Skin skin) {
		super(title, skin);
		pad(20f);
		setMovable(true);
	}

	@Override
	public Dialog button(String text, Object object) {
		CustomButton btn = new CustomButton(text, Assets.skin);
		btn.addListener((InputListener) object);
		button(btn);
		return this;
	}

	@Override
	public float getPrefHeight() {
		return 320f;
	}

	@Override
	public float getPrefWidth() {
		return 480f;
	}
}
