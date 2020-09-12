package EngineTest;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Model.RawModel;
import shaders.TerrainShaderProgram;
import terrains.Terrain;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class TerrainRenderer {
	private TerrainShaderProgram s;
	public TerrainRenderer(TerrainShaderProgram s,Matrix4f projectionMatrix) {
		this.s = s;
		
		this.s.start();
		this.s.loadProjectionMatrix(projectionMatrix);
		this.s.loadSkyColor(new Vector3f(0.5f,0.5f,0.5f));
		this.s.connectTextureUnit();
		this.s.stop();
	}
	public void render(List<Terrain> terrains)
	{
		for(Terrain terrain:terrains)
		{
			prepareTerrainTexture(terrain);
			prepareTerrain(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES,terrain.getModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);
		}
		unBindTextureModel();
	}

	public void prepareTerrainTexture(Terrain terrain) {
		RawModel model = terrain.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(terrain);
		s.loadSpect(1,0);
	}
	
	public void bindTextures(Terrain terrain) {
		TerrainTexturePack texturepack = terrain.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturepack.getBackgroundtexture().getID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturepack.getRtexture().getID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturepack.getGtexture().getID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getBtexture().getID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getID());
	}
	public void unBindTextureModel()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void prepareTerrain(Terrain terrain)
	{
		Matrix4f matrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(),0,terrain.getZ()),0,0,0,1);
		s.loadTransformationMatrix(matrix);	
	}
}
