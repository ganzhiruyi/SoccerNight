package com.ganzhiruyi.soccernight.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ganzhiruyi.soccernight.SoccerNight;

public class CustomButton extends TextButton {

	public CustomButton(String text, Skin skin) {
		super(text, skin);
		Drawable up, down;
		up = new TextureRegionDrawable(SoccerNight.mAltas.findRegion("btn"));
		down = new TextureRegionDrawable(
				SoccerNight.mAltas.findRegion("btn_press"));
		TextButtonStyle btnStyle = new TextButtonStyle(up, down, down,
				Assets.font);
		setStyle(btnStyle);
	}

}
