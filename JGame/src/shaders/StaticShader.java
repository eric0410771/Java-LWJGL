package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import textures.ModelTexture;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{
	
	private static String vertexfile = "src/shaders/vertexShaders.txt";
	private static String fragmentfile = "src/shaders/fragmentShaders.txt";
	private static int location_transformationMatrix;
	private static int location_projectionMatrix;
	private static int location_viewMatrix;
	private static int location_lightPosition;
	private static int location_lightColour;
	private static int location_shineDemper;
	private static int location_reflectivity;
	private static int location_fakelight;
	private static int location_skyColor;
	
	public StaticShader() {
		super(vertexfile, fragmentfile);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDemper = super.getUniformLocation("shineDemper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_fakelight = super.getUniformLocation("fakelight");
        location_skyColor = super.getUniformLocation("skycolor");
	}
	public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
     

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    
    public void loadViewMatrix(Camera camera)
    {
    	Matrix4f viewMatrix = Maths.createViewMatrix(camera);
    	super.loadMatrix(location_viewMatrix,viewMatrix);
    	
    }
    public void loadLightVector(Light light)
    {
    	super.loadVector(location_lightPosition,light.getPosition());
    	super.loadVector(location_lightColour,light.getColour());
    }
    
    public void loadSpect(ModelTexture texture) {
    	super.loadFloat(location_shineDemper, texture.getShineDemper());
    	super.loadFloat(location_reflectivity, texture.getreflectivity());
    }
    public void loadFakeLight(boolean fakelight)
    {
    	if(fakelight)
    		super.loadFloat(location_fakelight,1);
    	else 
    		super.loadFloat(location_fakelight,0);
    }
    public void loadSkyColor(Vector3f skycolor)
    {
    	super.loadVector(location_skyColor, skycolor);
    }
}
