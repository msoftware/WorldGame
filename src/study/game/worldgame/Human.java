package study.game.worldgame;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class Human extends Sprite{
	

	private char[] info = new char[5];
	
	/*
	 * 0 : Nationality
	 * 1 : Life
	 * 2 : Health
	 * 3 : Job
	 * 4 : IQ
	 */	
	
	public boolean direction = false;
	//BODY
	public AnimatedSprite thinBody;
	public AnimatedSprite normalBody;
	public AnimatedSprite fatBody;
	public AnimatedSprite muscularBody;
	//HEAD
	public AnimatedSprite joblessHead;
	public AnimatedSprite doctorHead;
	public AnimatedSprite engineerHead;
	public AnimatedSprite soldierHead;
	
	public float vx, vy;
	//create physic object behavior
	public FixtureDef bodyFixture = PhysicsFactory.createFixtureDef(0f,4f,4f, false);//density,elastic,friction,sensor mode
	public FixtureDef sensorFixture	= PhysicsFactory.createFixtureDef(0f,0f,0f, true);//density,elastic,friction,sensor mode;
	//create physicbody
	public Body physicbody;    
	
	
	public Human(char nationality, float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, MainActivity.invobjRegion, pVertexBufferObjectManager);
		
		Log.d("Human Created", "Im in here!");
		
			info[0] = nationality; //Nationality
			info[1] = 255;//Life
			info[2] = 1;//Health
			info[3] = 0;//Job
			info[4] = (char) MathUtils.random(80, 150);//IQ
			
			//create physic object
			this.physicbody = PhysicsFactory.createBoxBody(MainActivity.mPhysicsWorld, this, BodyType.DynamicBody, bodyFixture);//physic world,object,bodytype,bodyfixture
		
			
			this.joblessHead = new AnimatedSprite(-10,-145,MainActivity.headRegion,pVertexBufferObjectManager);
			this.normalBody = new AnimatedSprite(-35,-100,MainActivity.bodyRegion, pVertexBufferObjectManager){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
						Log.d("Human","You touched my body!");
						direction = !direction;						
					}
					return true;
				}				
			};
			
			this.physicbody.setUserData(this);
			
			//attach head and body to human object		
			this.attachChild(this.normalBody);
			this.attachChild(this.joblessHead);
			//attached human object to scene
			MainActivity.mScene.attachChild(this);
			
			//register physic object to physic world
			MainActivity.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this,this.physicbody, true, true));
			//register normal body as touch area in scene
			MainActivity.mScene.registerTouchArea(this.normalBody);
			//register object contact/collision listener
			
	}
	
	public void walk_right(AnimatedSprite body){
		vy = 0f;
		vx = 6f;
		if(!normalBody.isFlippedHorizontal())
			normalBody.setFlippedHorizontal(true);
		if(!normalBody.isAnimationRunning())
			normalBody.animate(new long[] {100,100,100} , 0 , 2 ,true);
	}
	
	public void walk_left(AnimatedSprite body){
		vy = 0f;
		vx = -6f;
		if(body.isFlippedHorizontal())
			body.setFlippedHorizontal(false);
		if(!body.isAnimationRunning())
			body.animate(new long[] {100,100,100} , 0 , 2 ,true);
	}

	public void walk_up(AnimatedSprite body){
		
		
	}
	
	public void walk_down(AnimatedSprite body){
		
		
	}

	public void walk_stop(AnimatedSprite body){
		
		
	}
	
	public void update_job(){
		
		
	}
	
	public void update_body(){
		
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
		this.physicbody.setLinearVelocity(vx,vy);
		this.physicbody.setFixedRotation(true);
		this.physicbody.setLinearDamping(10f);
	}	
	




}
