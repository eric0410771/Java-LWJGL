package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import displaymanager.DisplayManager;
import terrain.Terrain;

public class Player extends Entity{
	private static final float run_speed = 50;
	private static final float turn_speed = 240;
	private static final float jump_power = 60;
	private static final float terrain_height = 0;
	private static final float gravity = -150;
	
	
	private float currentspeed = 0;
	private float currentturnspeed = 0;
	private float upwardspeed = 0;
	
	public Player(TextureModel texturemodel,Vector3f position,float rotx,float roty,float rotz,float scale)
	{
		super(texturemodel,position,rotx,roty,rotz,scale);
	}
	public void move(Terrain terrain) {
		checkInputs(terrain);
		super.increaseRotate(0, currentturnspeed*DisplayManager.getFrameTimeSecond(),0);
		float distance = currentspeed*DisplayManager.getFrameTimeSecond();
		
		float dx = (float)(distance*Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float)(distance*Math.cos(Math.toRadians(super.getRotY())));
		
		super.increasePosition(dx,0, dz);
		
		upwardspeed += gravity*DisplayManager.getFrameTimeSecond();
		super.increasePosition(0,upwardspeed*DisplayManager.getFrameTimeSecond(),0);
		
		if(super.getTranslate().y<terrain.getHeightOfTerrain(this.getTranslate().x,this.getTranslate().z))
		{
			upwardspeed = 0;
			super.getTranslate().y = terrain.getHeightOfTerrain(this.getTranslate().x,this.getTranslate().z);
			
		}
		
	}
	public void checkInputs(Terrain terrain)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_I))
			currentspeed = run_speed;
		else if(Keyboard.isKeyDown(Keyboard.KEY_K))
			currentspeed = -run_speed;
		else 
			currentspeed = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_L))
			currentturnspeed = turn_speed;
		else if(Keyboard.isKeyDown(Keyboard.KEY_J))
			currentturnspeed = - turn_speed;
		else 
			currentturnspeed = 0 ;
	
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			if(super.getTranslate().y <=  terrain.getHeightOfTerrain(this.getTranslate().x,this.getTranslate().z)+0.01)
				upwardspeed += jump_power;
		
		}
	}
	public void changeTextureModel(TextureModel texturemodel)
	{
		super.changeTextureModel(texturemodel);
	}
}
