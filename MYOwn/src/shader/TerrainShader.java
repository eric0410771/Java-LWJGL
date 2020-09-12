package shader;

import org.lwjgl.util.vector.Matrix4f;

import entity.Light;
import texture.ModelTexture;

public class TerrainShader extends ShaderProgram{
	private static String vertexfile = "src/shader/terrainvertexShader.txt";
	private static String fragmentfile = "src/shader/terrainfragmentShader.txt";
	
	private static int location_transformmatrix;
	private static int location_projectionmatrix;
	private static int location_cameramatrix;
	private static int location_lightposition;
	private static int location_lightcolor;
	private static int location_backgroundtexture;
	private static int location_rtexture;
	private static int location_gtexture;
	private static int location_btexture;
	private static int location_blendmap;
	
	
	public TerrainShader()
	{
		super(vertexfile, fragmentfile);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0,"position");
		super.bindAttribute(1,"texturecoord");
		super.bindAttribute(2,"normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformmatrix = super.getUniformLocation("transformmatrix");
		location_projectionmatrix = super.getUniformLocation("projectionmatrix");
		location_cameramatrix = super.getUniformLocation("cameramatrix");
		location_lightposition = super.getUniformLocation("lightposition");
		location_lightcolor = super.getUniformLocation("lightcolor");
		location_backgroundtexture = super.getUniformLocation("backgroundtexture");
		location_rtexture = super.getUniformLocation("rtexture");
		location_gtexture = super.getUniformLocation("gtexture");
		location_btexture = super.getUniformLocation("btexture");
		location_blendmap = super.getUniformLocation("blendmap");
		
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
	public void loadTerrainTexturePack()
	{
		super.loadInt(location_backgroundtexture,0);
		super.loadInt(location_rtexture, 1);
		super.loadInt(location_gtexture, 2);
		super.loadInt(location_btexture, 3);
		super.loadInt(location_blendmap, 4);
	}
}
