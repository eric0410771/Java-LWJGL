package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import shader.TerrainShader;
import terrain.Terrain;
import texture.TerrainTexturePack;

public class TerrainRender {
	private TerrainShader ts;
	
	public TerrainRender(TerrainShader ts)
	{
		this.ts = ts;
		ts.start();
		ts.loadTerrainTexturePack();
		ts.stop();
		
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public void render(Terrain t)
	{
		GL30.glBindVertexArray(t.getRawModel().getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTexture(t);
		GL11.glDrawElements(GL11.GL_TRIANGLES,t.getRawModel().getVertexCounter(),GL11.GL_UNSIGNED_INT,0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	private void bindTexture(Terrain t)
	{
		TerrainTexturePack texturepack = t.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturepack.getBackgroundTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getRTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getGTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getBTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getBlendMap().getTextureId());
	}
}

