package study.game.worldgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import android.util.Log;


public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    //declaring resolution for the screen
    private static final int CAM_WIDTH = 1280;
    private static final int CAM_HEIGHT = 720;
    //declare camera
    private Camera mCamera;
    //declare memory buffer image/sprite
    private BitmapTextureAtlas BodyTextureAtlas;
    private BitmapTextureAtlas HeadTextureAtlas;
    private BitmapTextureAtlas InvTextureAtlas;
    //declare buffer region for the image
    public static TiledTextureRegion bodyRegion;
    public static TiledTextureRegion headRegion;
    public static TiledTextureRegion invobjRegion;
    
    public static PhysicsWorld mPhysicsWorld;
    public static Scene mScene;
    
    private char[] infobuff = new char[5];
    
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		
        //create new camera object
        mCamera = new Camera(0, 0, CAM_WIDTH, CAM_HEIGHT);
        //setting engine option
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAM_WIDTH, CAM_HEIGHT), this.mCamera);
        //return result from setting engine option 
        return engineOptions;
	}



	@Override
	protected void onCreateResources() {
		//Create memory buffer 
		this.BodyTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),458,458,TextureOptions.BILINEAR);
		this.HeadTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),308,104,TextureOptions.BILINEAR);
		this.InvTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),80,30,TextureOptions.BILINEAR);
		//Map Image into memory buffer
		MainActivity.bodyRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.BodyTextureAtlas,this,"bodypack.png",0,0,3,3);
		MainActivity.headRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.HeadTextureAtlas,this,"c1head.png",0,0,3,1);
		MainActivity.invobjRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.InvTextureAtlas, this, "select_mb.png",0,0,1,1);
		//Load image
		this.BodyTextureAtlas.load();
		this.HeadTextureAtlas.load();
		this.InvTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {		
		// TODO Auto-generated method stub
		MainActivity.mPhysicsWorld =  new PhysicsWorld(new Vector2(0,0),false);
        //create scene object
		MainActivity.mScene = new Scene();
        //create a background color
		MainActivity.mScene.setBackground(new Background(0.4f,0.4f,0.4f));
		
		Human mat2 = new Human((char) 2, 500, 500, this.getVertexBufferObjectManager());
		Human mat1 = new Human((char) 1, 100, 500, this.getVertexBufferObjectManager());	
			
		
		
		
		
		
		MainActivity.mScene.setOnSceneTouchListener(this);
		MainActivity.mScene.registerUpdateHandler(MainActivity.mPhysicsWorld);
		MainActivity.mPhysicsWorld.setContactListener(CreateContactListener());	
		
		//Human mat4 = new Human((char) 4, 500, 500, this.getVertexBufferObjectManager());
		//Human mat3 = new Human((char) 3, 500, 500, this.getVertexBufferObjectManager());
	
		
		mat1.walk_right(mat1.normalBody);
				
		return mScene;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		Log.d("Scene", "You touch scene!");
		return true;
	}	
	
	private ContactListener CreateContactListener(){
		ContactListener listen = new ContactListener(){

			@Override
			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				// TODO Auto-generated method stub
				Log.d("Object COntact Listerner", "beginContact");
				Human buffer = (Human) x1.getBody().getUserData();
				
				buffer.walk_left(buffer.normalBody);
				
				//Log.d("Human : I'm", String.valueOf((int)info[0]));
	
				
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				Log.d("Object COntact Listerner", "endContact");
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				Log.d("Object COntact Listerner", "preSolve");
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				Log.d("Object COntact Listerner", "postSolve");
			}
			
		};
		return listen;
		
	}


	
}
