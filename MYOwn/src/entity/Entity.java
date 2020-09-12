package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
	private TextureModel texturemodel;
	private Vector3f translate;
	private float rotx;
	private float roty;
	private float rotz;
	private float scale;
	private int textureindex = 0;
	
	public Entity(TextureModel texturemodel,Vector3f translate,float rotx,float roty,float rotz,float scale) {
		this.texturemodel = texturemodel;
		this.translate = translate;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.scale = scale;
	}
	public Entity(TextureModel texturemodel,int textureindex,Vector3f translate,float rotx,float roty,float rotz,float scale)
	{
		this(texturemodel, translate, rotx, roty, rotz, scale);
		this.textureindex = textureindex;
	}
	public void increasePosition(float dx,float dy,float dz) {
		translate.x += dx;
		translate.y += dy;
		translate.z += dz;
	}
	public void increaseRotate(float rx,float ry,float rz) {
		rotx += rx;
		roty += ry;
		rotz += rz;
	}
	public TextureModel getTextureModel() {
		return this.texturemodel;
	}
	public Vector3f getTranslate() {
		return this.translate;
	}
	public float getRotX() {
		return this.rotx;
	}
	public float getRotY() {
		return this.roty;
	}
	public float getRotZ() {
		return this.rotz;
	}
	public float getScale() {
		return this.scale;
	}
	protected void changeTextureModel(TextureModel texturemodel)
	{
		this.texturemodel = texturemodel;
	}
	public float getTextureXOffset() {
		int column = textureindex%texturemodel.getModelTexture().getNumberOfRow();
		return (float)column/(float)texturemodel.getModelTexture().getNumberOfRow();
	}
	public float getTextureYOffset() {
		int row = textureindex/texturemodel.getModelTexture().getNumberOfRow();
		return (float)row/(float)texturemodel.getModelTexture().getNumberOfRow();
	}
}

