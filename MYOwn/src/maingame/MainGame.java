package maingame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import displaymanager.DisplayManager;
import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import entity.RawModel;
import entity.TextureModel;
import guis.GuiRender;
import guis.GuiTexture;
import loader.Loader;
import loader.ObjLoader;
import objConverter.OBJFileLoader;
import render.EntityRender;
import render.TerrainRender;
import shader.StaticShader;
import shader.TerrainShader;
import terrain.Terrain;
import texture.ModelTexture;
import texture.TerrainTexture;
import texture.TerrainTexturePack;
import toolbox.Maths;
import java.security.SecureRandom;
public class MainGame {
	
	private static SecureRandom random = new SecureRandom();
	private static Map<TextureModel,List<Entity>> entities = new HashMap<TextureModel,List<Entity>>();
	private static List<Entity> entitygroup = new ArrayList<Entity>();
	private static List<GuiTexture> guis = new ArrayList<GuiTexture>();
	private static int rubywalknumber = 8;
	
	
	public static void processEntity(Entity entity)
	{
		TextureModel tm = entity.getTextureModel();
		List<Entity> value = entities.get(tm);
		if(value == null)
		{
			value = new ArrayList<Entity>();
			value.add(entity);
			entities.put(tm, value);
		}else
		{
			value.add(entity);
		}
	} 
	public static long getTime()
	{
		return Sys.getTime()/(Sys.getTimerResolution()/8);
	}
	public static void main(String args[])
	{
		DisplayManager displaymanager = new DisplayManager();
		displaymanager.create();
		
		
		StaticShader s = new StaticShader();
		TerrainShader ts = new TerrainShader();
		EntityRender render = new EntityRender(s);
		TerrainRender trender = new TerrainRender(ts);
		
		Loader loader = new Loader();
		
		GuiRender grender = new GuiRender(loader);
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("symbol"),new Vector2f(0.5f,0.5f),new Vector2f(0.3f,0.4f));
		GuiTexture gui2 = new GuiTexture(loader.loadTexture("symbol"),new Vector2f(0.5f,0.6f),new Vector2f(0.3f,0.4f));

		guis.add(gui);
		guis.add(gui2);
		
		RawModel []rubywalk = new RawModel[rubywalknumber];
		
		for(int i = 0 ;i<rubywalk.length;i++)
		{
			rubywalk[i] = OBJFileLoader.loadOBJ("ruby_walk"+String.valueOf(i));
		}
		
		ModelTexture rubytexture = new ModelTexture(loader.loadTexture("gray"));
		
		rubytexture.setReflectivity(0f);
		rubytexture.setShineDamper(5f);
		
		
		RawModel tree = ObjLoader.loadObjFile("tree", loader);
		ModelTexture treetexture = new ModelTexture(loader.loadTexture("tree"));
		treetexture.setReflectivity(1f);
		treetexture.setShineDamper(10f);
		
		
		RawModel fernmodel = ObjLoader.loadObjFile("fern", loader);
		ModelTexture ferntexture = new ModelTexture(loader.loadTexture("fern"));
		
		ferntexture.setReflectivity(0.5f);
		ferntexture.setShineDamper(10.0f);
		ferntexture.setTransparency(true);
		ferntexture.setNumberOfRow(2);
		
		
		RawModel dragonmodel = OBJFileLoader.loadOBJ("dragon");
		ModelTexture dragontexture = new ModelTexture(loader.loadTexture("yellow"));		
		dragontexture.setReflectivity(0.5f);
		dragontexture.setShineDamper(1.0f);
		
		TextureModel texturedragon = new TextureModel(dragonmodel,dragontexture);
		
		TextureModel texturefern = new TextureModel(fernmodel,ferntexture);
		
		

		TextureModel []texturerubymodel = new TextureModel[rubywalknumber];
		for(int i = 0 ;i<1;i++)
			texturerubymodel[i] = new TextureModel(rubywalk[i],rubytexture);
		
		
		TextureModel texturetree = new TextureModel(tree,treetexture);
		
		Player player = new Player(texturerubymodel[0],new Vector3f(400,0,400),0f,270f,0f,2f);
		Camera camera = new Camera(player);
		
		TerrainTexture backgroundtexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rtexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gtexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture btexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendmap = new TerrainTexture(loader.loadTexture("blendmap2"));
		
		TerrainTexturePack terraintexturepack = new TerrainTexturePack(backgroundtexture,rtexture,gtexture,btexture);
		
		
		Terrain terrain = new Terrain(0,0,loader,terraintexturepack,blendmap,"heightmap4");
		float dragonx = 800-random.nextFloat()*750;
		float dragonz = 800-random.nextFloat()*750;
		entitygroup.add(new Entity(texturedragon,new Vector3f(dragonx,terrain.getHeightOfTerrain(dragonx,dragonz),dragonz),0,0,0,3));
		
		for(int i = 0; i<100 ; i++)
		{
			float x = 800-random.nextFloat()*750;
			float z = 800-random.nextFloat()*750;
			float y = terrain.getHeightOfTerrain(x,z);
			entitygroup.add(new Entity(texturetree,new Vector3f(x,y,z),0,0,0,10));
		}
		for(int i = 0; i<60;i++)
		{
			float x = 500-random.nextFloat()*200;
			float z = 500-random.nextFloat()*200;
			float y = terrain.getHeightOfTerrain(x, z);
			int index = random.nextInt(4);
			entitygroup.add(new Entity(texturefern,index,new Vector3f(x,y,z),0,0,0,3));
		}
		entitygroup.add(player);
		
		for(Entity entity:entitygroup)
			processEntity(entity);
		
		Light light = new Light(new Vector3f(600,400,600),new Vector3f(1,1,1));
		s.start();
		s.loadLight(light);
		s.loadProjectionMatrix(Maths.createProjectionMatrix());
		s.stop();
		
		ts.start();
		ts.loadLight(light);
		ts.loadProjectionMatrix(Maths.createProjectionMatrix());
		ts.loadTransformMatrix(Maths.createTransformMatrix(new Vector3f(terrain.getX(),0,terrain.getZ()),0,0,0,1));
		ts.stop();
		
		Matrix4f cmatrix = Maths.createCameraMatrix(camera);
		 
		 
		while(!Display.isCloseRequested()) {
			/*entities.remove(player.getTextureModel());
			player.changeTextureModel(texturerubymodel[(int) ((getTime())%rubywalknumber)]);
			processEntity(player);*/
			render.prepare();
			grender.render(guis);
			s.start();
			player.move(terrain);
			camera.move();
			cmatrix = Maths.createCameraMatrix(camera);
			s.loadCameraMatrix(cmatrix);
			render.render(entities);
			s.stop();
			ts.start();
			ts.loadCameraMatrix(cmatrix);
			trender.render(terrain);
			ts.stop();
			displaymanager.update();
		}
		grender.cleanUp();
		ts.cleanUp();
		s.cleanUp();
		loader.cleanUP();
		displaymanager.destroy();
	}
}
