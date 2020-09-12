package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(800,20,800);
	private float pitch = 0f;
	private float yaw = 0f;
	private float roll = 0f;
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.y += 1f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.y -= 1f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			position.x += 1f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			position.x -= 1f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			position.z -= 1f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			position.z += 1f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			pitch += 0.2f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			pitch -= 0.2f;
		}
	}

	public Vector3f getPosition() {
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
}
