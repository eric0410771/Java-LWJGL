package textures;

public class ModelTexture {
	private int textureID;
	
	private int shineDemper = 1;
	private int reflectivity = 0;
	private boolean fakelight = false;
	private boolean transparency = false;
	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}
	public void setfakelight(boolean fakelight) {
		this.fakelight = fakelight;
	}
	public boolean getfakelight()
	{
		return fakelight;
	}
	public boolean getTransparency() {
		return transparency;
	}
	public ModelTexture(int id)
	{	
		textureID = id;
	}
	public int getID() {
		return textureID;
	}
	public int getShineDemper() {
		return shineDemper;
	}
	public void setShineDemper(int shineDemper) {
		this.shineDemper = shineDemper;
	}
	public int getreflectivity() {
		return reflectivity;
	}
	public void setreflectivity(int reflectivity) {
		this.reflectivity = reflectivity;
	}
}
