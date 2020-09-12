package entities;

import org.lwjgl.util.vector.Vector3f;

import Model.TextureModel;

public class Entity {
	private TextureModel texturemodel;
	private Vector3f translate;
	private float rotx,roty,rotz;
	private float scale;
	
	
	public Entity(TextureModel texturemodel, Vector3f translate, float rotx, float roty, float rotz, float scale) {
		super();
		this.texturemodel = texturemodel;
		this.translate = translate;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.scale = scale;
	}

	public void increasePosition(float dx,float dy,float dz) {
		this.translate.x+=dx;
		this.translate.y+=dy;
		this.translate.z+=dz;
		//System.out.println(translate);
	}

	public void increaseRotate(float rx,float ry,float rz) {
		this.rotx += rx;
		this.roty += ry;
		this.rotz += rz;
	}
	public TextureModel getTexturemodel() {
		return texturemodel;
	}


	public void setTexturemodel(TextureModel texturemodel) {
		this.texturemodel = texturemodel;
	}


	public Vector3f getTranslate() {
		return translate;
	}


	public void setTranslate(Vector3f translate) {
		this.translate = translate;
	}


	public float getRotx() {
		return rotx;
	}


	public void setRotx(float rotx) {
		this.rotx = rotx;
	}


	public float getRoty() {
		return roty;
	}


	public void setRoty(float roty) {
		this.roty = roty;
	}


	public float getRotz() {
		return rotz;
	}


	public void setRotz(float rotz) {
		this.rotz = rotz;
	}


	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
}
