package EngineTest;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Map;

import Model.RawModel;
import Model.TextureModel;
import entities.Entity;
import shaders.StaticShader;
import toolbox.Maths;

public class EntityRenderer{
	private StaticShader s;
	public EntityRenderer(StaticShader s,Matrix4f projectionMatrix) {
		this.s = s;
		s.start();
		s.loadProjectionMatrix(projectionMatrix);
		s.loadSkyColor(new Vector3f(0.5f,0.5f,0.5f));
		s.stop();
	}
	public void render(Map<TextureModel ,List<Entity>> entities) {
		for(TextureModel texturemodel:entities.keySet())
		{
			prepareTextureModel(texturemodel);
			List<Entity> batch = entities.get(texturemodel);
			s.loadFakeLight(texturemodel.getTexture().getfakelight());
			for(Entity entity:batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES,texturemodel.getModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);
			}
			unBindTextureModel();
		}
	}
	
	public void prepareTextureModel(TextureModel texturemodel) {
		RawModel model = texturemodel.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		if(texturemodel.getTexture().getTransparency())
			MasterRender.disableCulling();
		s.loadSpect(texturemodel.getTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturemodel.getTexture().getID());
	}
	public void unBindTextureModel()
	{
		MasterRender.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void prepareInstance(Entity entity)
	{
		Matrix4f matrix = Maths.createTransformationMatrix(entity.getTranslate(),entity.getRotx(),entity.getRoty(),entity.getRotz(),entity.getScale());
		s.loadTransformationMatrix(matrix);	
	}
}