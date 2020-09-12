package guis;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entity.RawModel;
import loader.Loader;
import toolbox.Maths;

public class GuiRender {
	private final RawModel quad;
	private GuiShader shader;
	public GuiRender(Loader loader)
	{
		float []position = {-1,1,-1,-1,1,1,1,-1};
		quad = loader.loadToVao(position);
		shader = new GuiShader();
	}
	public void render(List<GuiTexture> guis) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		
		for(GuiTexture gui:guis)
		{
			shader.loadTransformMatrix(Maths.createTransformMatrix(gui.getPosition(), gui.getScale()));
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,gui.getTexture());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0,quad.getVertexCounter());
		}
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	public void cleanUp()
	{
		shader.cleanUp();
	}
}
