package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrain.Terrain;

public class Camera {
	private float distanceFromPlayer = 100;
	private float angleAroundPlayer = 0;
	private Vector3f position = new Vector3f(400,1,400);
	private float pitch = 20;
	private float yaw;
	private float roll;
	private Player player;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	public void move() {
		caculateZoom();
		caculatePitch();
		caculateAngleAroundPlayer();
		float horizontalDistance = caculateHorizontalDistance();
		float verticalDistance =caculateVerticalDistance();
		caculateCameraPosition(horizontalDistance,verticalDistance);
		yaw =180- (player.getRotY()+angleAroundPlayer);
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	private float caculateHorizontalDistance()
	{
		return (float)(distanceFromPlayer*Math.cos(Math.toRadians(getPitch())));
	}
	private float caculateVerticalDistance() {
		return (float)(distanceFromPlayer*Math.sin(Math.toRadians(getPitch())));
	}
	private void caculateCameraPosition(float horizontalDistance,float veriticalDistance)
	{
		float theta = player.getRotY()+angleAroundPlayer;
		float offsetx = (float)(horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetz = (float)(horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.y = player.getTranslate().y+veriticalDistance;
		position.x = player.getTranslate().x-offsetx;
		position.z = player.getTranslate().z-offsetz;
		
	}
	private void caculateZoom() {
		float zoomlevel = Mouse.getDWheel()*0.1f;
		distanceFromPlayer -= zoomlevel;
	}
	private void caculatePitch() {
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() *0.1f;
			pitch-= pitchChange;
		}
	}
	private void caculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX()*0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
