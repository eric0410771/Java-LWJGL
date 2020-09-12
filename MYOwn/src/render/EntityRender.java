package render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import entity.Entity;
import entity.RawModel;
import entity.TextureModel;
import shader.StaticShader;
import toolbox.Maths;

public class EntityRender {
	private StaticShader s;
	public EntityRender(StaticShader s) {
		this.s = s;
	}
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public void render(Entity entity,StaticShader s)
	{
		RawModel model = entity.getTextureModel().getRawModel();
		GL30.glBindVertexArray(model.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		s.loadSpecular(entity.getTextureModel().getModelTexture());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,entity.getTextureModel().getModelTexture().getTextureId());
		GL11.glDrawElements(GL11.GL_TRIANGLES,model.getVertexCounter(),GL11.GL_UNSIGNED_INT,0);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	public void render(Map<TextureModel,List<Entity>>entities)
	{
		for(TextureModel tm:entities.keySet())
		{
			processTextureModel(tm);
			for(Entity entity:entities.get(tm))
			{
				processTransformMatrix(entity);
				processTextureIndex(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES,tm.getRawModel().getVertexCounter(),GL11.GL_UNSIGNED_INT,0);
			}
			unbindTextureModel();
		}
	}
	public void processTextureModel(TextureModel tm) {
		GL30.glBindVertexArray(tm.getRawModel().getVaoId());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		if(tm.getModelTexture().getTransparency())
			GL11.glDisable(GL11.GL_CULL_FACE);
		s.loadSpecular(tm.getModelTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,tm.getModelTexture().getTextureId());
	}
	public void unbindTextureModel() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	public void processTransformMatrix(Entity entity) {
		Matrix4f matrix = Maths.createTransformMatrix(entity.getTranslate(),entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());
		s.loadTransformMatrix(matrix);
	}
	private void processTextureIndex(Entity entity)
	{
		s.loadNumberOfRow(entity.getTextureModel().getModelTexture().getNumberOfRow());
		s.loadOffset(new Vector2f(entity.getTextureXOffset(),entity.getTextureYOffset()));
	}
}
