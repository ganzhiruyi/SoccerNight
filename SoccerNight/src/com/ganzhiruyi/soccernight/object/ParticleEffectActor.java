package com.ganzhiruyi.soccernight.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	private ParticleEffect effect;
	private float effectX, effectY;

	public ParticleEffectActor(ParticleEffect effect) {
		this.effect = effect;
	}

	public ParticleEffectActor(String particleFile, String particlePicDir) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal(particleFile),
				Gdx.files.internal(particlePicDir));
	}

	/*
	 * this is called when need change the position.
	 */
	public void setEffectPosition(float x, float y) {
		effectX = x;
		effectY = y;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		effect.setPosition(effectX, effectY);
		effect.update(delta);
		effect.start();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		effect.draw(batch);
	}

}