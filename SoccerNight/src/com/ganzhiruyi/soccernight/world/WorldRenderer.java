package com.ganzhiruyi.soccernight.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ganzhiruyi.soccernight.role.Bob;
import com.ganzhiruyi.soccernight.utils.Animation;
import com.ganzhiruyi.soccernight.utils.Assets;

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
		//renderBackground();
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
		renderBob();
		batch.end();
	}
	private void renderBob(){
		Bob bob = world.getBob();
		TextureRegion region;
		float stateTime = bob.getStateTime();
		switch(bob.getState()){
		case IDLE:
			System.out.println("-----------------------bob state is idle");
			region = Assets.aniBobIdle.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
			batch.draw(region, bob.position.x, bob.position.y);
			break;
		case MOVING:
			System.out.println("-----------------------bob state is moving");
			float x = bob.getVelocity().x;
			float y = bob.getVelocity().y;
			if(x < 0){
				region = Assets.aniBobL.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
				batch.draw(region, x, y);
			}
			else if(x > 0){
				region = Assets.aniBobR.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
				batch.draw(region, x, y);
			}
			break;
		default:
			break;
		}
	}
}
