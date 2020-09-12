package EngineTest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Model.RawModel;
import Model.TextureModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import objConverter.OBJFileLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;


public class MainGameLoop {
	private static SecureRandom random;
	public static void main(String args[]) throws LWJGLException
	{
		DisplayManager.createDisplay();
		random = new SecureRandom();

		Loader loader = new Loader();	
		RawModel dragonmodel = OBJLoader.loadObjModel("dragon",loader);
		RawModel treemodel = OBJLoader.loadObjModel("tree", loader);
		RawModel fernmodel = OBJLoader.loadObjModel("fern", loader);
		RawModel rubymodel = OBJLoader.loadObjModel("model1",loader);
		RawModel grassmodel = OBJLoader.loadObjModel("grassModel", loader);
		RawModel sheepmodel = OBJLoader.loadObjModel("sheep", loader);
		RawModel weaponmodel = OBJLoader.loadObjModel("weapon",loader);
		RawModel blockmodel = OBJFileLoader.loadOBJ("block");
		
		TerrainTexture backgroundtexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rtexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gtexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture btexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendmap = new TerrainTexture(loader.loadTexture("blendMap"));
				
		TerrainTexturePack texturepack = new TerrainTexturePack(backgroundtexture,rtexture,gtexture,btexture);
		
		
		
		ModelTexture grasstexture = new ModelTexture(loader.loadTexture("grass"));
		ModelTexture treetexture = new ModelTexture(loader.loadTexture("tree"));
		ModelTexture ferntexture = new ModelTexture(loader.loadTexture("fern"));
		ModelTexture bluetexture = new ModelTexture(loader.loadTexture("blue"));
		ModelTexture realgrasstexture = new ModelTexture(loader.loadTexture("grassTexture"));
		ModelTexture blacktexture = new ModelTexture(loader.loadTexture("black"));
		ModelTexture whitetexture = new ModelTexture(loader.loadTexture("white"));
		ModelTexture weapontexture = new ModelTexture(loader.loadTexture("red"));
		ModelTexture blocktexture = new ModelTexture(loader.loadTexture("block"));
		
		grasstexture.setreflectivity(1);
		grasstexture.setShineDemper(10);
		treetexture.setreflectivity(1);
		treetexture.setShineDemper(5);
		ferntexture.setreflectivity(1);
		ferntexture.setShineDemper(4);
		bluetexture.setreflectivity(1);
		bluetexture.setShineDemper(5);
		realgrasstexture.setreflectivity(1);
		realgrasstexture.setShineDemper(5);
		blacktexture.setreflectivity(1);
		blacktexture.setShineDemper(5);
		weapontexture.setreflectivity(1);
		weapontexture.setShineDemper(5);
		whitetexture.setShineDemper(5);
		whitetexture.setreflectivity(1);
		
		
		TextureModel dragontexturemodel = new TextureModel(dragonmodel,grasstexture);
		TextureModel treetexturemodel = new TextureModel(treemodel,treetexture);
		TextureModel ferntexturemodel = new TextureModel(fernmodel,ferntexture);
		TextureModel sheeptexturemodel = new TextureModel(sheepmodel,bluetexture);
		TextureModel grasstexturemodel = new TextureModel(grassmodel,realgrasstexture);
		TextureModel ruby = new TextureModel(rubymodel,whitetexture);
		TextureModel weapon = new TextureModel(weaponmodel,weapontexture);
		TextureModel block = new TextureModel(blockmodel,blocktexture);
		
		
		ferntexturemodel.getTexture().setTransparency(true);
		ferntexturemodel.getTexture().setfakelight(true);
		grasstexturemodel.getTexture().setTransparency(true);
		grasstexturemodel.getTexture().setfakelight(true);
		ruby.getTexture().setfakelight(false);
		
		Light light = new Light(new Vector3f(3000,-5000,3000),new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		MasterRender masterrender = new MasterRender();
		
		Terrain terrain = new Terrain(0,0,loader,texturepack,blendmap);
		
		List<Entity> entities = new ArrayList<Entity>();
		
		entities.add(new Entity(block,new Vector3f(100,1,20),0,0,0,5));
		
		for(int i = 0 ;i<2;i++)
		{
			float x = 50*i+20;
			float y = 1;
			float z = 50*i+20;
			entities.add(new Entity(dragontexturemodel,new Vector3f(x,y,z),0,0,0,1));
		}
		for(int i = 0;i<50;i++)
		{
			entities.add(new Entity(treetexturemodel,new Vector3f(800-random.nextFloat()*750,1,800-random.nextFloat()*750),0,0,0,20));
		}
		for(int i = 0 ;i<100;i++)
		{
			entities.add(new Entity(ferntexturemodel,new Vector3f(800-random.nextFloat()*750,1,800-random.nextFloat()*750),0,0,0,5));
		}
		
		for(int i = 0;i<50;i++)
		{
			entities.add(new Entity(grasstexturemodel,new Vector3f(800-random.nextFloat()*750,1,800-random.nextFloat()*750),0,0,0,5));
		}
		entities.add(new Entity(sheeptexturemodel,new Vector3f(50,1,80),0,0,0,5));
		//entities.add(new Entity(ruby,new Vector3f(200,1,200),0,0,0,5));
		//entities.add(new Entity(weapon,new Vector3f(300,1,300),0,0,0,10));
		
		
		while(!Display.isCloseRequested())
		{
			camera.move();
			
			for(Entity entity:entities)
			{
				masterrender.processEntity(entity);
			}
			
			masterrender.processTerrain(terrain);
			
			masterrender.render(camera, light);
			DisplayManager.updateDisplay();
		}
		
		masterrender.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
		
	}
}
