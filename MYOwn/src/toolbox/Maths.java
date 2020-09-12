package toolbox;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;

public class Maths {
	private static float fov = 70f;
	private static float z_near = 0.1f;
	private static float z_far = 1000f;
	
	public static Matrix4f createTransformMatrix(Vector2f position,Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x,scale.y,1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformMatrix(Vector3f translate,float rx,float ry,float rz,float scale) {
		Matrix4f matrix =new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translate,matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(rx),new Vector3f(1,0,0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(ry),new Vector3f(0,1,0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(rz),new Vector3f(0,0,1),matrix,matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale),matrix,matrix);
		return matrix;
	}
	public static Matrix4f createCameraMatrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(camera.getPitch()),new Vector3f(1,0,0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(camera.getYaw()),new Vector3f(0,1,0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(camera.getRoll()),new Vector3f(0,0,1),matrix,matrix);
		Vector3f negativeposition = new Vector3f(-camera.getPosition().x,-camera.getPosition().y,-camera.getPosition().z);
		Matrix4f.translate(negativeposition,matrix,matrix);
		return matrix;
	}
	public static Matrix4f createProjectionMatrix() {
		Matrix4f matrix = new Matrix4f();
		float a = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * a);
		float x_scale = y_scale/a;
		
		float zm = z_far - z_near;
		float zp = z_far + z_near;
		
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -zp/zm;
		matrix.m23 = -1;
		matrix.m32 = -(2*z_far*z_near)/zm;
		matrix.m33 = 0;
		return matrix;
	}
}
