package com.ganzhiruyi.soccernight.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ganzhiruyi.soccernight.role.Bob;

public class WorldRenderer {
	public static final float FRUSTUM_WIDTH = 10;
	public static final float FRUSTUM_HEIGHT = 15;
	private World world;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	public WorldRenderer(SpriteBatch batch, World world){
		this.world = world;
		this.batch = batch;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}
	public void render(){
		if(world.getBob().position.x != cam.position.x || world.getBob().position.y != cam.position.y){
			//make sure the camera is always on bob, he is the star, haha
			cam.position.x = world.getBob().position.x;
			cam.position.y = world.getBob().position.y;
		}
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderElement();
	}
	private void renderBackground() {
		batch.disableBlending();
		batch.begin();
		batch.end();
	}
	private void renderElement() {
		batch.enableBlending();
		batch.begin();
		batch.end();
	}
	private void renderBob(){
		TextureRegion keyFrame;
		Bob bob = world.getBob();
		switch(bob.getState()){
		case IDLE:
			
			break;
		case MOVING:
			
			break;
		}
	}
}
