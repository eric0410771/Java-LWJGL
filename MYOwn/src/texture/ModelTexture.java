package texture;

public class ModelTexture {
	private int textureId;
	private float shinedamper = 1.0f;
	private float reflectivity = 0.0f;
	private boolean transparency = false;
	
	private int numberOfRow = 1;
	
	public ModelTexture(int textureId) {
		this.textureId = textureId;
	}
	public int getTextureId() {
		return textureId;
	}
	public void setShineDamper(float value) {
		shinedamper = value;
	}
	public void setReflectivity(float value) {
		reflectivity = value;
	}
	public float getShineDamper() {
		return shinedamper;
	}
	public float getReflectivity() {
		return reflectivity;
	}
	public void setTransparency(boolean value)
	{
		transparency = value;
	}
	public boolean getTransparency()
	{
		return transparency;
	}
	public int getNumberOfRow() {
		return numberOfRow;
	}
	public void setNumberOfRow(int numberOfRow) {
		this.numberOfRow = numberOfRow;
	}
}
