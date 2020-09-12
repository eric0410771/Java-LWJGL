package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShaderProgram extends ShaderProgram{
	
	private static String vertexfile = "src/shaders/terrainvertexShaders.txt";
	private static String fragmentfile = "src/shaders/terrainfragmentShaders.txt";
	private static int location_transformationMatrix;
	private static int location_projectionMatrix;
	private static int location_viewMatrix;
	private static int location_lightPosition;
	private static int location_lightColour;
	private static int location_shineDemper;
	private static int location_reflectivity;
	private static int location_skyColor;
	private static int location_backgroundtexture;
	private static int location_rtexture;
	private static int location_gtexture;
	private static int location_btexture;
	private static int location_blendmap;
	
	public TerrainShaderProgram() {
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
        location_skyColor = super.getUniformLocation("skycolor");
        location_backgroundtexture = super.getUniformLocation("backgroundTexture");
        location_rtexture = super.getUniformLocation("rTexture");
        location_gtexture = super.getUniformLocation("gTexture");
        location_btexture = super.getUniformLocation("bTexture");
        location_blendmap = super.getUniformLocation("blendMap");
	}
	
	public void connectTextureUnit()
	{
		super.loadInt(location_backgroundtexture,0);
		super.loadInt(location_rtexture, 1);
		super.loadInt(location_gtexture,2);
		super.loadInt(location_btexture, 3);
		super.loadInt(location_blendmap,4);
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
    
    public void loadSpect(float shine,float reflectivity) {
    	super.loadFloat(location_shineDemper, shine);
    	super.loadFloat(location_reflectivity, reflectivity);
    }
    public void loadSkyColor(Vector3f skycolor)
    {
    	super.loadVector(location_skyColor, skycolor);
    }
}
