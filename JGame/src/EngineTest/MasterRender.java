package EngineTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import Model.TextureModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import shaders.StaticShader;
import shaders.TerrainShaderProgram;
import terrains.Terrain;

public class MasterRender {
	private StaticShader shader = new StaticShader();
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	private EntityRenderer renderer;
	private Map<TextureModel ,List<Entity>> entities = new HashMap<TextureModel,List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private TerrainRenderer terrainrender;
	private TerrainShaderProgram terrainshader = new TerrainShaderProgram();
	
	public MasterRender() {
		createProjectionMatrix();
		renderer= new EntityRenderer(shader,projectionMatrix);
		terrainrender = new TerrainRenderer(terrainshader,projectionMatrix);
		
	}
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	public void render(Camera camera,Light light)
	{
		prepare();
		shader.start();
		shader.loadLightVector(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		terrainshader.start();
		terrainshader.loadLightVector(light);
		terrainshader.loadViewMatrix(camera);
		terrainrender.render(terrains);
		terrainshader.stop();
		entities.clear();
		terrains.clear();
		
	}
	
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		TextureModel texturemodel = entity.getTexturemodel();
		List<Entity> batch = entities.get(texturemodel);
		if(batch!=null)
		{
			batch.add(entity);
		}else
		{	List<Entity> newbatch = new ArrayList<Entity>();
			newbatch.add(entity);
			entities.put(texturemodel,newbatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainshader.cleanUp();
	}
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		
	}
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
	
}
