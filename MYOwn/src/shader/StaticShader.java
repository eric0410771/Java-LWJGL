package shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import entity.Light;
import texture.ModelTexture;

public class StaticShader extends ShaderProgram{
	
	private static String vertexfile = "src/shader/vertexShader.txt";
	private static String fragmentfile = "src/shader/fragmentShader.txt";
	private static int location_transformmatrix;
	private static int location_cameramatrix;
	private static int location_projectionmatrix;
	private static int location_lightposition;
	private static int location_lightcolor;
	private static int location_shinedamper;
	private static int location_reflectivity;
	private static int location_transparency;
	private static int location_numberofrow;
	private static int location_offset;
	
	public StaticShader() {
		super(vertexfile, fragmentfile);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texturecoords");
		super.bindAttribute(2, "normal");
	}
	protected void getAllUniformLocations() {
		location_transformmatrix = super.getUniformLocation("transformmatrix");
		location_cameramatrix = super.getUniformLocation("cameramatrix");
		location_projectionmatrix = super.getUniformLocation("projectionmatrix");
		location_lightposition = super.getUniformLocation("lightposition");
		location_lightcolor = super.getUniformLocation("lightcolor");
		location_shinedamper = super.getUniformLocation("shinedamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_transparency = super.getUniformLocation("transparency");
		location_numberofrow = super.getUniformLocation("numberofrow");
		location_offset = super.getUniformLocation("offset");
	}
	public void loadTransformMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformmatrix, matrix);
	}
	public void loadCameraMatrix(Matrix4f matrix) {
		super.loadMatrix(location_cameramatrix,matrix);
	}
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionmatrix, matrix);
	}
	public void loadLight(Light light) {
		super.loadVector3(location_lightposition,light.getPosition());
		super.loadVector3(location_lightcolor,light.getColor());
	}
	public void loadSpecular(ModelTexture modeltexture) {
		super.loadFloat(location_shinedamper,modeltexture.getShineDamper());
		super.loadFloat(location_reflectivity,modeltexture.getReflectivity());
	}
	public void loadTransparency(boolean value)
	{
		super.loadBoolean(location_transparency, value);
	}
	public void loadOffset(Vector2f offset)
	{
		super.loadVector2(location_offset,offset);
	}
	public void loadNumberOfRow(float numberofrow)
	{
		super.loadFloat(location_numberofrow, numberofrow);
	}
}